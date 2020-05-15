package ru.javaops.masterjava.service.mail.persist.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.javaops.masterjava.service.mail.Addressee;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Message {
    
    private int id;

    private List<Addressee> to;

    private List<Addressee> cc;

    private String subject;

    private String body;

    private LocalDateTime dateTime = LocalDateTime.now();

}
