# LOGGED AS tp2019
# PASS admin

DROP DATABASE IF EXISTS assign3;
CREATE DATABASE assign3;
USE assign3;

DROP TABLE IF EXISTS client;
CREATE TABLE client(
	id int PRIMARY KEY AUTO_INCREMENT,
    firstName varchar(30) NOT NULL,
    lastName varchar(30) NOT NULL,
    addressId int NOT NULL,
    email varchar(30) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS product;
CREATE TABLE product(
	id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(30) NOT NULL UNIQUE,
    unitPrice float NOT NULL,
    quantity int NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders(
	id int PRIMARY KEY AUTO_INCREMENT,
    clientId int NOT NULL,
    productId int NOT NULL,
    quantity int NOT NULL,
    price float NOT NULL,
    date DATETIME DEFAULT NOW(),
    receiptPath varchar(255) NOT NULL
);

DROP TABLE IF EXISTS address;
CREATE TABLE address(
	id int PRIMARY KEY AUTO_INCREMENT,
    street varchar(30) NOT NULL,
    number varchar(5) NOT NULL,
    flat varchar(5) DEFAULT '',
    apartment varchar(5) DEFAULT '',
    city varchar(30) NOT NULL,
    county varchar(30) NOT NULL,
    country varchar(30) NOT NULL,
    postalCode varchar(6) NOT NULL
);


ALTER TABLE client ADD FOREIGN KEY (addressId) REFERENCES address(id);
ALTER TABLE orders ADD FOREIGN KEY (clientId) REFERENCES client(id);
ALTER TABLE orders ADD FOREIGN KEY (productId) REFERENCES product(id);
