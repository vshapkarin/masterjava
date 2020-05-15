--liquibase formatted sql

--changeset vshapkarin:3
CREATE SEQUENCE msg_seq START 100000;

CREATE TABLE messages (
    id          INTEGER PRIMARY KEY DEFAULT nextval('msg_seq'),
    receivers   TEXT NOT NULL,
    copy        TEXT,
    subject     TEXT NOT NULL,
    body        TEXT NOT NULL,
    date_time   TIMESTAMP
)