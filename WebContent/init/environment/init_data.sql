TRUNCATE TABLE utenti;
TRUNCATE TABLE autore_libro;
TRUNCATE TABLE autori;
TRUNCATE TABLE libri;
TRUNCATE TABLE prestiti;

INSERT INTO utenti (nome, password, ruolo) VALUES('dante', MD5('dante'), 'cliente');
INSERT INTO utenti (nome, password, ruolo) VALUES('verdi', MD5('verdi'), 'cliente');
INSERT INTO utenti (nome, password, ruolo) VALUES('alessandro', MD5('alessandro'), 'amministratore');

INSERT INTO autori (nome, cognome) VALUES ('Lewis ','Carroll');
INSERT INTO autori (nome, cognome) VALUES ('Joanne','Rowling');
INSERT INTO autori (nome, cognome) VALUES ('Alexandre','Dumas');
INSERT INTO autori (nome, cognome) VALUES ('Umberto','Eco');
INSERT INTO autori (nome, cognome) VALUES ('L. Frank','Baum');
INSERT INTO autori (nome, cognome) VALUES ('Herman','Melville');
INSERT INTO autori (nome, cognome) VALUES ('Dom','Holdaway');
INSERT INTO autori (nome, cognome) VALUES ('Massimo','Scaglioni');

INSERT INTO libri (titolo, descrizione, image_url, editore) VALUES ('Alices adventures in Wonderland ','A little girl falls down a rabbit hole and discovers a world of nonsensical and amusing characters.','http://covers.openlibrary.org/b/OLID/OL1396620M-M.jpg','Dover');
INSERT INTO libri (titolo, descrizione, image_url, editore) VALUES ('Harry Potter and the Prisoner of Azkaban','During his third year at Hogwarts School for Witchcraft and Wizardry, Harry Potter must confront the devious and dangerous wizard responsible for his parents deaths.','http://covers.openlibrary.org/b/OLID/OL47724M-M.jpg','Thorndike Press');
INSERT INTO libri (titolo, descrizione, image_url, editore) VALUES ('The Count of Monte Cristo ','This is the description of the book The Count of Monte Cristo ','http://covers.openlibrary.org/b/OLID/OL3433046M-M.jpg','Pocket Books');
INSERT INTO libri (titolo, descrizione, image_url, editore) VALUES ('Foucaults Pendulum ','Table of Contents KETER 1. When the light of the infinite 2. Wee haue divers curious Clocks…','http://covers.openlibrary.org/b/OLID/OL22774807M-M.jpg','Balllantine Books');
INSERT INTO libri (titolo, descrizione, image_url, editore) VALUES ('The Wizard of Oz ','After a cyclone transports her to the land of Oz, Dorothy must seek out the great wizard in order to return to Kansas.','http://covers.openlibrary.org/b/OLID/OL2400084M-M.jpg','Unicorn Pub. House');
INSERT INTO libri (titolo, descrizione, image_url, editore) VALUES ('Moby Dick ','In comic strip format.','http://covers.openlibrary.org/b/OLID/OL24981832M-M.jpg','Academic Industries');
INSERT INTO libri (titolo, descrizione, image_url, editore) VALUES ('The walking dead','In onda negli Stati Uniti e sulle reti di oltre cento paesi nel mondo dal 2010, The Walking Dead si è imposto, negli anni, come uno dei casi televisivi di maggiore successo a livello globale, generando, allo stesso tempo, un vasto e variegato fenomeno di fandom e di culto.','http://mimesisedizioni.it/media/catalog/product/cache/1/thumbnail/9df78eab33525d08d6e5fb8d27136e95/n/a/narrazioni-seriali-holdaway-walking-dead-1.jpg','Mimesis Edizioni');

INSERT INTO autore_libro(id_autore, id_libro) VALUES (1,1);
INSERT INTO autore_libro(id_autore, id_libro) VALUES (2,2);
INSERT INTO autore_libro(id_autore, id_libro) VALUES (3,3);
INSERT INTO autore_libro(id_autore, id_libro) VALUES (4,4);
INSERT INTO autore_libro(id_autore, id_libro) VALUES (5,5);
INSERT INTO autore_libro(id_autore, id_libro) VALUES (6,6);
INSERT INTO autore_libro(id_autore, id_libro) VALUES (7,7);
INSERT INTO autore_libro(id_autore, id_libro) VALUES (8,7);
