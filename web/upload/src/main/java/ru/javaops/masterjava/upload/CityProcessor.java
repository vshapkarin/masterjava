package ru.javaops.masterjava.upload;

import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.CityDao;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CityProcessor {

    private static CityDao cityDao = DBIProvider.getDao(CityDao.class);

    public void process(InputStream inputStream) throws XMLStreamException {
        StaxStreamProcessor processor = new StaxStreamProcessor(inputStream);

        List<String> cityIdNames = new ArrayList<>();
        List<String> cityNames = new ArrayList<>();
        while (processor.doUntil(XMLEvent.START_ELEMENT, "City")) {
            String cityIdName = processor.getAttribute("id");
            String cityName = processor.getText();
            cityIdNames.add(cityIdName);
            cityNames.add(cityName);
        }

        cityDao.insertBatch(cityIdNames, cityNames);
    }

}
