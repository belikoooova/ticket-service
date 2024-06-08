--liquibase formatted sql
--changeset Maria Belikova:1
CREATE TABLE session (
     id SERIAL PRIMARY KEY,
     user_id INT NOT NULL,
     token VARCHAR(255) NOT NULL,
     expires TIMESTAMP WITHOUT TIME ZONE NOT NULL,
     FOREIGN KEY (user_id) REFERENCES _user(id)
);

--changeset Maria Belikova:2
DROP TABLE session;

CREATE TABLE _session (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    expires TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES _user(id)
);
