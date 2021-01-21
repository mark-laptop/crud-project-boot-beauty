CREATE TABLE users
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    nickname   VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255) UNIQUE NOT NULL,
    last_name  VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    age        TINYINT
);
CREATE INDEX IX_users_nickname ON users (nickname);
CREATE INDEX IX_users_email ON users (email);

CREATE TABLE roles
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);
CREATE INDEX IX_roles_name ON roles (name);

CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id),
    UNIQUE (user_id, role_id)
);

INSERT INTO users (id, nickname, first_name, last_name, password, email, age)
# 123
VALUES (1, 'user_1@gmail.com', 'Ivan', 'Ivanov', '$2y$12$57MhjlAQd0AlfYLt1MqWYOAwq/z/vXMwN4TKPOWwXTVPw7z8eTcsG', 'user_1@gmail.com', 30),
# admin
       (2, 'admin@gmail.com', 'Petr', 'Petrov', '$2y$12$kp49xTAg1BDn64.9Yr6v4..A74nhXP84m2GMLzTEVSvH/MHqFcrGK', 'admin@gmail.com', 31);

INSERT INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 2),
       (2, 2),
       (2, 1);