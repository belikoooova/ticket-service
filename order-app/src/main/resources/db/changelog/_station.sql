--liquibase formatted sql
--changeset Maria Belikova:1
CREATE TABLE _order (
     id SERIAL PRIMARY KEY,
     user_id INT NOT NULL,
     from_station_id INT NOT NULL,
     to_station_id INT NOT NULL,
     status INT NOT NULL,
     created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (from_station_id) REFERENCES _station(id),
     FOREIGN KEY (to_station_id) REFERENCES _station(id)
);
