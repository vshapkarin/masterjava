package ru.javaops.masterjava.persist.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javaops.masterjava.persist.CityTestData;
import ru.javaops.masterjava.persist.ProjectTestData;
import ru.javaops.masterjava.persist.UserTestData;

public class ProjectDaoTest extends AbstractDaoTest<ProjectDao> {

    public ProjectDaoTest() {
        super(ProjectDao.class);
    }

    @Before
    public void setUp() {
        CityTestData.setUp();
        UserTestData.setUp();
        ProjectTestData.setUp();
    }

    @Test
    public void insertProjectTest() {
        dao.insertProject("masterjava04", "masterjava");

        int groupsNumber = dao.getGroupsNumber();

        Assert.assertEquals(5, groupsNumber);
    }

    @Test
    public void insertParticipantTest() {
        dao.insertParticipant(1, "startjava01");
        dao.insertParticipant(1, "basejava01");
        dao.insertParticipant(2, "topjava01");

        int participantsNumber = dao.getParticipantsNumber();

        Assert.assertEquals(3, participantsNumber);
    }

}
