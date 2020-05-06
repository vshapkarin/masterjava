package ru.javaops.masterjava.persist;

import ru.javaops.masterjava.persist.dao.ProjectDao;

public class ProjectTestData {

    public static void setUp() {
        ProjectDao dao = DBIProvider.getDao(ProjectDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            dao.insertProject("startjava01", "startjava");
            dao.insertProject("basejava01", "basejava");
            dao.insertProject("topjava01", "topjava");
            dao.insertProject("masterjava01", "masterjava");
        });
    }

}
