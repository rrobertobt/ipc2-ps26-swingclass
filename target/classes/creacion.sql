/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  robertob
 * Created: Feb 24, 2026
 */

CREATE DATABASE ipc2tareas;

USE ipc2tareas;

CREATE TABLE users(
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE todos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    done BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_todos_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO users (username, password) VALUES
('roberto', '1234'),
('ana', 'abcd')
ON DUPLICATE KEY UPDATE username = username;

-- Tareas de ejemplo para roberto (id=1 si es primera vez)
INSERT INTO todos (user_id, title, done) VALUES
(1, 'Terminar práctica IPC2', FALSE),
(1, 'Subir repo a GitHub', TRUE);