INSERT INTO User_Info (first_name, second_name, phone, email)
SELECT split_part(MAX(name), ' ', 1) AS FirstName, split_part(MAX(name), ' ', 2) AS SecondName, MAX(phone) AS phone, email AS UserAmount
FROM contact_info GROUP BY email;


ALTER TABLE advertisement ADD user_id BIGINT;
ALTER TABLE advertisement ADD FOREIGN KEY (user_id) REFERENCES User_Info(id);

UPDATE advertisement SET user_id = ui.id
FROM contact_info ci join user_info ui on ci.email = ui.email WHERE ci.id = owner_id;

ALTER TABLE advertisement DROP owner_id;
ALTER TABLE advertisement RENAME user_id to owner_id;