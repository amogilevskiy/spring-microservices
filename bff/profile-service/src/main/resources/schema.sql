DROP TABLE IF EXISTS profiles CASCADE;


CREATE TABLE profiles
(
    id         BIGINT PRIMARY KEY,
    username   VARCHAR(255) UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL
);