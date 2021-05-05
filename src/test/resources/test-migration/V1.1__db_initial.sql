create table if not exists category
(
    id bigserial not null
        constraint category_pkey
            primary key,
    name varchar(50) not null
        constraint category_name_key
            unique,
    orderable boolean
);

alter table category owner to postgres;

create table if not exists status
(
    id bigserial not null
        constraint status_pkey
            primary key,
    name varchar(50) not null
        constraint status_name_key
            unique
);

alter table status owner to postgres;

create table if not exists service
(
    id bigserial not null
        constraint service_pkey
            primary key,
    name varchar(50) not null
        constraint service_name_key
            unique,
    price double precision not null
);

alter table service owner to postgres;

create table if not exists type
(
    id bigserial not null
        constraint type_pkey
            primary key,
    name varchar(50) not null
        constraint type_name_key
            unique
);

alter table type owner to postgres;

create table if not exists services_of_category
(
    category_id bigint not null
        constraint services_of_category_category_id_fkey
            references category,
    service_id bigint not null
        constraint services_of_category_services_id_fkey
            references service,
    constraint services_of_category_pkey
        primary key (category_id, service_id)
);

alter table services_of_category owner to postgres;

create table if not exists role
(
    id bigserial not null
        constraint role_pkey
            primary key,
    name varchar(20)
);

alter table role owner to postgres;

create table if not exists user_info
(
    id bigserial not null
        constraint user_info_pkey
            primary key,
    first_name varchar(100) not null,
    second_name varchar(100) not null,
    phone varchar(20) not null,
    email varchar(100) not null
        constraint user_info_email_key
            unique,
    role_id bigint default 2
        constraint user_info_role_id_fkey
            references role,
    money_amount double precision default 0.0,
    password varchar(100)
);

alter table user_info owner to postgres;

create table if not exists advertisement
(
    id bigserial not null
        constraint advertisement_pkey
            primary key,
    title varchar(100) not null,
    text varchar(2000),
    price double precision default 0.0,
    publication_time timestamp,
    expired_time timestamp,
    category_id bigint
        constraint advertisement_category_id_fkey
            references category,
    type_id bigint not null
        constraint advertisement_type_id_fkey
            references type,
    status_id bigint
        constraint advertisement_status_id_fkey
            references status,
    visible boolean default true,
    owner_id bigint
        constraint advertisement_user_id_fkey
            references user_info
);

alter table advertisement owner to postgres;

create table if not exists advertisement_order
(
    id bigserial not null
        constraint adorder_pkey
            primary key,
    order_time timestamp not null,
    total_price double precision,
    advertisement_id bigint not null
        constraint adorder_advertisement_id_fkey
            references advertisement,
    customer_id bigint not null
        constraint advertisement_order_seller_id_fkey
            references user_info
);

alter table advertisement_order owner to postgres;

create table if not exists services_of_order
(
    service_id bigint not null
        constraint services_of_order_services_id_fkey
            references service,
    order_id bigint not null
        constraint services_of_order_adorder_id_fkey
            references advertisement_order,
    constraint services_of_order_pkey
        primary key (service_id, order_id)
);

alter table services_of_order owner to postgres;

create table if not exists image
(
    id bigserial not null
        constraint image_pkey
            primary key,
    advertisement_id bigint
        constraint image_advertisement_id_fkey
            references advertisement,
    main_image boolean,
    image_bytes bytea
);

alter table image owner to postgres;

create table if not exists services_of_advertisement
(
    service_id bigint not null
        constraint services_of_advertisement_service_id_fkey
            references service,
    advertisement_id bigint not null
        constraint services_of_advertisement_advertisement_id_fkey
            references advertisement,
    constraint services_of_advertisement_pkey
        primary key (service_id, advertisement_id)
);

alter table services_of_advertisement owner to postgres;

create table if not exists category_of_types
(
    category_id bigint not null
        constraint category_of_types_category_id_fkey1
            references category,
    type_id bigint not null
        constraint category_of_types_type_id_fkey
            references type,
    constraint category_of_types_pkey
        primary key (category_id, type_id)
);

alter table category_of_types owner to postgres;

create table if not exists user_favourites_advertisements
(
    user_id bigint not null
        constraint user_favourites_advertisements_user_id_fkey
            references user_info,
    advertisement_id bigint not null
        constraint user_favourites_advertisements_advertisement_id_fkey
            references advertisement,
    constraint user_favourites_advertisements_pkey
        primary key (user_id, advertisement_id)
);

alter table user_favourites_advertisements owner to postgres;

create table if not exists coordinates
(
    advertisement_id bigserial not null
        constraint coordinates_pkey
            primary key
        constraint coordinates_advertisement_id_fkey
            references advertisement,
    latitude double precision,
    longitude double precision
);

alter table coordinates owner to postgres;

create table if not exists flyway_schema_history
(
    installed_rank integer not null
        constraint flyway_schema_history_pk
            primary key,
    version varchar(50),
    description varchar(200) not null,
    type varchar(20) not null,
    script varchar(1000) not null,
    checksum integer,
    installed_by varchar(100) not null,
    installed_on timestamp default now() not null,
    execution_time integer not null,
    success boolean not null
);

alter table flyway_schema_history owner to postgres;

create index if not exists flyway_schema_history_s_idx
    on flyway_schema_history (success);

