-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: defaultdb
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cpf` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'Nome Atualizado do Cliente','123.456.789-00','(11) 99999-0000','novo_email@teste.com'),(2,'Lucas Rodrigues','234.567.890-11','(11) 97777-6666','lucas.rod@email.com'),(3,'Mariana Costa','345.678.901-22','(21) 96666-5555','mari.costa@email.com'),(4,'Rodrigo Almeida','456.789.012-33','(31) 95555-4444','rodrigo.alm@email.com'),(5,'Beatriz Santos','567.890.123-44','(11) 94444-3333','bia.santos@email.com'),(6,'André Oliveira','678.890.123-55','(19) 93333-2222','andre.oli@email.com'),(7,'Camila Souza','789.012.345-66','(11) 92222-1111','camila.souza@email.com'),(8,'Ricardo Pereira','890.123.456-77','(21) 91111-0000','ricardo.p@email.com'),(9,'Fernanda Lima','901.234.567-88','(41) 99999-8888','fer.lima@email.com'),(10,'Thiago Barbosa','012.345.678-99','(11) 98888-1122','thiago.b@email.com'),(11,'Amanda Martins','112.233.445-55','(31) 97777-2233','amanda.m@email.com'),(12,'Gabriel Ramos','223.344.556-66','(11) 96666-3344','gabriel.ramos@email.com'),(13,'Larissa Melo','334.455.667-77','(19) 95555-4455','larissa.melo@email.com'),(14,'Felipe Castro da Silva','','',''),(15,'Patrícia Rocha','556.677.889-99','(11) 93333-6677','patricia.r@email.com'),(16,'Maria da Graça','829.166.620-22','6337613781','kojeho3272@ocuser.com'),(18,'Juliana Mendes','737.410.220-80','4726574367','kojeho3272@ocuser.com'),(19,'Fulano de tal','09900035211','','');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colaboradores`
--

DROP TABLE IF EXISTS `colaboradores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colaboradores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL,
  `cargo` varchar(100) NOT NULL,
  `especialidade` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colaboradores`
--

