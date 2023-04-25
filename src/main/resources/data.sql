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
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('5', 1, '7186418588', 'abecadlo@gmail.pl', 'Martin', 'Cummins', '888547321');


INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('6', 1, '7186418588', 'Lavish@gmail.pl', 'Eric', 'Lindsey', '776023379 ');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('7', 1, '2938399407', 'Pumped@gmail.pl', 'Jose', 'Montes', '128705806');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('8', 1, '1611468403', 'Bouncy@gmail.pl', 'Chaim', 'Cantu', '059879612');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('9', 1, '6941589751', 'Steep@gmail.pl', 'Shirley', 'Andrews', '397460783');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('10', 1, '4993738661', 'Sore@gmail.pl', 'Orlando', 'Stevenson', '047543823');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('11', 1, '8440744766', 'Talented@gmail.pl', 'Sumaya', 'Daugherty', '436221844 ');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('12', 1, '1695736022', 'Young@gmail.pl', 'Annika', 'Medina', '210626343');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('13', 1, '1620624715', 'Moldy@gmail.pl', 'Bethan', 'Casey', '170613166');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('14', 1, '8039785329', 'Courageous@gmail.pl', 'Ashwin', 'Andersen', '615488252');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('15', 1, '5700033576', 'Guarded@gmail.pl', 'Gilbert', 'Wall', '790214467 ');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('16', 1, '6864652222', 'Crooked@gmail.pl', 'Rehan', 'Rangel', '353858909');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('17', 1, '2473471026', 'Efficacious@gmail.pl', 'Katelyn', 'Barrett', '613051471');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('18', 1, '7656940806', 'Wrong@gmail.pl', 'Sahil', 'Solis', '735925606');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('19', 1, '4364722331', 'Easy@gmail.pl', 'Abel', 'Cooke', '676210251');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('20', 1, '7029563833', 'Unwieldy@gmail.pl', 'Aaron', 'Valdez', '282882990');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('21', 1, '8460424329', 'Near@gmail.pl', 'Ricky', 'Bradshaw', '011595691');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('22', 1, '3690607827', 'Uppity@gmail.pl', 'Tyler', 'Love', '678755363');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('23', 1, '6305706429', 'Black@gmail.pl', 'Zack', 'Bennett', '739438375');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('24', 1, '3852622680', 'Piquant@gmail.pl', 'Annabel', 'Page', '551541378');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber)
VALUES('25', 1, '8504187893', 'Ripe@gmail.pl', 'Susan', 'Saunders', '083446571');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('26', 1, 'Wandering@gmail.pl', 'Filip', 'Haines', '285122513');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('27', 1, 'Stereotyped@gmail.pl', 'Larissa', 'Ray', '406900375');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('28', 1, 'Soft@gmail.pl', 'Kallum', 'Escobar', '053573364');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('29', 1, 'Abrupt@gmail.pl', 'Alexandros', 'Schneider', '991835689');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('30', 1, 'Womanly@gmail.pl', 'Marley', 'Blanchard', '577570401');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('31', 1, 'Minor@gmail.pl', 'Shania', 'Arroyo', '451731094');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('32', 1, 'Grateful@gmail.pl', 'Nicolas', 'Goodwin', '670754376');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('33', 1, 'Stale@gmail.pl', 'Michaela', 'Guzman', '836353287');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('34', 1, 'Thundering@gmail.pl', 'Lucy', 'Savage', '107493484');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('35', 1, 'Right@gmail.pl', 'Mary', 'Winter', '012445268');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('36', 1, 'Petite@gmail.pl', 'Joan', 'Becker', '412985096');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('37', 1, 'Spotty@gmail.pl', 'Walter', 'Rush', '316011455');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('38', 1, 'Ubiquitous@gmail.pl', 'Faye', 'Salinas', '572178546');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('39', 1, 'Bustling@gmail.pl', 'Naomi', 'Montoya', '631884229');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('40', 1, 'Wry@gmail.pl', 'Katy', 'Flynn', '376122385');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('41', 1, 'Squealing@gmail.pl', 'Alissa', 'Arnold', '528058285');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('42', 1, 'Adhesive@gmail.pl', 'Kelsey', 'Hall', '690088448');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('43', 1, 'Hissing@gmail.pl', 'Janet', 'Barrera', '888547321');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('44', 1, 'Sloppy@gmail.pl', 'Valentina', 'Mcmahon', '146461499');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber)
VALUES('45', 1, 'Hushed@gmail.pl', 'Robert', 'Mcclure', '786736102');
