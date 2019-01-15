CREATE TABLE IF NOT EXISTS users
(
    id int NOT NULL AUTO_INCREMENT,
    login_id varchar(20) NOT NULL UNIQUE,
    password varchar(60) NOT NULL,
    name varchar(100) NOT NULL,
    PRIMARY KEY (id)
);
