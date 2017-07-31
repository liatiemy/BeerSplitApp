CREATE TABLE users (
    username VARCHAR(255) NOT NULL PRIMARY KEY,
    password VARCHAR(10) NOT NULL
);

CREATE TABLE ROUNDS(
    id_round ​integer NOT NULL PRIMARY KEY ​AUTOINCREMENT,
    name VARCHAR(255) NOT NULL,
    people INTEGER NOT NULL,
    total DOUBLE NOT NULL,
    totalPerPerson DOUBLE NOT NULL,
    tip VARCHAR(1) NOT NULL
);

CREATE TABLE EXPENSE(
    id_round ​integer NOT NULL,
    nameExpense VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INTEGER NOT NULL
);