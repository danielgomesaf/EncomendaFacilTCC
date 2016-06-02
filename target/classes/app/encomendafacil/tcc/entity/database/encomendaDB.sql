CREATE TABLE `encomendafacil`.`encomenda` (
  `idEncomenda` VARCHAR(12) NOT NULL,
  `codigoRastreio` VARCHAR(13) NOT NULL,
  `dataAtualizacao` DATETIME NOT NULL,
  `dataCadastro` DATETIME NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `houveMudanca` TINYINT(1) NOT NULL DEFAULT 0,
  `status` VARCHAR(100) NOT NULL,
  `idUsuario` VARCHAR(12) NOT NULL,
  `local` VARCHAR(1000) NOT NULL,
  `informacoes` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`idEncomenda`),
  INDEX `USUARIO_ENCOMENDA_FK_idx` (`idUsuario` ASC),
  CONSTRAINT `USUARIO_ENCOMENDA_FK`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `encomendafacil`.`usuario` (`idUsuario`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);