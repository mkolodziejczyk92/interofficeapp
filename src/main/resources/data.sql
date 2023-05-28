INSERT INTO user (version, id, username, firstName, lastName, password)
VALUES (1, '1','user', 'John', 'Normal', '$2a$10$H1WEkjK3i3aibtlAYs1zCO6Cawjk3ll38vhUWLe6H3.2f2NDNeCX.');
INSERT INTO user (version, id, username, firstName, lastName, password)
VALUES (1, '2', 'admin', 'Emma', 'Powerful', '$2a$10$H1WEkjK3i3aibtlAYs1zCO6Cawjk3ll38vhUWLe6H3.2f2NDNeCX.');

INSERT INTO user_eroles (user_id, ERoles) VALUES ('1', 'USER');
insert into user_eroles (user_id, ERoles) values ('2', 'USER');
insert into user_eroles (user_id, ERoles) values ('2', 'ADMIN');

INSERT INTO client (id, version,  email, firstName, lastName, phoneNumber,  officeClient, addedBy)
VALUES('1', 1, 'martin@onet.pl', 'Martin', 'Eden', '876908765', true, 'Emma Powerful');
INSERT INTO client (id, version,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES ('2', 1, 'stephen@o2.pl', 'Stephen', 'Maturin', '856328756', false, 'Emma Powerful');
INSERT INTO client (id, version,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES ('3', 1, 'willywonka@poczta.pl', 'Willy', 'Wonka', '859999567', true, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('4', 1, '7123268588', 'minhar@gmail.pl', 'Mina', 'Harker', '857999567', false, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('5', 1, '7186418588', 'abecadlo@gmail.pl', 'Martin', 'Cummins', '888547321', false, 'John Normal');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('6', 1, '7186418588', 'Lavish@gmail.pl', 'Eric', 'Lindsey', '776023379 ', false, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('7', 1, '2938399407', 'Pumped@gmail.pl', 'Jose', 'Montes', '128705806', false, 'John Normal');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('8', 1, '1611468403', 'Bouncy@gmail.pl', 'Chaim', 'Cantu', '059879612', false, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('9', 1, '6941589751', 'Steep@gmail.pl', 'Shirley', 'Andrews', '397460783', true, 'John Normal');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('10', 1, '4993738661', 'Sore@gmail.pl', 'Orlando', 'Stevenson', '047543823', false, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('11', 1, '8440744766', 'Talented@gmail.pl', 'Sumaya', 'Daugherty', '436221844 ', false, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('12', 1, '1695736022', 'Young@gmail.pl', 'Annika', 'Medina', '210626343', true, 'John Normal');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('13', 1, '1620624715', 'Moldy@gmail.pl', 'Bethan', 'Casey', '170613166', false, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('14', 1, '8039785329', 'Courageous@gmail.pl', 'Ashwin', 'Andersen', '615488252', false, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('15', 1, '5700033576', 'Guarded@gmail.pl', 'Gilbert', 'Wall', '790214467 ', true, 'John Normal');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('16', 1, '6864652222', 'Crooked@gmail.pl', 'Rehan', 'Rangel', '353858909', false, 'John Normal');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('17', 1, '2473471026', 'Efficacious@gmail.pl', 'Katelyn', 'Barrett', '613051471', true, 'John Normal');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('18', 1, '7656940806', 'Wrong@gmail.pl', 'Sahil', 'Solis', '735925606', false, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('19', 1, '4364722331', 'Easy@gmail.pl', 'Abel', 'Cooke', '676210251', false, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('20', 1, '7029563833', 'Unwieldy@gmail.pl', 'Aaron', 'Valdez', '282882990', true, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('21', 1, '8460424329', 'Near@gmail.pl', 'Ricky', 'Bradshaw', '011595691', false, 'John Normal');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('22', 1, '3690607827', 'Uppity@gmail.pl', 'Tyler', 'Love', '678755363', true, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('23', 1, '6305706429', 'Black@gmail.pl', 'Zack', 'Bennett', '739438375', false, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('24', 1, '3852622680', 'Piquant@gmail.pl', 'Annabel', 'Page', '551541378', true, 'Emma Powerful');
INSERT INTO client (id, version, nip,  email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('25', 1, '8504187893', 'Ripe@gmail.pl', 'Susan', 'Saunders', '083446571', false, 'John Normal');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('26', 1, 'Wandering@gmail.pl', 'Filip', 'Haines', '285122513', false, 'Emma Powerful');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('27', 1, 'Stereotyped@gmail.pl', 'Larissa', 'Ray', '406900375', true, 'Emma Powerful');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('28', 1, 'Soft@gmail.pl', 'Kallum', 'Escobar', '053573364', true, 'John Normal');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('29', 1, 'Abrupt@gmail.pl', 'Alexandros', 'Schneider', '991835689', false, 'Emma Powerful');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('30', 1, 'Womanly@gmail.pl', 'Marley', 'Blanchard', '577570401', true, 'John Normal');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('31', 1, 'Minor@gmail.pl', 'Shania', 'Arroyo', '451731094', false, 'Emma Powerful');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('32', 1, 'Grateful@gmail.pl', 'Nicolas', 'Goodwin', '670754376', true, 'Emma Powerful');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('33', 1, 'Stale@gmail.pl', 'Michaela', 'Guzman', '836353287', false, 'John Normal');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('34', 1, 'Thundering@gmail.pl', 'Lucy', 'Savage', '107493484', true, 'Emma Powerful');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('35', 1, 'Right@gmail.pl', 'Mary', 'Winter', '012445268', true, 'John Normal');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('36', 1, 'Petite@gmail.pl', 'Joan', 'Becker', '412985096', true, 'Emma Powerful');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('37', 1, 'Spotty@gmail.pl', 'Walter', 'Rush', '316011455', true, 'John Normal');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('38', 1, 'Ubiquitous@gmail.pl', 'Faye', 'Salinas', '572178546', false, 'John Normal');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('39', 1, 'Bustling@gmail.pl', 'Naomi', 'Montoya', '631884229', true, 'Emma Powerful');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('40', 1, 'Wry@gmail.pl', 'Katy', 'Flynn', '376122385', false, 'John Normal');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('41', 1, 'Squealing@gmail.pl', 'Alissa', 'Arnold', '528058285', false, 'Emma Powerful');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('42', 1, 'Adhesive@gmail.pl', 'Kelsey', 'Hall', '690088448', true, 'John Normal');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('43', 1, 'Hissing@gmail.pl', 'Janet', 'Barrera', '888547321', true, 'Emma Powerful');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('44', 1, 'Sloppy@gmail.pl', 'Valentina', 'Mcmahon', '146461499', false, 'John Normal');
INSERT INTO client (id, version, email, firstName, lastName, phoneNumber, officeClient, addedBy)
VALUES('45', 1, 'Hushed@gmail.pl', 'Robert', 'Mcclure', '786736102', true, 'Emma Powerful');


INSERT INTO supplier(id, version, nameOfCompany, nip)
VALUES('1', 1 , 'Arrow Company' ,'5241485450');
INSERT INTO supplier(id, version, nameOfCompany, nip)
VALUES('2', 1 , 'GrowFox' ,'3393744330');
INSERT INTO supplier(id, version, nameOfCompany, nip)
VALUES('3', 1 , 'NewHorizon Windows','5332163479');
INSERT INTO supplier(id, version, nameOfCompany, nip)
VALUES('4', 1 , 'Door Tools','5214116518');
INSERT INTO supplier(id, version, nameOfCompany, nip)
VALUES('5', 1 , 'Odfix','1247841323');
INSERT INTO supplier(id, version, nameOfCompany, nip)
VALUES('6', 1 , 'Primly','5094535039');
INSERT INTO supplier(id, version, nameOfCompany, nip)
VALUES('7', 1 ,'Evji' ,'5291722828');
INSERT INTO supplier(id, version, nameOfCompany, nip)
VALUES('8', 1 , 'Spireso','3564419655');
INSERT INTO supplier(id, version, nameOfCompany, nip)
VALUES('9', 1 , 'Artfully' ,'9324078761');
INSERT INTO supplier(id, version, nameOfCompany, nip)
VALUES('10', 1 , 'Voyagly' ,'5355482834');
INSERT INTO supplier(id, version, nameOfCompany, nip)
VALUES('11', 1 , 'Fuxly' ,'9516206456');


-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id, comment,)
-- VALUES ('1',0, 'INTERNAL_DOORS', '5000', 'ORDERED', '4000' , 1 , 1 , 'Fast delivery');
--
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id)
-- VALUES ('2',0, 'EXTERNAL_DOORS', '8500', 'PAID_NOT_ORDERED', '3600' , 1 , 2  );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id, comment)
-- VALUES ('3',0, 'EXTERNAL_WINDOWS_BLINDS_TYPE_C', '450', 'PAID_NOT_ORDERED', '400' , 1 , 3, 'Send with one extra window'  );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id)
-- VALUES ('4',0, 'EXTERNAL_DOORS', '7850', 'SUPPLIED_FOR_STORAGE', '840' , 5 , 4  );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id)
-- VALUES ('5',0, 'INTERIOR_WINDOW_SHADES', '9600', 'SUPPLIED_FOR_STORAGE', '840' , 4 , 4  );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id, comment)
-- VALUES ('6',0, 'INTERNAL_DOORS', '13000', 'COMPLETED', '560' , 4 , 5, '10% extra discount' );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id)
-- VALUES ('7',0, 'EXTERNAL_ROLLER_SHUTTER', '4600', 'COMPLETED', '700' , 3 , 8  );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id)
-- VALUES ('8',0, 'INTERIOR_WINDOW_SHADES', '7850', 'SUPPLIED_FOR_STORAGE', '780' , 11 , 2  );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id)
-- VALUES ('9',0, 'EXTERNAL_WINDOWS_BLINDS_TYPE_C', '18000', 'PAID_NOT_ORDERED', '460' , 13 , 3  );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id, comment)
-- VALUES ('10',0, 'WINDOWS', '3700', 'ORDERED', '800' , 14 , 6, 'Call and ask for window type' );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id)
-- VALUES ('11',0, 'INTERIOR_WINDOW_SHADES', '4800', 'NOT_PAID', '9000' , 22 , 10  );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id)
-- VALUES ('12',0, 'INTERNAL_DOORS', '11000', 'NOT_PAID', '3500' , 18 , 11  );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id , comment)
-- VALUES ('13',0, 'INTERNAL_DOORS', '13500', 'PAID_NOT_ORDERED', '150' , 38 , 9, 'Call in next week' );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id , comment)
-- VALUES ('14',0, 'INTERIOR_WINDOW_SHADES', '6800', 'ORDERED', '455' , 24 , 7, 'Ask for extra window order' );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id , comment)
-- VALUES ('15',0, 'EXTERNAL_DOORS', '9850', 'ORDERED', '365' , 18 , 3,'new house' );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id , comment)
-- VALUES ('16',0, 'WINDOWS', '2000', 'COMPLETED', '760' , 20 , 2, '2 extra windows' );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id , comment)
-- VALUES ('17',0, 'INTERIOR_WINDOW_SHADES', '1400', 'PAID_NOT_ORDERED', '480' , 32 , 4, 'finished 1st floor'  );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id)
-- VALUES ('18',0, 'EXTERNAL_DOORS', '1530', 'SUPPLIED_FOR_STORAGE', '687' , 31 , 1  );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id)
-- VALUES ('19',0, 'EXTERNAL_WINDOWS_BLINDS_TYPE_Z', '4000', 'NOT_PAID', '444' , 36 , 7  );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id, comment)
-- VALUES ('20',0, 'INTERIOR_WINDOW_SHADES', '8650', 'COMPLETED', '580' , 26 , 8, 'started new construction ' );
-- INSERT INTO purchase(id, version, commodityType, netAmount, status,
--                      supplierPurchaseNumber, client_id, supplier_id)
-- VALUES ('21',0, 'EXTERNAL_WINDOWS_BLINDS_TYPE_Z', '3000', 'SUPPLIED_FOR_STORAGE', '170' , 28 , 9  );
--
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('1', 0, 'INTERNAL_DOORS' , true, '4700' , '10/05/2023', 1);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('2', 0, 'INTERNAL_DOORS' , true, '5800' , '11/05/2023', 5);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('3', 0, 'WINDOWS' , false, '14000' , '12/05/2023', 1);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('4', 0, 'EXTERNAL_WINDOWS_BLINDS_TYPE_C' , true, '6800' , '13/05/2023', 10);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('5', 0, 'EXTERNAL_ROLLER_SHUTTER' , false, '7600' , '14/05/2023', 11);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('6', 0, 'WINDOWS' , false, '14300' , '10/06/2023', 18);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('7', 0, 'INTERIOR_WINDOW_SHADES' , false, '7000' , '11/06/2023', 9);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('8', 0, 'EXTERNAL_WINDOWS_BLINDS_TYPE_C' , true, '6870' , '12/06/2023', 7);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('9', 0, 'INTERNAL_DOORS' , true, '12000' , '13/06/2023', 22);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('10', 0, 'EXTERNAL_ROLLER_SHUTTER' , false, '14950' , '14/06/2023', 36);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('11', 0, 'INTERIOR_WINDOW_SHADES' , false, '2050' , '15/06/2023', 22);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('12', 0, 'EXTERNAL_DOORS' , false, '6050' , '16/06/2023', 1);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('13', 0, 'EXTERNAL_WINDOWS_BLINDS_TYPE_Z' , false, '9500' , '17/06/2023', 1);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('14', 0, 'EXTERNAL_ROLLER_SHUTTER' , true, '11500' , '18/06/2023', 8);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('15', 0, 'INTERNAL_DOORS' , true, '16000' , '10/07/2023', 24);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('16', 0, 'EXTERNAL_WINDOWS_BLINDS_TYPE_C' , false, '7450' , '11/07/2023', 14);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('17', 0, 'EXTERNAL_DOORS' , true, '4630' , '12/07/2023', 13);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('18', 0, 'EXTERNAL_DOORS' , true, '8600' , '13/07/2023', 16);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('19', 0, 'INTERIOR_WINDOW_SHADES' , false, '4700' , '14/07/2023', 12);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('20', 0, 'INTERNAL_DOORS' , false, '8400' , '15/07/2023', 40);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('21', 0, 'INTERIOR_WINDOW_SHADES' , true, '19000' , '10/08/2023', 41);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('22', 0, 'WINDOWS' , false, '2500' , '11/08/2023', 27);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('23', 0, 'WINDOWS' , false, '4600' , '12/08/2023', 13);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('24', 0, 'EXTERNAL_WINDOWS_BLINDS_TYPE_C' , true, '3500' , '13/08/2023', 16);
-- INSERT INTO contract(id, version, commodityType, completed, netAmount, number, client_id)
-- VALUES ('25', 0, 'WINDOWS' , false, '2000' , '14/08/2023', 18);

