package ru.javaops.masterjava.persist;

import ru.javaops.masterjava.persist.dao.CityDao;

public class CityTestData {

    public static void setUp() {
        CityDao dao = DBIProvider.getDao(CityDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
                 dao.insert("spb", "Санкт-Петербург");
                 dao.insert("mow", "Москва");
                 dao.insert("kiv", "Киев");
                 dao.insert("mnsk", "Минск");
        });
    }

}
