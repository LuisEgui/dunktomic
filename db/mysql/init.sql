-- Author: Luis Egui

-- Create the database/schema
create database if not exists dunktomic_db 
    character set utf8mb4 
    collate utf8mb4_general_ci;

-- Select the created database to be used 
use dunktomic_db;

-- Create a common user, before this statement make sure
-- you have logged in with an admin user (root):
create user if not exists 'user'@'%' 
    identified with mysql_native_password by '1234';
grant all privileges on `dunktomic_db`.* TO 'user'@'%';

flush privileges;

-- Aux: show active users in the db:
-- select user from mysql.user;pro