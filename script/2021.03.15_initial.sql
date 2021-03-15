CREATE DATABASE board;
CREATE TABLE Advertisement (
                                 id SERIAL PRIMARY KEY,
                                 title varchar (100) NOT NULL,
                                 text varchar (2000),
                                 price float8,
                                 image bytea,
                                 address varchar (150),
                                 publication_time timestamp NOT NULL,
                                 end_time timestamp NOT NULL,
                                 category_id int NOT NULL,
                                 type_id int NOT NULL,
                                 status_id int NOT NULL,
                                 owner_id int NOT NULL
);

CREATE TABLE Category (
                            id SERIAL PRIMARY KEY,
                            name varchar (50) NOT NULL UNIQUE
);

CREATE TABLE Status
(    id SERIAL PRIMARY KEY,
     name varchar (50) NOT NULL UNIQUE
);

CREATE TABLE Service (
                           id SERIAL PRIMARY KEY,
                           name varchar (50) NOT NULL UNIQUE,
                           price float8 NOT NULL
);

CREATE TABLE Type (
                        id SERIAL PRIMARY KEY,
                        name varchar (50) NOT NULL UNIQUE
);

CREATE TABLE Contact_Info (
                                id SERIAL PRIMARY KEY,
                                name varchar (100) NOT NULL,
                                phone varchar(20) UNIQUE NOT NULL,
                                email varchar (100) UNIQUE NOT NULL
);

CREATE TABLE AdOrder (
                         id SERIAL PRIMARY KEY,
                         order_time timestamp not null,
                         total_price float8,
                         advertisement_id int NOT NULL,
                         contact_info_id int NOT NULL
);

CREATE TABLE Services_of_category (
                                        category_id int not null ,
                                        services_id int not null
);

CREATE TABLE Services_of_order (
                                     services_id int not null ,
                                     adOrder_id int not null
);

ALTER TABLE Advertisement ADD FOREIGN KEY (category_id) REFERENCES Category (id);

ALTER TABLE Advertisement ADD FOREIGN KEY (type_id) REFERENCES Type (id);

ALTER TABLE Advertisement ADD FOREIGN KEY (owner_id) REFERENCES Contact_Info (id);

ALTER TABLE Advertisement ADD FOREIGN KEY (status_id) REFERENCES Status(id);

ALTER TABLE AdOrder ADD FOREIGN KEY (advertisement_id) REFERENCES Advertisement (id);

ALTER TABLE AdOrder ADD FOREIGN KEY (contact_info_id) REFERENCES Contact_Info (id);

ALTER TABLE Services_of_category ADD FOREIGN KEY (category_id) REFERENCES Category (id);

ALTER TABLE Services_of_category ADD FOREIGN KEY (services_id) REFERENCES Service (id);

ALTER TABLE Services_of_order ADD FOREIGN KEY (adOrder_id) REFERENCES AdOrder (id);

ALTER TABLE Services_of_order ADD FOREIGN KEY (services_id) REFERENCES Service(id);


