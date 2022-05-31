CREATE TABLE product (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    name varchar(55) NOT NULL,
    price BIGINT NOT NULL
);