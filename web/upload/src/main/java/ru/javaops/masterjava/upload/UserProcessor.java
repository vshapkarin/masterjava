package ru.javaops.masterjava.upload;

import ru.javaops.masterjava.persist.model.User;
import ru.javaops.masterjava.persist.model.UserFlag;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.JaxbUnmarshaller;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class UserProcessor {
    private static final JaxbParser jaxbParser = new JaxbParser(ObjectFactory.class);
    private static final int THREAD_NUMBER = 8;

    public List<User> process(final InputStream is) throws XMLStreamException, JAXBException {
        final StaxStreamProcessor processor = new StaxStreamProcessor(is);
        List<User> users = new ArrayList<>();

        JaxbUnmarshaller unmarshaller = jaxbParser.createUnmarshaller();
        while (processor.doUntil(XMLEvent.START_ELEMENT, "User")) {
            ru.javaops.masterjava.xml.schema.User xmlUser = unmarshaller.unmarshal(processor.getReader(), ru.javaops.masterjava.xml.schema.User.class);
            final User user = new User(xmlUser.getValue(), xmlUser.getEmail(), UserFlag.valueOf(xmlUser.getFlag().value()));
            users.add(user);
        }
        return users;
    }

    public List<User> processAndApplyFunctionInParallel(final InputStream is,
                                                        int chunkSize,
                                                        Function<List<User>, List<User>> function)
            throws XMLStreamException, JAXBException {
        final StaxStreamProcessor processor = new StaxStreamProcessor(is);
        JaxbUnmarshaller unmarshaller = jaxbParser.createUnmarshaller();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER);
        List<User> resultList = new ArrayList<>();
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        List<User> chunkOfUsers = new ArrayList<>();
        for (int i = 0; processor.doUntil(XMLEvent.START_ELEMENT, "User"); i++) {
            ru.javaops.masterjava.xml.schema.User xmlUser = unmarshaller.unmarshal(processor.getReader(), ru.javaops.masterjava.xml.schema.User.class);
            final User user = new User(xmlUser.getValue(), xmlUser.getEmail(), UserFlag.valueOf(xmlUser.getFlag().value()));
            chunkOfUsers.add(user);

            if (i % chunkSize == 0) {
                List<User> chunkOfUsersForThread = new ArrayList<>(chunkOfUsers);
                CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> function.apply(chunkOfUsersForThread), executor)
                        .thenAccept(resultList::addAll);
                futures.add(future);
                chunkOfUsers.clear();
            }
        }

        if (chunkOfUsers.size() != 0) {
            CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> function.apply(chunkOfUsers), executor)
                    .thenAccept(resultList::addAll);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return resultList;
    }

}
