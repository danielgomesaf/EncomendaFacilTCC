CREATE TABLE `encomendafacil`.`historico_encomenda` (
  `idHistoricoEncomenda` VARCHAR(12) NOT NULL,
  `idEncomenda` VARCHAR(12) NOT NULL,
  `codigoRastreio` VARCHAR(13) NOT NULL,
  `dataAtualizacao` DATETIME NOT NULL,
  `dataCadastro` DATETIME NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `status` VARCHAR(100) NOT NULL,
  `idUsuario` VARCHAR(12) NOT NULL,
  `local` VARCHAR(1000) NOT NULL,
  `informacoes` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`idHistoricoEncomenda`));