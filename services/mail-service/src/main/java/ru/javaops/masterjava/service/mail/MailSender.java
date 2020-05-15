package ru.javaops.masterjava.service.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import ru.javaops.masterjava.config.Configs;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.service.mail.persist.dao.MessageDao;
import ru.javaops.masterjava.service.mail.persist.model.Message;

import java.util.List;

@Slf4j
public class MailSender {

    public static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Config mail = Configs.getConfig("mail.conf", "mail");
    private static final MessageDao messageDao = DBIProvider.getDao(MessageDao.class);

    @SneakyThrows
    static void sendMail(@NonNull List<Addressee> to,
                         @NonNull List<Addressee> cc,
                         @NonNull String subject,
                         @NonNull String body) {

        Email email = new SimpleEmail();
        Message message = new Message().setTo(to)
                .setCc(cc)
                .setSubject(subject)
                .setBody(body);

        configureEmail(email);
        setReceiversAndMessage(message, email);
        email.send();
        messageDao.insert(message);

        log.info("Send mail to \'" + to + "\' cc \'" + cc + "\' subject \'" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));
    }

    private static void configureEmail(Email email) throws EmailException {
        email.setHostName(mail.getString("host"));
        email.setSmtpPort(mail.getInt("port"));
        email.setAuthentication(mail.getString("username"), mail.getString("password"));
        email.setSSLOnConnect(mail.getBoolean("useSSL"));
        email.setStartTLSEnabled(mail.getBoolean("useTLS"));
        email.setDebug(mail.getBoolean("debug"));
        email.setFrom(mail.getString("fromName"));
    }

    private static void setReceiversAndMessage(Message message, Email email) throws EmailException {
        email.setSubject(message.getSubject());
        email.setMsg(message.getBody());
        for (Addressee toAddress : message.getTo()) {
            email.addTo(toAddress.getEmail(), toAddress.getName());
        }
        for (Addressee ccAddress : message.getCc()) {
            email.addCc(ccAddress.getEmail(), ccAddress.getName());
        }
    }

}
