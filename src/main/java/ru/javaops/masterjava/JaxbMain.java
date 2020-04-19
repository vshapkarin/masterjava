package ru.javaops.masterjava;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema2.*;
import ru.javaops.masterjava.xml.util.JaxbParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JaxbMain {
    public static void main(String[] args) throws IOException, JAXBException {
        JaxbParser jaxbParser = new JaxbParser(ObjectFactory.class);
        Payload payload = jaxbParser.unmarshal(Resources.getResource("payload.xml").openStream());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please, type a project group:");
            String searchableGroup = scanner.next();
            if ("exit".equalsIgnoreCase(searchableGroup)) {
                break;
            }

            List<String> projectParticipants = getProjectParticipants(payload, searchableGroup);
            System.out.println("List of " + searchableGroup + " participants\n" + projectParticipants + "\n");
        }
    }

    private static List<String> getProjectParticipants(Payload payload, String searchableGroup) {
        List<String> projectParticipants = new ArrayList<>();
        for (User user : payload.getUsers().getUser()) {
            for (Object usersProject : user.getInvolvedProjects()) {
                String usersProjectName = ((Group) usersProject).getValue();
                if (searchableGroup.equals(usersProjectName)) {
                    projectParticipants.add(user.getFullName());
                }
            }
        }
        return projectParticipants;
    }

}
