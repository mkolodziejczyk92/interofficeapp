insert into user (version, id, username, name, lastName, hashedPassword)
values (1, '1','user', 'John', 'Normal', '$2a$10$H1WEkjK3i3aibtlAYs1zCO6Cawjk3ll38vhUWLe6H3.2f2NDNeCX.');
insert into user_eroles (user_id, ERoles) values ('1', 'USER');

insert into user (version, id, username, name, lastName, hashedPassword)
values (1, '2', 'admin', 'Emma', 'Powerful', '$2a$10$H1WEkjK3i3aibtlAYs1zCO6Cawjk3ll38vhUWLe6H3.2f2NDNeCX.');

insert into user_eroles (user_id, ERoles) values ('2', 'USER');
insert into user_eroles (user_id, ERoles) values ('2', 'ADMIN');