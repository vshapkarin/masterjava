package ru.javaops.masterjava.service.mail.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.AbstractDaoTest;
import ru.javaops.masterjava.service.mail.data.MessageTestData;
import ru.javaops.masterjava.service.mail.persist.dao.MessageDao;
import ru.javaops.masterjava.service.mail.persist.model.Message;

import java.util.Arrays;
import java.util.List;

import static ru.javaops.masterjava.service.mail.data.MessageTestData.MESSAGE_1;
import static ru.javaops.masterjava.service.mail.data.MessageTestData.MESSAGE_2;

public class MessageDaoTest extends AbstractDaoTest<MessageDao> {

    public MessageDaoTest() {
        super(MessageDao.class);
    }

    @BeforeClass
    public static void init() {
        MessageTestData.init();
    }

    @Before
    public void setUp() {
        MessageDao dao = DBIProvider.getDao(MessageDao.class);
        dao.clean();
    }

    @Test
    public void insert() {
        dao.insert(MESSAGE_1);
        dao.insert(MESSAGE_2);

        int expectedSize = 2;
        int actualSize = dao.getAll().size();

        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void getAll() {
        dao.insert(MESSAGE_1);
        dao.insert(MESSAGE_2);

        List<Message> expectedMessages = Arrays.asList(MESSAGE_1, MESSAGE_2);
        List<Message> actualMessages = dao.getAll();
        actualMessages.forEach(m -> m.setId(0));

        Assert.assertEquals(expectedMessages, actualMessages);
    }

}
