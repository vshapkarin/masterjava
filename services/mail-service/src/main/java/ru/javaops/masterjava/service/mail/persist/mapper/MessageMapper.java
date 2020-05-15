package ru.javaops.masterjava.service.mail.persist.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import ru.javaops.masterjava.service.mail.Addressee;
import ru.javaops.masterjava.service.mail.persist.model.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javaops.masterjava.service.mail.MailSender.objectMapper;

public class MessageMapper implements ResultSetMapper<Message> {

    @SneakyThrows
    @Override
    public Message map(int i, ResultSet resultSet, StatementContext statementContext) {
        int id = resultSet.getInt("id");

        String receivers = resultSet.getString("receivers");
        List<Addressee> to = objectMapper.readValue(receivers, new TypeReference<List<Addressee>>() {});

        String copy = resultSet.getString("copy");
        List<Addressee> cc = objectMapper.readValue(copy, new TypeReference<List<Addressee>>() {});

        String subject = resultSet.getString("subject");
        String body = resultSet.getString("body");
        LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();
        return new Message(id, to, cc, subject, body, dateTime);
    }
}
