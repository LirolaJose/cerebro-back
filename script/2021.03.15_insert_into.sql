insert into type (name)
VALUES ('buy'),
       ('sale'),
       ('service');
insert into type (name)
VALUES ('work');

insert into category (name)
VALUES ('property'),
       ('cars'),
       ('furniture'),
       ('vacancies');

insert into status (name)
VALUES ('actively'),
       ('sold');

insert into advertisement (title, text, price, address, publication_time,  category_id, type_id, status_id)
VALUES ('sell a car', 'lada 2110, 1500 $', 1500.0,  'Voronezh', '15-03-2021', 2, 2, 1),
       ('sell a table', 'good table for working', 50.0, 'Voronezh', '15-03-2021', 3, 2, 1),
       ('Manager', 'need manager to a bank, salary 1000 $', null, 'Voronezh', '14-03-2021', 4, 4, 1);