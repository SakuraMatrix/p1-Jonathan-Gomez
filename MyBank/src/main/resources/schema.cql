CREATE KEYSPACE IF NOT EXISTS MyBank WITH replication = {'class': 'SimpleStrategy', 'replication_factor':1};

USE MyBank;

CREATE TABLE IF NOT EXISTS accounts (
    a_id int,
    a_name text,
    amount double,
    PRIMARY KEY (a_id)
);

CREATE TABLE IF NOT EXISTS customers (
    c_id int,
    c_name text,
    account_id int,
    PRIMARY KEY (c_id, c_name)
);