-- DROP DATABASE IF EXISTS dvdLibrary;

CREATE DATABASE dvdLibrary;

USE dvdLibrary;

CREATE TABLE dvds (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    releaseYear INT,
    mpaaRating VARCHAR(50),
    director VARCHAR(255),
    studio VARCHAR(255),
    userRating VARCHAR(50)
);
