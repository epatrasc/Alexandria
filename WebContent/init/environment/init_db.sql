SET FOREIGN_KEY_CHECKS = 0; 
DROP TABLE IF EXISTS db_exist;
DROP TABLE IF EXISTS prestiti;
DROP TABLE IF EXISTS libri;
DROP TABLE IF EXISTS utenti;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE db_exist (
	flag TINYINT
)
COMMENT = 'Tabella per inizializzare il database, flag = false per resettare';
INSERT INTO db_exist VALUES (true);


CREATE TABLE utenti (
  id int NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  password VARCHAR(100) NOT NULL,
  ruolo VARCHAR(20) NOT NULL DEFAULT 'cliente',
  attivo TINYINT NULL DEFAULT true,
  PRIMARY KEY (id)
)
COMMENT = 'Utenti';

CREATE TABLE libri (
  id int NOT NULL AUTO_INCREMENT,
  titolo VARCHAR(45),
  autori VARCHAR(500),
  descrizione VARCHAR(500),
  image_url VARCHAR(1000),
  editore VARCHAR(45),
  cancellato TINYINT DEFAULT false,
  disponibile TINYINT DEFAULT true,
  PRIMARY KEY (id),
  UNIQUE INDEX libri (titolo ASC)
)
COMMENT = 'Libri';

CREATE TABLE prestiti (
  id int NOT NULL AUTO_INCREMENT,
  id_utente INT NOT NULL,
  id_libro INT NOT NULL,
  data_prestito DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_restituzione DATETIME NULL DEFAULT NULL,
  restituito TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (id, id_utente, id_libro),
  INDEX fk_idLibro_idx (id_libro ASC),
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
