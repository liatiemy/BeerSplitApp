CREATE TABLE rounds(
    id_round ​integer NOT NULL PRIMARY KEY ​AUTOINCREMENT,
    name VARCHAR(255) NOT NULL,
    people INTEGER NOT NULL,
    total DOUBLE NOT NULL,
    totalPerPerson DOUBLE NOT NULL,
    tip VARCHAR(1) NOT NULL
);