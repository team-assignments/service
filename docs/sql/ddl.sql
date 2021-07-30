create sequence hibernate_sequence start with 1 increment by 1;
create table group_team
(
    group_id      bigint       not null,
    creation_date timestamp    not null,
    name          varchar(255) not null,
    owner_id      bigint       not null,
    primary key (group_id)
);
create table task
(
    task_id            bigint    not null,
    completed          boolean   not null,
    confirmed_complete boolean   not null,
    due_date           timestamp,
    post_date          timestamp not null,
    group_id           bigint    not null,
    user_id            bigint,
    primary key (task_id)
);
create table user_group
(
    group_id bigint not null,
    user_id  bigint not null,
    primary key (group_id, user_id)
);
create table user_profile
(
    user_id       bigint       not null,
    connected     timestamp    not null,
    creation_date timestamp    not null,
    display_name  varchar(255) not null,
    oauth_key     varchar(255) not null,
    primary key (user_id)
);
alter table group_team
    add constraint UK_esbaurrhfbmhgc2bcdftusio5 unique (name);
create
unique index UK7tp6jirbig19oxna6gwqanvkj on user_profile (display_name);
alter table user_profile
    add constraint UK_6f815wi5o4jq8p1q1w63o4mhd unique (oauth_key);
alter table group_team
    add constraint FKr18iutgh98i7ugishg5b20mmi foreign key (owner_id) references user_profile;
alter table task
    add constraint FKhdg487k7o9qdoy40g01xeuvd4 foreign key (group_id) references group_team;
alter table task
    add constraint FK8pjfc87le6wgda5b2r29gpdbf foreign key (user_id) references user_profile;
alter table user_group
    add constraint FKlwrp57w1uwa5uklyto7oppa40 foreign key (user_id) references user_profile;
alter table user_group
    add constraint FKgvw81e1x8bxadg1fox1xbqb95 foreign key (group_id) references group_team;
