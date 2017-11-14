DROP TABLE IF EXISTS db_exist;
CREATE TABLE db_exist (
	flag BOOLEAN
)
COMMENT = 'Tabella per inizializzare il database, flag = false per resettare';
INSERT INTO db_exist VALUES (true);

DROP TABLE IF EXISTS utenti;
CREATE TABLE utenti (
  id int NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  password VARCHAR(15) NOT NULL,
  ruolo VARCHAR(20) NOT NULL DEFAULT 'cliente',
  attivo BOOLEAN NULL DEFAULT false,
  PRIMARY KEY (id)
)
COMMENT = 'Utenti';

DROP TABLE IF EXISTS libri;
CREATE TABLE libri (
  id int NOT NULL AUTO_INCREMENT,
  titolo VARCHAR(45),
  editore VARCHAR(45),
  cancellato BOOLEAN DEFAULT false,
  disponibile BOOLEAN DEFAULT true,
  PRIMARY KEY (id),
  UNIQUE INDEX libri (titolo ASC)
)
COMMENT = 'Libri';

DROP TABLE IF EXISTS autori;
CREATE TABLE autori (
  id int NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  cognome VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
)
COMMENT = 'Autori';

DROP TABLE IF EXISTS autore_libro;
CREATE TABLE autore_libro (
  id_autore INT NOT NULL,
  id_libro INT NOT NULL,
  PRIMARY KEY (id_autore, id_libro),
  INDEX libro_fk_idx (id_libro ASC),
  CONSTRAINT autore_fk
    FOREIGN KEY (id_autore)
    REFERENCES libreria.autori (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT libro_fk
    FOREIGN KEY (id_libro)
    REFERENCES libreria.libri (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
COMMENT = 'Associazione tra autore e libro';
