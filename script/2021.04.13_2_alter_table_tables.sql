alter table user_info alter column money_amount set default 0.0;
alter table advertisement drop column image;
alter table advertisement drop column address;
alter table advertisement RENAME COLUMN end_time TO expired_time;
alter table advertisement_order rename column contact_info_id to seller_id;
alter table services_of_order rename column adorder_id to order_id;

INSERT INTO Role (name)
VALUES ('ADMIN'),
       ('USER'),
       ('ANONYMOUS');

ALTER TABLE advertisement ALTER COLUMN id TYPE bigint;
ALTER TABLE advertisement_order ALTER COLUMN id TYPE bigint;
ALTER TABLE category ALTER COLUMN id TYPE bigint;
ALTER TABLE coordinates ALTER COLUMN id TYPE bigint;
ALTER TABLE image ALTER COLUMN id TYPE bigint;
ALTER TABLE role ALTER COLUMN id TYPE bigint;
ALTER TABLE service ALTER COLUMN id TYPE bigint;
ALTER TABLE status ALTER COLUMN id TYPE bigint;
ALTER TABLE type ALTER COLUMN id TYPE bigint;
ALTER TABLE contact_info ALTER COLUMN id TYPE bigint;

ALTER TABLE advertisement ALTER COLUMN category_id TYPE bigint;
ALTER TABLE advertisement ALTER COLUMN type_id TYPE bigint;
ALTER TABLE advertisement ALTER COLUMN status_id TYPE bigint;
ALTER TABLE advertisement ALTER COLUMN owner_id TYPE bigint;

ALTER TABLE Advertisement_Order ALTER COLUMN seller_id TYPE bigint;
ALTER TABLE Advertisement_Order ALTER COLUMN advertisement_id TYPE bigint;

ALTER TABLE category_of_types ALTER COLUMN category_id TYPE bigint;
ALTER TABLE category_of_types ALTER COLUMN type_id TYPE bigint;

ALTER TABLE image ALTER COLUMN advertisement_id TYPE bigint;

ALTER TABLE services_of_advertisement ALTER COLUMN service_id Type bigint;
ALTER TABLE services_of_advertisement ALTER COLUMN advertisement_id Type bigint;
ALTER TABLE services_of_category ALTER COLUMN category_id Type bigint;
ALTER TABLE services_of_category ALTER COLUMN service_id Type bigint;
ALTER TABLE services_of_order ALTER COLUMN service_id Type bigint;
ALTER TABLE services_of_order ALTER COLUMN order_id Type bigint;

ALTER TABLE user_favourites_advertisements ALTER COLUMN user_id TYPE BIGINT;
ALTER TABLE user_favourites_advertisements ALTER COLUMN advertisement_id TYPE BIGINT;
ALTER TABLE user_info ALTER COLUMN advertisements_id TYPE BIGINT;
ALTER TABLE user_info ALTER COLUMN role_id TYPE BIGINT;