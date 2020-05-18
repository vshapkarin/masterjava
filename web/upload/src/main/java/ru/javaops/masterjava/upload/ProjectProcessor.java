package ru.javaops.masterjava.upload;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.GroupDao;
import ru.javaops.masterjava.persist.dao.ProjectDao;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.Project;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.XMLStreamException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ProjectProcessor {
    private final GroupProcessor groupProcessor = new GroupProcessor();
    private final ProjectDao projectDao = DBIProvider.getDao(ProjectDao.class);
    private final GroupDao groupDao = DBIProvider.getDao(GroupDao.class);

    public Map<String, Group> process(StaxStreamProcessor processor) throws XMLStreamException {
        while(processor.startElement("Project", "Projects")) {
            String name = processor.getAttribute("name");
            String description = processor.getElementValue("description");
            Project project = new Project(name, description);

            log.info("Insert project {}", project);
            projectDao.insert(project);

            groupProcessor.process(processor, groupDao, project.getId());
        }
        return groupDao.getAsMap();
    }

}