INSERT INTO address(id, version, addressType, apartmentNumber, city, country, houseNumber,
                    municipality, plotNumber, street, voivodeship, zipCode, client_id)
VALUES('10', 0,'RESIDENCE', '2', 'Cracow', 'POLAND', '5', 'Krakowska', '' ,
       'Krakowska', 'LESSER_POLAND' , '30-414' , 1);
INSERT INTO address(id, version, addressType, apartmentNumber, city, country, houseNumber,
                    municipality, plotNumber, street, voivodeship, zipCode, client_id)
VALUES('4', 0,'INVESTMENT', '', 'Cracow', 'POLAND', '14', 'Krakowska', '1' ,
       'Łukasińskiego', 'LESSER_POLAND' , '30-470' , 1);
INSERT INTO address(id, version, addressType, apartmentNumber, city, country, houseNumber,
                    municipality, plotNumber, street, voivodeship, zipCode, client_id)
VALUES('7', 0,'RESIDENCE', '', 'Cracow', 'POLAND', '23', 'Krakowska', '7' ,
       'Bieżanowska', 'LESSER_POLAND' , '32-400' , 8);
INSERT INTO address(id, version, addressType, apartmentNumber, city, country, houseNumber,
                    municipality, plotNumber, street, voivodeship, zipCode, client_id)
