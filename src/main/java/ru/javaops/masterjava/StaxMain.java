package ru.javaops.masterjava;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StaxMain {
    public static void main(String[] args) throws IOException, XMLStreamException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please, type a project group:");
            String searchableGroup = scanner.next();
            if ("exit".equalsIgnoreCase(searchableGroup)) {
                break;
            }

            List<String> projectParticipants = getProjectParticipants(searchableGroup);
            System.out.println("List of " + searchableGroup + " participants\n" + projectParticipants + "\n");
        }
    }

    private static List<String> getProjectParticipants(String searchableGroup) throws XMLStreamException, IOException {
        List<String> projectParticipants = new ArrayList<>();
        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(Resources.getResource("payload.xml").openStream())) {
            String attribute;
            while ((attribute = processor.getAttributes("User", "involvedProjects")) != null) {
                if (attribute.contains(searchableGroup)) {
                    String participantEmail = processor.getAttribute("email");
                    String participantName = processor.getElementValue("fullName");
                    projectParticipants.add(participantName + "/" + participantEmail);
                }
            }
        }
        return projectParticipants;
    }

}
