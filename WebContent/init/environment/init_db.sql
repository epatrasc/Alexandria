DROP TABLE IF EXISTS db_exist;
CREATE TABLE db_exist (
	flag TINYINT
)
COMMENT = 'Tabella per inizializzare il database, flag = false per resettare';
INSERT INTO db_exist VALUES (true);

DROP TABLE IF EXISTS utenti;
CREATE TABLE utenti (
  id int NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  password VARCHAR(100) NOT NULL,
  ruolo VARCHAR(20) NOT NULL DEFAULT 'cliente',
  attivo TINYINT NULL DEFAULT true,
  PRIMARY KEY (id)
)
COMMENT = 'Utenti';

DROP TABLE IF EXISTS libri;
CREATE TABLE libri (
  id int NOT NULL AUTO_INCREMENT,
  titolo VARCHAR(45),
  descrizione VARCHAR(500),
  image_url VARCHAR(1000),
  editore VARCHAR(45),
  cancellato TINYINT DEFAULT false,
  disponibile TINYINT DEFAULT true,
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
    REFERENCES autori (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT libro_fk
    FOREIGN KEY (id_libro)
    REFERENCES libri (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
COMMENT = 'Associazione tra autore e libro';

DROP TABLE IF EXISTS prestiti;
CREATE TABLE prestiti (
  id_utente INT NOT NULL,
  id_libro INT NOT NULL,
  data_prestito DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_restituzione DATETIME NULL DEFAULT NULL,
  restituito TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (id_utente, id_libro),
  INDEX fk_idLibro_idx (id_libro ASC),
  UNIQUE INDEX id_libro_UNIQUE (id_libro ASC),
  CONSTRAINT fk_idUtente
    FOREIGN KEY (id_utente)
    REFERENCES utenti (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_idLibro
    FOREIGN KEY (id_libro)
    REFERENCES libri (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Prestiti libri';
