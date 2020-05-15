package ru.javaops.masterjava.service.mail.persist.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import ru.javaops.masterjava.persist.dao.AbstractDao;
import ru.javaops.masterjava.service.mail.Addressee;
import ru.javaops.masterjava.service.mail.persist.bind.BindMessage;
import ru.javaops.masterjava.service.mail.persist.mapper.MessageMapper;
import ru.javaops.masterjava.service.mail.persist.model.Message;

import java.time.LocalDateTime;
import java.util.List;

@RegisterMapper(MessageMapper.class)
public abstract class MessageDao implements AbstractDao {

    @SqlUpdate("TRUNCATE messages")
    @Override
    public abstract void clean();

    @SqlUpdate("INSERT INTO messages (receivers, copy, subject, body, date_time) " +
            "VALUES (:receivers, :copy, :subject, :body, :date_time)")
    public abstract void insert(@BindMessage Message message);

    @SqlQuery("SELECT * FROM messages")
    public abstract List<Message> getAll();

}
