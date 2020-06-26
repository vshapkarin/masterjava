package ru.javaops.masterjava.service.mail;

import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import ru.javaops.web.WebStateException;

import java.util.Collections;

@Slf4j
public class MailWSClientMain {
    public static void main(String[] args) throws WebStateException {
        String state = MailWSClient.sendToGroup(
                ImmutableSet.of(new Addressee("To <masterjava@javaops.ru>")),
                ImmutableSet.of(new Addressee("Copy <masterjava@javaops.ru>")), "Subject", "Body",
                Collections.emptyList(),
                Collections.emptyList());
        System.out.println(state);
    }
}