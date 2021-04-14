CREATE TABLE Role
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR (20)
);

create table User_Info
(
    id BIGSERIAL PRIMARY KEY,
    first_name varchar(100) NOT NULL,
    second_name varchar(100) NOT NULL,
    phone varchar(20)  NOT NULL,
    email varchar(100) NOT NULL,
    role_id INT,
    money_amount float8
);

ALTER TABLE User_Info ADD FOREIGN KEY (role_id) REFERENCES role(id);

ALTER TABLE adorder RENAME TO Advertisement_Order;

Create table image
(
    id BIGSERIAL PRIMARY KEY,
    advertisement_id int,
    main_image boolean
);
ALTER TABLE image
    ADD FOREIGN KEY (advertisement_id) REFERENCES advertisement (id);

ALTER TABLE Services_of_order
    ADD FOREIGN KEY (service_id) REFERENCES Service (id);

CREATE TABLE Services_of_Advertisement
(
    service_id int,
    advertisement_id int
);

ALTER TABLE Services_of_Advertisement
    ADD FOREIGN KEY (service_id) REFERENCES service (id);

ALTER TABLE Services_of_Advertisement
    ADD FOREIGN KEY (advertisement_id) REFERENCES advertisement (id);

CREATE TABLE Category_of_types
(
    category_id int,
    type_id int
);
ALTER TABLE Category_of_types
    ADD FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE Category_of_types
    ADD FOREIGN KEY (category_id) REFERENCES type (id);

ALTER TABLE category ADD orderable BOOLEAN;

CREATE TABLE Coordinates
(
    advertisement_id BIGSERIAL PRIMARY KEY,
    latitude float8,
    longitude float8
);
ALTER TABLE Coordinates ADD FOREIGN KEY (advertisement_id) REFERENCES Advertisement (id);

CREATE TABLE User_Favourites_Advertisements
(
    user_id int,
    advertisement_id int
);
ALTER TABLE User_Favourites_Advertisements
    ADD FOREIGN KEY (user_id) REFERENCES user_info (id);

ALTER TABLE User_Favourites_Advertisements
    ADD FOREIGN KEY (advertisement_id) REFERENCES advertisement (id);

ALTER TABLE advertisement add visible BOOLEAN default true;