VALUES('2', 0,'RESIDENCE', '', 'Cracow', 'POLAND', '17', 'Krakowska', '8' ,
       'Lubelska', 'LESSER_POLAND' , '31-444' , 14);
INSERT INTO address(id, version, addressType, apartmentNumber, city, country, houseNumber,
                    municipality, plotNumber, street, voivodeship, zipCode, client_id)
VALUES('6', 0,'RESIDENCE', '14', 'Wieliczka', 'POLAND', '9', 'Wieliczka', '' ,
       'Wielicka', 'LESSER_POLAND' , '32-020' , 16);
INSERT INTO address(id, version, addressType, apartmentNumber, city, country, houseNumber,
                    municipality, plotNumber, street, voivodeship, zipCode, client_id)
VALUES('9', 0,'INVESTMENT', '', 'Wieliczka', 'POLAND', '2', 'Wieliczka', '4' ,
       'Kosocicka', 'LESSER_POLAND' , '32-146' , 3);
INSERT INTO address(id, version, addressType, apartmentNumber, city, country, houseNumber,
                    municipality, plotNumber, street, voivodeship, zipCode, client_id)
VALUES('3', 0,'INVESTMENT', '', 'Wieliczka', 'POLAND', '5', 'Wieliczka', '10' ,
       'Marszałkowska', 'LESSER_POLAND' , '31-782' , 8);
INSERT INTO address(id, version, addressType, apartmentNumber, city, country, houseNumber,
                    municipality, plotNumber, street, voivodeship, zipCode, client_id)
VALUES('1', 0,'RESIDENCE', '28', 'Lublin', 'POLAND', '13', 'Lublin', '' ,
       'Wrocławska', 'LUBLIN' , '38-640' , 9);
INSERT INTO address(id, version, addressType, apartmentNumber, city, country, houseNumber,
                    municipality, plotNumber, street, voivodeship, zipCode, client_id)
VALUES('5', 0,'RESIDENCE', '42', 'Lublin', 'POLAND', '7', 'Lublin', '' ,
       'Zbrojarzy', 'LUBLIN' , '38-540' , 24);
INSERT INTO address(id, version, addressType, apartmentNumber, city, country, houseNumber,
                    municipality, plotNumber, street, voivodeship, zipCode, client_id)
VALUES('8', 0,'INVESTMENT', '', 'Lublin', 'POLAND', '142', 'Lublin', '17' ,
       'Zakopiańska', 'LUBLIN' , '38-760' , 26);
