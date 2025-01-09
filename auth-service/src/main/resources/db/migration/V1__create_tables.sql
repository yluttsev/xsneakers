CREATE TABLE IF NOT EXISTS user_role
(
    id   varchar(255) PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users
(
    id            uuid PRIMARY KEY,
    first_name    varchar(255) NOT NULL,
    last_name     varchar(255),
    email         varchar(320) NOT NULL UNIQUE,
    password      varchar(255) NOT NULL,
    creation_date timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_blocked    boolean      NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id uuid         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id varchar(255) NOT NULL REFERENCES user_role (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS refresh_token
(
    id            uuid PRIMARY KEY,
    refresh_token text      NOT NULL UNIQUE,
    issued_at     timestamp NOT NULL,
    expires_at    timestamp NOT NULL,
    user_id       uuid      NOT NULL REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO user_role (id, name)
VALUES ('ROLE_USER', 'Пользователь'),
       ('ROLE_ADMIN', 'Администратор');