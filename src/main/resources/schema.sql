CREATE DATABASE IF NOT EXISTS eissafaheem;

CREATE TABLE  IF NOT EXISTS users
(
    userId    VARCHAR(255) PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName  VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS otp
(
    otp_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email  VARCHAR(255) NOT NULL,
    otp    VARCHAR(6)   NOT NULL,
    expiry DATETIME         NOT NULL
);

CREATE TABLE IF NOT EXISTS keep
(
    keepId VARCHAR(255)  PRIMARY KEY,
    title  TEXT,
    description TEXT,
    backgroundColor VARCHAR(255)
);

