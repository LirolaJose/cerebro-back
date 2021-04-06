ALTER TABLE service ALTER COLUMN  price DROP NOT NULL ;

INSERT INTO service  (name)
VALUES ('Delivery'),
       ('Packing'),
       ('Furnishings'),
       ('Help with documents registration'),
       ('Test drive'),
       ('Insurance');

INSERT INTO services_of_category(category_id, services_id)
VALUES (1, 3),
       (1, 4),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 1),
       (3, 2),
       (5, 1),
       (5, 2);
