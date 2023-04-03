INSERT INTO user (version, id, username, firstName, lastName, hashedPassword)
VALUES (1, '1','user', 'John', 'Normal', '$2a$10$H1WEkjK3i3aibtlAYs1zCO6Cawjk3ll38vhUWLe6H3.2f2NDNeCX.');
INSERT INTO user (version, id, username, firstName, lastName, hashedPassword)
VALUES (1, '2', 'admin', 'Emma', 'Powerful', '$2a$10$H1WEkjK3i3aibtlAYs1zCO6Cawjk3ll38vhUWLe6H3.2f2NDNeCX.');

INSERT INTO user_eroles (user_id, ERoles) VALUES ('1', 'USER');
insert into user_eroles (user_id, ERoles) values ('2', 'USER');
insert into user_eroles (user_id, ERoles) values ('2', 'ADMIN');

INSERT INTO client (id, version,  email, firstName, lastName, phoneNumber)
VALUES('1', 1, 'martin@onet.pl', 'Martin', 'Eden', '876908765');
INSERT INTO client (id, version,  email, firstName, lastName, phoneNumber)
VALUES ('2', 1, 'stephen@o2.pl', 'Stephen', 'Maturin', '856328756');
INSERT INTO client (id, version,  email, firstName, lastName, phoneNumber)
VALUES ('3', 1, 'willywonka@poczta.pl', 'Willy', 'Wonka', '859999567');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('4', 1, '7123268588', 'minhar@gmail.pl', 'Mina', 'Harker', '857999567');