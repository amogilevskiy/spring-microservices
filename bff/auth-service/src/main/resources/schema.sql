DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_authorities;


CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,
    username   VARCHAR(255) UNIQUE,
    password   VARCHAR(255)
);

CREATE TABLE user_authorities
(
    id        BIGSERIAL PRIMARY KEY,
    user_id   BIGINT NOT NULL,
    authority VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
