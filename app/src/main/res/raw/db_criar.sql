CREATE TABLE users (
    username VARCHAR(255) NOT NULL PRIMARY KEY,
    password VARCHAR(10) NOT NULL
);


CREATE TABLE expenses(
    id_round ​integer NOT NULL,
    nameExpense VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INTEGER NOT NULL
);


CREATE TABLE rounds(
    id_round ​integer NOT NULL,
    name VARCHAR(255) NOT NULL,
    people INTEGER NOT NULL,
    total DOUBLE NOT NULL,
    totalPerPerson DOUBLE NOT NULL,
    tip VARCHAR(1) NOT NULL
);