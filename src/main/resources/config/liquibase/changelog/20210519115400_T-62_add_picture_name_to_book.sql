-- liquibase formatted sql

-- changeset karmanov:T-62-1:20210519115400
alter table book add picture_name varchar(255) null;
