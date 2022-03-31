create table audit_history
(
    id              character varying(100)  not null,
    source_id       character varying(100)  not null,
    source_name     character varying(50)   not null,
    event           character varying(50)   not null,
    data            character varying(4000) not null,
    user_id         character varying(20)   not null,
    created_ts      timestamp with time zone default current_timestamp not null,
    comment         character varying(100)  not null,

    constraint "audit_history_pkey" primary key (id)
) tablespace pg_default;
