-- liquibase formatted sql

-- changeset karmanov:00000000000012_add_table_token_rule
create sequence token_rule_seq;
create table token_rule
(
    id bigint default nextval('public.token_rule_seq') not null,
    part_of_speech varchar(16) not null,
    rule_name varchar(32) not null,
    target_part_of_speech varchar(16) not null,

    constraint token_rule_pk
        primary key (id)
);
alter sequence token_rule_seq owned by token_rule.id;
create unique index token_rule_part_of_speech_uindex on token_rule (part_of_speech);

comment on table token_rule is 'Список правил для формирования итогового слова от части речи';

comment on column token_rule.part_of_speech is 'Часть речи';
comment on column token_rule.rule_name is 'Название правила';
comment on column token_rule.target_part_of_speech is 'Итоговая часть речи';
