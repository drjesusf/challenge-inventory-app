CREATE DATABASE db_inventory;
use db_inventory;
CREATE TABLE products (
    id varchar(100) ,
    name varchar(50),
    quantity integer,
    price decimal(6,2)
);