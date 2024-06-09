--liquibase formatted sql
--changeset Maria Belikova:1
CREATE TABLE _station (
     id SERIAL PRIMARY KEY,
     station VARCHAR(50) NOT NULL
);

--changeset Maria Belikova:2
INSERT INTO _station (station) VALUES
    ('Saint Petersburg'),
    ('Moscow'),
    ('London'),
    ('LA'),
    ('Paris');
