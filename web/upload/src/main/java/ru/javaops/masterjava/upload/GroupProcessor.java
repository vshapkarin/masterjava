package ru.javaops.masterjava.upload;

import lombok.extern.slf4j.Slf4j;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.GroupDao;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.type.GroupType;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.XMLStreamException;

@Slf4j
public class GroupProcessor {

    public void process(StaxStreamProcessor processor, GroupDao groupDao, int projectId) throws XMLStreamException {
        while(processor.startElement("Group", "Project")) {
            String name = processor.getAttribute("name");
            GroupType type = GroupType.valueOf(processor.getAttribute("type"));
            Group group = new Group(name, type, projectId);

            log.info("Insert group {}", group);
            groupDao.insert(group);
        }
    }

}
