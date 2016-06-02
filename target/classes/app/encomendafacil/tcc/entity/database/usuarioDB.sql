CREATE TABLE `encomendafacil`.`usuario` (
  `idUsuario` VARCHAR(12) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `senha` VARCHAR(30) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `tokenId` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idUsuario`));