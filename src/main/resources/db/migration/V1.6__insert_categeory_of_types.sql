ALTER TABLE Category_of_types
    ADD FOREIGN KEY (type_id) REFERENCES type (id);

ALTER TABLE category_of_types ADD PRIMARY KEY (category_id, type_id);
ALTER TABLE services_of_advertisement ADD PRIMARY KEY (service_id, advertisement_id);
ALTER TABLE user_favourites_advertisements ADD PRIMARY KEY (user_id, advertisement_id);

INSERT INTO category_of_types(category_id, type_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (3, 1),
       (3, 2),
       (4, 4),
       (5, 1),
       (5, 2),
       (6, 4);