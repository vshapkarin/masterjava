package ru.javaops.masterjava.service.mail.data;

import ru.javaops.masterjava.service.mail.Addressee;
import ru.javaops.masterjava.service.mail.persist.model.Message;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

public class MessageTestData {

    public static Message MESSAGE_1;
    public static Message MESSAGE_2;

    public static void init() {
        MESSAGE_1 = new Message()
                .setTo(Arrays.asList(
                        new Addressee("test1@mail.com", "test1"),
                        new Addressee("Test1@google.by", "Test1")))
                .setCc(Arrays.asList(
                        new Addressee("testCopy1@mail.rt", "testCopy1"),
                        new Addressee("testCopy1@google.df", "TestCopy1")))
                .setBody("Test message #1")
                .setSubject("Test subject #1");

        MESSAGE_2 = new Message()
                .setTo(Collections.singletonList(
                        new Addressee("test2@mail.com", "test2")))
                .setCc(Collections.singletonList(
                        new Addressee("testCopy2@mail.rt", "testCopy2")))
                .setBody("Test message #2")
                .setSubject("Test subject #2");
    }

}
