package ru.javaops.masterjava.service.mail.persist.bind;

import lombok.SneakyThrows;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;
import ru.javaops.masterjava.service.mail.persist.model.Message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ru.javaops.masterjava.service.mail.MailSender.objectMapper;

@BindingAnnotation(BindMessage.BindArrayBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindMessage {

    class BindArrayBinderFactory implements BinderFactory<BindMessage> {

        @Override
        public Binder<BindMessage, Message> build(BindMessage annotation) {
            return new Binder<BindMessage, Message>() {
                @SneakyThrows
                @Override
                public void bind(SQLStatement<?> sqlStatement, BindMessage bindMessage, Message message) {
                    String receivers = objectMapper.writeValueAsString(message.getTo());
                    String copy = objectMapper.writeValueAsString(message.getCc());
                    sqlStatement.bind("receivers", receivers)
                            .bind("copy", copy)
                            .bind("subject", message.getSubject())
                            .bind("body", message.getBody())
                            .bind("date_time", message.getDateTime());
                }
            };
        }
    }
}