LOCK TABLES `colaboradores` WRITE;
/*!40000 ALTER TABLE `colaboradores` DISABLE KEYS */;
INSERT INTO `colaboradores` VALUES (1,'Carlos Souza','MECANICO','Injeção Eletrônica'),(2,'Marcos Lima','ATENDENTE','Recepção Comercial'),(3,'Roberto Alves','MECANICO','Suspensão e Freios'),(4,'Eliane Silveira','ATENDENTE','Faturamento'),(5,'Fernando Jorge','MECANICO','Motores de Linha Leve'),(6,'Alexandre Cruz','MECANICO','Alinhamento e Geometria'),(7,'Julio Cesar','MECANICO','Ar Condicionado Automotivo'),(8,'Sandra Regina','ATENDENTE','Pós-Venda'),(9,'Ricardo Dias','MECANICO','Câmbio Automático'),(11,'Bruna Mendes','ATENDENTE','Orçamentos'),(12,'Lucas Antunes','MECANICO','Mecânica Geral'),(13,'Eduardo Sales','MECANICO','Diagnóstico Scanner'),(14,'Tatiane Costa','ATENDENTE','Gerência de Atendimento'),(15,'Diego Meireles','MECANICO','Freios ABS e Hidráulica'),(16,'Carlos Souza De Medeiros','Atendente de Mecânica','');
/*!40000 ALTER TABLE `colaboradores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logs_auditoria`
--

DROP TABLE IF EXISTS `logs_auditoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logs_auditoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_alteracao` datetime NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `entidade_afetada` varchar(50) NOT NULL,
  `registro_id` int(11) NOT NULL,
  `tipo_operacao` enum('INSERT','UPDATE','DELETE') NOT NULL,
  `descricao_mudanca` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_logs_colaboradores` (`usuario_id`),
  CONSTRAINT `fk_logs_colaboradores` FOREIGN KEY (`usuario_id`) REFERENCES `colaboradores` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs_auditoria`
--

LOCK TABLES `logs_auditoria` WRITE;
/*!40000 ALTER TABLE `logs_auditoria` DISABLE KEYS */;
INSERT INTO `logs_auditoria` VALUES (1,'2026-06-01 08:30:00',2,'ORDEM_SERVICO',1,'INSERT','Ordem de serviço aberta sob o nº 1001.'),(2,'2026-06-01 12:00:00',2,'ORDEM_SERVICO',1,'UPDATE','Ordem de serviço finalizada e dada como CONCLUIDA.'),(3,'2026-06-03 14:00:00',2,'ORDEM_SERVICO',3,'INSERT','Ordem de serviço nº 1003 gerada.'),(4,'2026-06-05 11:45:00',11,'ORDEM_SERVICO',4,'UPDATE','Status alterado para AGUARDANDO_PECA. Correia sob encomenda.'),(5,'2026-06-02 11:40:00',2,'ORDEM_SERVICO',9,'UPDATE','Ordem de serviço CANCELADA por solicitação do cliente.'),(6,'2026-06-10 08:45:00',2,'ORDEM_SERVICO',14,'INSERT','Ordem de serviço nº 1014 aberta.'),(7,'2026-06-10 14:20:00',2,'ORDEM_SERVICO',14,'UPDATE','Status alterado para AGUARDANDO_PECA - aguardando chegada de bomba de água.'),(8,'2026-06-13 17:27:58',1,'ordens_servico',1,'UPDATE','Alterado valor_total_geral de 0.00 para 250.00');
/*!40000 ALTER TABLE `logs_auditoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordens_servico`
--

DROP TABLE IF EXISTS `ordens_servico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordens_servico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero_os` int(11) NOT NULL,
  `situacao` enum('ABERTA','EM_ANDAMENTO','AGUARDANDO_PECA','CONCLUIDA','CANCELADA') NOT NULL,
  `data_abertura` datetime NOT NULL,
  `data_encerramento` datetime DEFAULT NULL,
  `data_aguardando_pecas_desde` datetime DEFAULT NULL,
  `chk_estepe` tinyint(1) NOT NULL DEFAULT 1,
  `chk_macaco_chave_roda` tinyint(1) NOT NULL DEFAULT 1,
  `chk_triangulo` tinyint(1) NOT NULL DEFAULT 1,
  `chk_radio` tinyint(1) NOT NULL DEFAULT 1,
  `chk_nivel_combustivel` varchar(30) NOT NULL,
  `chk_observacoes_avarias` text DEFAULT NULL,
  `cliente_id` int(11) NOT NULL,
  `veiculo_id` int(11) NOT NULL,
  `aberto_por_colaborador_id` int(11) NOT NULL,
  `valor_total_servicos` decimal(10,2) NOT NULL DEFAULT 0.00,
  `valor_total_pecas` decimal(10,2) NOT NULL DEFAULT 0.00,
  `valor_total_geral` decimal(10,2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_os` (`numero_os`),
  KEY `fk_os_clientes` (`cliente_id`),
  KEY `fk_os_veiculos` (`veiculo_id`),
  KEY `fk_os_colaboradores` (`aberto_por_colaborador_id`),
  CONSTRAINT `fk_os_clientes` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`),
  CONSTRAINT `fk_os_colaboradores` FOREIGN KEY (`aberto_por_colaborador_id`) REFERENCES `colaboradores` (`id`),
  CONSTRAINT `fk_os_veiculos` FOREIGN KEY (`veiculo_id`) REFERENCES `veiculos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordens_servico`
--

LOCK TABLES `ordens_servico` WRITE;
/*!40000 ALTER TABLE `ordens_servico` DISABLE KEYS */;
INSERT INTO `ordens_servico` VALUES (1,1001,'CONCLUIDA','2026-06-01 08:30:00','2026-06-01 12:00:00',NULL,1,1,1,1,'MEIO_TANQUE','Sem avarias visíveis.',1,1,2,210.00,400.00,250.00),(2,1002,'CONCLUIDA','2026-06-02 09:15:00','2026-06-02 16:30:00',NULL,1,1,1,1,'CHEIO','Parachoque dianteiro levemente riscado.',2,2,4,120.00,0.00,120.00),(3,1003,'CONCLUIDA','2026-06-03 14:00:00','2026-06-04 10:00:00',NULL,1,0,1,1,'RESERVA','Falta chave de roda.',3,3,2,130.00,180.00,310.00),(4,1004,'AGUARDANDO_PECA','2026-06-05 10:30:00',NULL,'2026-06-05 11:45:00',1,1,1,0,'QUART_TANQUE','Rádio foi retirado pelo cliente.',4,4,11,350.00,160.00,510.00),(5,1005,'EM_ANDAMENTO','2026-06-08 08:00:00',NULL,NULL,1,1,1,1,'MEIO_TANQUE','Pequeno amassado na porta do motorista.',5,5,2,180.00,70.00,250.00),(6,1006,'CONCLUIDA','2026-06-08 13:20:00','2026-06-09 11:00:00',NULL,0,1,1,1,'CHEIO','Veículo sem estepe.',6,6,4,600.00,750.00,1350.00),(7,1007,'CANCELADA','2026-06-12 11:00:00',NULL,NULL,1,1,1,1,'MEIO_TANQUE','Calota traseira direita quebrada. | OS CANCELADA. Motivo: Teste Juliana',7,7,2,0.00,0.00,0.00),(8,1008,'EM_ANDAMENTO','2026-06-11 09:00:00',NULL,NULL,1,1,1,1,'QUART_TANQUE','Arranhões na tampa do porta malas.',8,8,14,200.00,40.00,240.00),(9,1009,'CANCELADA','2026-06-02 11:00:00','2026-06-02 11:40:00',NULL,1,1,1,1,'CHEIO','Cliente desistiu do orçamento preliminar.',9,9,2,0.00,0.00,0.00),(10,1010,'CONCLUIDA','2026-06-04 15:30:00','2026-06-05 17:00:00',NULL,1,1,1,1,'MEIO_TANQUE','Vidro elétrico traseiro esquerdo travado.',10,10,11,150.00,140.00,290.00),(11,1011,'CONCLUIDA','2026-06-06 08:10:00','2026-06-06 12:00:00',NULL,1,1,1,1,'RESERVA','Banco de couro do motorista rasgado.',11,11,4,120.00,0.00,120.00),(12,1012,'EM_ANDAMENTO','2026-06-11 14:15:00',NULL,NULL,1,1,0,1,'CHEIO','Falta triângulo de sinalização.',12,12,2,280.00,760.00,1040.00),(13,1013,'CONCLUIDA','2026-06-09 10:00:00','2026-06-09 15:00:00',NULL,1,1,1,1,'MEIO_TANQUE','Retrovisor direito quebrado.',13,13,14,90.00,265.00,355.00),(14,1014,'AGUARDANDO_PECA','2026-06-10 08:45:00',NULL,'2026-06-10 14:20:00',1,1,1,1,'QUART_TANQUE','Sem avarias.',14,14,2,450.00,210.00,660.00),(15,1015,'CONCLUIDA','2026-06-03 11:20:00','2026-06-03 14:50:00',NULL,1,1,1,1,'CHEIO','Roda de liga leve ralada.',15,15,11,220.00,0.00,220.00);
/*!40000 ALTER TABLE `ordens_servico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `os_mecanicos`
--

DROP TABLE IF EXISTS `os_mecanicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `os_mecanicos` (
  `ordem_servico_id` int(11) NOT NULL,
  `colaborador_id` int(11) NOT NULL,
  PRIMARY KEY (`ordem_servico_id`,`colaborador_id`),
  KEY `fk_osmec_colab` (`colaborador_id`),
  CONSTRAINT `fk_osmec_colab` FOREIGN KEY (`colaborador_id`) REFERENCES `colaboradores` (`id`),
  CONSTRAINT `fk_osmec_os` FOREIGN KEY (`ordem_servico_id`) REFERENCES `ordens_servico` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `os_mecanicos`
--

LOCK TABLES `os_mecanicos` WRITE;
/*!40000 ALTER TABLE `os_mecanicos` DISABLE KEYS */;
INSERT INTO `os_mecanicos` VALUES (1,1),(1,3),(2,6),(3,3),(4,5),(5,13),(6,5),(6,9),(8,7),(10,1),(11,6),(12,12),(13,3),(14,13),(15,1);
/*!40000 ALTER TABLE `os_mecanicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `os_pecas_utilizadas`
--

DROP TABLE IF EXISTS `os_pecas_utilizadas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `os_pecas_utilizadas` (
  `ordem_servico_id` int(11) NOT NULL,
  `peca_id` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `preco_aplicado` decimal(10,2) NOT NULL,
  PRIMARY KEY (`ordem_servico_id`,`peca_id`),
  KEY `fk_ospec_peca` (`peca_id`),
  CONSTRAINT `fk_ospec_os` FOREIGN KEY (`ordem_servico_id`) REFERENCES `ordens_servico` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_ospec_peca` FOREIGN KEY (`peca_id`) REFERENCES `pecas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `os_pecas_utilizadas`
--

LOCK TABLES `os_pecas_utilizadas` WRITE;
/*!40000 ALTER TABLE `os_pecas_utilizadas` DISABLE KEYS */;
INSERT INTO `os_pecas_utilizadas` VALUES (1,1,2,180.00),(1,14,1,40.00),(3,1,1,180.00),(4,4,1,160.00),(5,5,2,35.00),(6,12,1,750.00),(8,11,1,40.00),(10,7,1,140.00),(12,8,2,380.00),(13,2,1,45.00),(13,3,4,55.00),(14,13,1,210.00);
/*!40000 ALTER TABLE `os_pecas_utilizadas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `os_servicos_prestados`
--

DROP TABLE IF EXISTS `os_servicos_prestados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `os_servicos_prestados` (
  `ordem_servico_id` int(11) NOT NULL,
  `servico_id` int(11) NOT NULL,
  `preco_aplicado` decimal(10,2) NOT NULL,
  PRIMARY KEY (`ordem_servico_id`,`servico_id`),
  KEY `fk_osser_servico` (`servico_id`),
  CONSTRAINT `fk_osser_os` FOREIGN KEY (`ordem_servico_id`) REFERENCES `ordens_servico` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_osser_servico` FOREIGN KEY (`servico_id`) REFERENCES `servicos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `os_servicos_prestados`
--

LOCK TABLES `os_servicos_prestados` WRITE;
/*!40000 ALTER TABLE `os_servicos_prestados` DISABLE KEYS */;
INSERT INTO `os_servicos_prestados` VALUES (1,2,130.00),(1,15,80.00),(2,1,120.00),(3,2,130.00),(4,7,350.00),(5,4,180.00),(6,9,600.00),(8,5,200.00),(10,6,150.00),(11,1,120.00),(12,12,280.00),(13,3,90.00),(14,11,450.00),(15,13,220.00);
/*!40000 ALTER TABLE `os_servicos_prestados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pecas`
--

DROP TABLE IF EXISTS `pecas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pecas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL,
  `preco_venda` decimal(10,2) NOT NULL,
  `estoque` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pecas`
--

LOCK TABLES `pecas` WRITE;
/*!40000 ALTER TABLE `pecas` DISABLE KEYS */;
INSERT INTO `pecas` VALUES (1,'Pastilha de Freio Dianteira',180.00,45),(2,'Filtro de Óleo Lubrificante',45.00,120),(3,'Óleo de Motor 5W30 (Litro)',55.00,200),(4,'Correia Dentada Continental',160.00,30),(5,'Aditivo de Radiador Concentrado',35.00,85),(6,'Filtro de Ar do Motor',50.00,60),(7,'Jogo de Velas de Ignição (4 un)',140.00,40),(8,'Amortecedor Dianteiro Cofap',380.00,24),(9,'Disco de Freio Ventilado (Par)',290.00,18),(10,'Bateria Moura 60Ah',490.00,15),(11,'Filtro de Cabine (Ar Condicionado)',40.00,50),(12,'Kit de Embreagem LUK',750.00,8),(13,'Bomba de Água Urba',210.00,12),(14,'Fluido de Freio Dot 4 Bosch',30.00,90),(15,'Palheta do Limpador Dyna (Par)',85.00,35);
/*!40000 ALTER TABLE `pecas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicos`
--

DROP TABLE IF EXISTS `servicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(150) NOT NULL,
  `preco_tabela` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicos`
--

LOCK TABLES `servicos` WRITE;
/*!40000 ALTER TABLE `servicos` DISABLE KEYS */;
INSERT INTO `servicos` VALUES (1,'Alinhamento e Balanceamento',120.00),(2,'Troca de Pastilha de Freio',130.00),(3,'Troca de Óleo e Filtro',90.00),(4,'Limpeza do Sistema de Arrefecimento',180.00),(5,'Carga de Gás do Ar Condicionado',200.00),(6,'Regulagem de Motor (Scanner)',150.00),(7,'Troca de Correia Dentada',350.00),(8,'Revisão do Sistema Suspensão',140.00),(9,'Troca de Embreagem',600.00),(10,'Reparo Elétrico (Iluminação)',110.00),(11,'Manutenção de Caixa de Direção',450.00),(12,'Troca de Amortecedores (Par)',280.00),(13,'Limpeza de Bicos Injetores',220.00),(14,'Troca de Fluido de Freio',100.00),(15,'Diagnóstico de Barulho em Geral',80.00),(16,'Alinhamento e Balanceamento 3D',249.50);
/*!40000 ALTER TABLE `servicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `colaborador_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  KEY `colaborador_id` (`colaborador_id`),
  CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`colaborador_id`) REFERENCES `colaboradores` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'carlos.mecanico','1234',1),(2,'marcos.atendente','4321',2),(3,'idd_juliana','1234',1),(4,'idd_tais','1234',2);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veiculos`
--

DROP TABLE IF EXISTS `veiculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `veiculos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `placa` varchar(8) NOT NULL,
  `modelo` varchar(80) NOT NULL,
  `ano` int(11) NOT NULL,
  `cor` varchar(30) NOT NULL,
  `quilometragem` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `placa` (`placa`),
  KEY `fk_veiculos_clientes` (`cliente_id`),
  CONSTRAINT `fk_veiculos_clientes` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veiculos`
--

LOCK TABLES `veiculos` WRITE;
/*!40000 ALTER TABLE `veiculos` DISABLE KEYS */;
INSERT INTO `veiculos` VALUES (1,'ABC-1234','Fiat Uno 1.0',2015,'Prata',124500,1),(2,'XYZ-9876','Chevrolet Onix',2019,'Preto',65000,2),(3,'KJD-4512','Volkswagen Gol',2014,'Branco',142000,3),(4,'MHO-7845','Ford Ka',2018,'Vermelho',78000,4),(5,'LPU-3214','Hyundai HB20',2021,'Cinza',43000,5),(6,'OWE-8855','Toyota Corolla',2017,'Preto',95000,6),(7,'PQA-9922','Honda Civic',2016,'Cinza',110000,7),(8,'JIR-1020','Renault Sandero',2015,'Prata',118000,8),(9,'HFD-3698','Jeep Renegade',2020,'Verde',52000,9),(10,'GTY-1470','Nissan Versa',2018,'Branco',89000,10),(11,'FRT-8520','Fiat Palio',2012,'Azul',165000,11),(12,'DEW-9630','Chevrolet Prisma',2017,'Prata',91000,12),(13,'VGT-7410','Volkswagen Fox',2015,'Preto',105000,13),(14,'BNM-3625','Ford Fiesta',2014,'Branco',130000,14),(15,'KLO-8521','Hyundai Creta',2019,'Prata',71000,15);
/*!40000 ALTER TABLE `veiculos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-20 22:36:44
