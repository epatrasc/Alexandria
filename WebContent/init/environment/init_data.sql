SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE TABLE prestiti;
TRUNCATE TABLE utenti;
TRUNCATE TABLE libri;
SET FOREIGN_KEY_CHECKS = 1;


INSERT INTO utenti (nome, password, ruolo) VALUES('dante', MD5('dante'), 'cliente');
INSERT INTO utenti (nome, password, ruolo) VALUES('verdi', MD5('verdi'), 'cliente');
INSERT INTO utenti (nome, password, ruolo) VALUES('alessandro', MD5('alessandro'), 'amministratore');

INSERT INTO libri (autori, titolo, descrizione, image_url, editore) VALUES ('Lewis Carroll', 'Alices adventures in Wonderland ','A little girl falls down a rabbit hole and discovers a world of nonsensical and amusing characters.','https://goo.gl/NyAEkg','Dover');
INSERT INTO libri (autori, titolo, descrizione, image_url, editore) VALUES ('Joanne Rowling', 'Harry Potter and the Prisoner of Azkaban','During his third year at Hogwarts School for Witchcraft and Wizardry, Harry Potter must confront the devious and dangerous wizard responsible for his parents deaths.','https://goo.gl/xGjRey','Thorndike Press');
INSERT INTO libri (autori, titolo, descrizione, image_url, editore) VALUES ('Alexandre Dumas', 'The Count of Monte Cristo ','This is the description of the book The Count of Monte Cristo ','http://alessandria.bookrepublic.it/api/books/9788852044458/cover','Pocket Books');
INSERT INTO libri (autori, titolo, descrizione, image_url, editore) VALUES ('Umberto Eco', 'Foucaults Pendulum ','Table of Contents KETER 1. When the light of the infinite 2. Wee haue divers curious Clocks…','http://image.anobii.com/anobi/image_book.php?item_id=01709ef5994a106f00','Balllantine Books');
INSERT INTO libri (autori, titolo, descrizione, image_url, editore) VALUES ('L. Frank Baum', 'The Wizard of Oz ','After a cyclone transports her to the land of Oz, Dorothy must seek out the great wizard in order to return to Kansas.','http://reteindaco.sebina.it/wp-content/uploads/2017/04/71RmF63Wi2L.jpg','Unicorn Pub. House');
INSERT INTO libri (autori, titolo, descrizione, image_url, editore) VALUES ('Herman Melville', 'Moby Dick ','In comic strip format.','https://goo.gl/ufVZKn','Academic Industries');
INSERT INTO libri (autori, titolo, descrizione, image_url, editore) VALUES ('Massimo Scaglioni, Dom Holdaway', 'The walking dead','In onda negli Stati Uniti e sulle reti di oltre cento paesi nel mondo dal 2010, The Walking Dead si è imposto, negli anni, come uno dei casi televisivi di maggiore successo a livello globale, generando, allo stesso tempo, un vasto e variegato fenomeno di fandom e di culto.','https://goo.gl/Z7Qa8L','Mimesis Edizioni');
