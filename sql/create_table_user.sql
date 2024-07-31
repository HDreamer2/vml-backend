create table user
(
    id          bigint auto_increment
        primary key,
    username    varchar(32) not null,
    password    varchar(64) not null,
    create_time datetime    null,
    update_time datetime    null,
    constraint user_pk
        unique (username)
);


