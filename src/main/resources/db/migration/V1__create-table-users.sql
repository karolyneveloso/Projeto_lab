-- Active: 1767138022011@@127.0.0.1@5432@meudb
CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    name_user VARCHAR(100) NOT NULL,
    cpf VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(300) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(80) NOT NULL,

    CONSTRAINT check_role
        CHECK (role IN ('ADMIN','DENTIST'))
);
