package ru.javaops.masterjava.persist.dao;

import org.junit.BeforeClass;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.DBITestProvider;
import ru.javaops.masterjava.persist.UserTestData;

public abstract class AbstractDaoTest<DAO extends AbstractDao> {
    static {
        DBITestProvider.initDBI();
    }

    protected DAO dao;

    protected AbstractDaoTest(Class<DAO> daoClass) {
        this.dao = DBIProvider.getDao(daoClass);
    }

    @BeforeClass
    public static void init() throws Exception {
        UserTestData.init();
    }

}
