
INSERT INTO users VALUES (10, 'John');
INSERT INTO users VALUES (20, 'Maria');
INSERT INTO users VALUES (30, 'Mark');
INSERT INTO users VALUES (40, 'Will');

INSERT INTO addresses (user_id, address_line, city, country, postal_code) VALUES (10, 'some street 1', 'New York', 'US', '1234');
INSERT INTO addresses (user_id, address_line, city, country, postal_code) VALUES (10, 'some street 2', 'Miami', 'US', '12345');
INSERT INTO addresses (user_id, address_line, city, country, postal_code) VALUES (20, 'some street 3', 'Berlin', 'DE', '2234');
INSERT INTO addresses (user_id, address_line, city, country, postal_code) VALUES (30, 'some street 4', 'Helsinki', 'FI', '1134');

INSERT INTO providers (name) VALUES ('PROVIDER_1');
INSERT INTO providers (name) VALUES ('PROVIDER_2');
INSERT INTO providers (name) VALUES ('PROVIDER_3');