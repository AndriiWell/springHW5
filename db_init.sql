This files uses only in run_docker_commands.ps1 and instruction step-by-step README.md
-- create schema if not exists `hometask5_spring`;
-- use `hometask5_spring`;
-- grant all privileges on hometask5_spring.* to 'user1'@'%';

-- Attempt to with with liquibase via Dockers....
-- create schema if not exists `${MYSQLDB_DATABASE}`;
-- use `${MYSQLDB_DATABASE}`;
--
-- create user if not exists '${MYSQLDB_USER}'@'%' identified by '${MYSQLDB_PASSWORD}';
-- grant all privileges on `${MYSQLDB_DATABASE}`.* to '${MYSQLDB_USER}'@'%';
