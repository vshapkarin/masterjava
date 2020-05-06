DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS project_participants;
DROP TABLE IF EXISTS project_groups;
DROP SEQUENCE IF EXISTS user_seq;
DROP SEQUENCE IF EXISTS groups_seq;
DROP SEQUENCE IF EXISTS cities_seq;
DROP TYPE IF EXISTS user_flag;
DROP TYPE IF EXISTS project_name;

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');

CREATE TYPE project_name AS ENUM ('startjava', 'basejava', 'topjava', 'masterjava');

CREATE SEQUENCE user_seq START 100000;

CREATE SEQUENCE groups_seq START 100000;

CREATE SEQUENCE cities_seq START 100000;

CREATE TABLE cities (
                        id          INTEGER PRIMARY KEY DEFAULT nextval('cities_seq'),
                        id_name     TEXT UNIQUE NOT NULL,
                        city_name   TEXT NOT NULL
);

CREATE TABLE users (
  id             INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  full_name      TEXT NOT NULL,
  email          TEXT NOT NULL,
  flag           user_flag NOT NULL,
  city_id_name   TEXT REFERENCES cities(id_name) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE project_groups (
    id         INTEGER PRIMARY KEY DEFAULT nextval('groups_seq'),
    group_name TEXT UNIQUE NOT NULL,
    project    project_name NOT NULL
);

CREATE TABLE project_participants (
    user_id             INTEGER REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
    project_group_id    INTEGER REFERENCES project_groups(id) ON UPDATE CASCADE,
    CONSTRAINT project_participant_id PRIMARY KEY (user_id, project_group_id)
);

CREATE UNIQUE INDEX email_idx ON users (email);