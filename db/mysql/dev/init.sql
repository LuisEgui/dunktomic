-- Author: Luis Egui

-- Create the database/schema
create database if not exists dunktomic_db
    character set utf8mb4
    collate utf8mb4_general_ci;

-- Select the created database to be used
use dunktomic_db;

-- Create a data ingestor user, before this statement make sure
-- you have logged in with an admin user (root):
create user if not exists 'data_ingestor'@'%'
    identified with mysql_native_password by 'di_password';

grant all privileges on `dunktomic_db`.* TO 'data_ingestor'@'%';
grant trigger on `dunktomic_db`.* TO 'data_ingestor'@'%';

-- Create a backend user
create user if not exists 'dunktomic_backend'@'%'
    identified with mysql_native_password by 'backend_password';

grant all privileges on `dunktomic_db`.* TO 'dunktomic_backend'@'%';
grant trigger on `dunktomic_db`.* TO 'dunktomic_backend'@'%';

flush privileges;
