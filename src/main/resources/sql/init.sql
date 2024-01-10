CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(255),
    firstname  VARCHAR(255),
    lastname   VARCHAR(255),
    birth_date DATE,
    role       VARCHAR(50)
);
CREATE TABLE companies
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

drop table users cascade;
drop table companies cascade;
drop table users_companies;