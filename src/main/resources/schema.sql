DROP TABLE IF EXISTS word;
CREATE TABLE word (
    id        INTEGER AUTO_INCREMENT PRIMARY KEY,
    word      VARCHAR(255) NOT NULL,
    groupname VARCHAR(255) NOT NULL
);