package ru.javaops.masterjava.service.mail;

import com.google.common.collect.ImmutableSet;

public class MailWSClientMain {
    public static void main(String[] args) {
        MailWSClient.sendToGroup(
                ImmutableSet.of(new Addressee("To <vshapkarin@mail.ru>")),
                ImmutableSet.of(new Addressee("Copy <vshapkarin@gmail.com>")), "Subject", "Body");
    }
}