

CREATE DATABASE IF NOT EXISTS schedule_app;
USE schedule_app;
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255)
);

INSERT INTO users (name) VALUES ('Tanaka');