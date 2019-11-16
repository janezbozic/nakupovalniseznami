INSERT INTO uporabnik (ime, priimek, uporabniskoime, email) VALUES ('Petra', 'Kos', 'petrakos', 'petra.kos@hotmail.com');
INSERT INTO uporabnik (ime, priimek, uporabniskoime, email) VALUES ('Miha', 'Novak', 'mihanovak', 'miha.novak@gmail.com');

insert into popust (velikost) values (10);
insert into popust (velikost) values (5);

insert into artikel (cena, ime, zaloga, popust_id) values (899.99, 'Samsung galaxy s10', 150, 1);
insert into artikel (cena, ime, zaloga, popust_id) values (999.99, 'Samsung galaxy s10+', 150, 2);

insert into nakupovalni_seznam (opravljeno, uporabnik_id, ustvarjen) values ('DA', 1, '');
insert into nakupovalni_seznam (opravljeno, uporabnik_id, ustvarjen) values ('NE', 2, '');

insert into artikel_seznama (nakupovalni_seznam, artikel) VALUES (1,1);
insert into artikel_seznama (nakupovalni_seznam, artikel) VALUES (2,2);