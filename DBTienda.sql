-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dbtienda
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `alertas`
--

DROP TABLE IF EXISTS `alertas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alertas` (
  `AlertaID` int NOT NULL AUTO_INCREMENT,
  `ProductoID` int DEFAULT NULL,
  `TipoAlerta` varchar(50) DEFAULT NULL,
  `Mensaje` text,
  `FechaEmitida` datetime DEFAULT NULL,
  PRIMARY KEY (`AlertaID`),
  KEY `ProductoID` (`ProductoID`),
  CONSTRAINT `alertas_ibfk_1` FOREIGN KEY (`ProductoID`) REFERENCES `productos` (`ProductoID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alertas`
--

LOCK TABLES `alertas` WRITE;
/*!40000 ALTER TABLE `alertas` DISABLE KEYS */;
/*!40000 ALTER TABLE `alertas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area` (
  `AreaID` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(255) DEFAULT NULL,
  `Descripcion` text,
  `GananciaPorcentaje` decimal(5,2) DEFAULT '0.00',
  PRIMARY KEY (`AreaID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (1,'Salchichonería',NULL,3.00),(2,'Frutas y Verduras',NULL,0.00),(3,'Panadería',NULL,0.00),(4,'Galletas y Cereales',NULL,0.00),(5,'Bebidas',NULL,8.00),(6,'Productos de Limpieza',NULL,0.00),(7,'Botanas',NULL,90.00),(8,'Cuidado Personal',NULL,0.00),(9,'Congelados',NULL,0.00);
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditoria`
--

DROP TABLE IF EXISTS `auditoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha_hora` datetime DEFAULT CURRENT_TIMESTAMP,
  `procedimiento` varchar(255) DEFAULT NULL,
  `mensaje` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditoria`
--

LOCK TABLES `auditoria` WRITE;
/*!40000 ALTER TABLE `auditoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuracionroles`
--

DROP TABLE IF EXISTS `configuracionroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `configuracionroles` (
  `Rol` varchar(50) NOT NULL,
  `ContraseñaHash` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Rol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracionroles`
--

LOCK TABLES `configuracionroles` WRITE;
/*!40000 ALTER TABLE `configuracionroles` DISABLE KEYS */;
INSERT INTO `configuracionroles` VALUES ('ADMINISTRADOR','8d4ea62f1f99b8e1f4fe03b23d1d1eeb02443c17084900a99d6c6261d5c82327'),('GERENTE','7ce65c7cae3b41c02411558583e88cae4ee09b38baec2ff7b9c7fd16bdaf70af'),('SUPERVISOR','4e08e72a3dfafc1e69b2beae48cee39a2868bb00589efa36db7bc276ddd96449');
/*!40000 ALTER TABLE `configuracionroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detallesventa`
--

DROP TABLE IF EXISTS `detallesventa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detallesventa` (
  `DetalleVentaID` int NOT NULL AUTO_INCREMENT,
  `VentaID` int DEFAULT NULL,
  `ProductoID` int DEFAULT NULL,
  `Cantidad` int DEFAULT NULL,
  `PrecioUnitario` decimal(10,2) DEFAULT NULL,
  `TotalLinea` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`DetalleVentaID`),
  KEY `VentaID` (`VentaID`),
  KEY `ProductoID` (`ProductoID`),
  CONSTRAINT `detallesventa_ibfk_1` FOREIGN KEY (`VentaID`) REFERENCES `ventas` (`VentaID`),
  CONSTRAINT `detallesventa_ibfk_2` FOREIGN KEY (`ProductoID`) REFERENCES `productos` (`ProductoID`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallesventa`
--

LOCK TABLES `detallesventa` WRITE;
/*!40000 ALTER TABLE `detallesventa` DISABLE KEYS */;
INSERT INTO `detallesventa` VALUES (1,1,2046,1,47.80,47.80),(2,2,2046,3,47.80,143.40),(3,3,2046,3,47.80,143.40),(4,4,2046,1,62.14,62.14),(5,5,2046,2,49.23,98.47),(6,5,2047,1,56.65,56.65),(7,6,2047,2,56.65,113.30),(8,6,2048,2,41.72,83.43),(9,7,2047,3,56.65,169.95),(10,7,2048,1,41.72,41.72),(11,7,2046,1,49.23,49.23),(12,8,2046,1,49.23,49.23),(13,8,2047,2,56.65,113.30),(14,8,2048,1,41.72,41.72),(15,9,2047,2,56.65,113.30),(16,9,2048,2,41.72,83.43),(17,9,2052,2,51.71,103.41),(18,9,2046,2,49.23,98.47),(19,10,2046,2,49.23,98.47),(20,10,2047,1,56.65,56.65),(21,10,2048,1,41.72,41.72),(22,11,2046,1,49.23,49.23),(23,11,2047,1,56.65,56.65),(24,12,2046,3,49.23,147.70),(25,12,2047,3,56.65,169.95),(26,12,2048,2,41.72,83.43),(27,13,2046,1,49.23,49.23),(28,13,2047,1,56.65,56.65),(29,14,2046,1,49.23,49.23),(30,14,2047,2,56.65,113.30),(31,14,2048,1,41.72,41.72),(32,15,2046,1,49.23,49.23),(33,15,2047,4,56.65,226.60),(34,16,2046,7,49.23,344.64),(35,16,2048,1,41.72,41.72),(36,17,2046,1,49.23,49.23),(37,17,2048,1,41.72,41.72),(38,18,2046,1,49.23,49.23),(39,18,2047,1,56.65,56.65),(40,18,2048,1,41.72,41.72),(41,19,2046,1,49.23,49.23),(42,19,2047,1,56.65,56.65),(43,19,2048,1,41.72,41.72),(44,20,2047,1,56.65,56.65),(45,21,2046,1,49.23,49.23),(46,22,2046,1,49.23,49.23),(47,23,2048,2,41.72,83.43),(48,24,2046,1,49.23,49.23),(49,25,2046,1,49.23,49.23),(50,26,2047,1,56.65,56.65),(51,27,2046,1,49.23,49.23),(52,28,2047,1,56.65,56.65),(53,29,2047,1,56.65,56.65),(54,30,2046,1,49.23,49.23),(55,31,2046,1,49.23,49.23),(56,32,2048,1,41.72,41.72),(57,33,2048,1,41.72,41.72),(58,34,2048,1,41.72,41.72),(59,35,2047,1,56.65,56.65),(60,36,2047,1,56.65,56.65),(61,37,2047,1,56.65,56.65),(62,38,2047,1,56.65,56.65),(63,39,2047,1,56.65,56.65),(64,40,2047,1,56.65,56.65),(65,40,2048,1,41.72,41.72),(66,41,2047,1,56.65,56.65),(67,41,2048,1,41.72,41.72),(68,42,2047,1,56.65,56.65),(69,43,2047,1,56.65,56.65),(70,44,2047,1,56.65,56.65),(71,45,2046,1,49.23,49.23),(72,46,2048,1,41.72,41.72),(73,47,2048,1,41.72,41.72),(74,48,2046,1,49.23,49.23),(75,49,2046,1,49.23,49.23),(76,50,2048,1,41.72,41.72),(77,51,2046,1,49.23,49.23);
/*!40000 ALTER TABLE `detallesventa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `ProductoID` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(255) DEFAULT NULL,
  `Descripcion` text,
  `AreaID` int DEFAULT NULL,
  `Precio` decimal(10,2) DEFAULT NULL,
  `UnidadesDisponibles` int DEFAULT NULL,
  `NivelReorden` int DEFAULT NULL,
  `FechaCaducidad` date DEFAULT NULL,
  `CodigoBarras` varchar(255) DEFAULT NULL,
  `TamañoNeto` varchar(255) DEFAULT NULL,
  `Marca` varchar(255) DEFAULT NULL,
  `Contenido` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ProductoID`),
  UNIQUE KEY `CodigoBarras` (`CodigoBarras`),
  KEY `CategoriaID` (`AreaID`),
  CONSTRAINT `fk_productos_area` FOREIGN KEY (`AreaID`) REFERENCES `area` (`AreaID`)
) ENGINE=InnoDB AUTO_INCREMENT=2561 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (2046,'Salchicha de pavo',NULL,1,47.80,-2,NULL,'2024-08-15','10001',NULL,'FUD','8 salchichas'),(2047,'Salchicha de Res',NULL,1,55.00,7,NULL,'2024-07-10','10002',NULL,'San Rafael','Paquete de 1kg'),(2048,'Salchicha de Cerdo',NULL,1,40.50,10,NULL,'2024-09-12','10003',NULL,'Zwan','Paquete de 750g'),(2049,'Queso Manchego',NULL,1,62.30,40,NULL,'2024-12-27','10004',NULL,'Lala','Pieza de 500g'),(2050,'Queso Oaxaca',NULL,1,48.70,35,NULL,'2025-04-04','10005',NULL,'Sargento','Paquete de 1kg'),(2051,'Jamón de Pavo',NULL,1,45.90,20,NULL,'2024-08-24','10006',NULL,'Zwan','Paquete de 300g'),(2052,'Salami',NULL,1,50.20,40,NULL,'2024-10-19','10007',NULL,'La Villita','Paquete de 200g'),(2053,'Chorizo Español',NULL,1,65.50,42,NULL,'2024-07-19','10008',NULL,'El Mero','Paquete de 400g'),(2054,'Chorizo Argentino',NULL,1,55.90,28,NULL,'2025-05-14','10009',NULL,'Rica','Paquete de 600g'),(2055,'Jamoneta de Pavo',NULL,1,44.00,25,NULL,'2024-09-13','10010',NULL,'San Rafael','Paquete de 350g'),(2056,'Salchichas Frankfurt',NULL,1,42.50,30,NULL,'2025-02-07','10011',NULL,'Zwan','Paquete de 10 unidades'),(2057,'Salchichón',NULL,1,48.00,20,NULL,'2025-05-04','10012',NULL,'Villa María','Paquete de 250g'),(2058,'Salchicha de Tofu',NULL,1,38.50,36,NULL,'2025-01-21','10013',NULL,'H-E-B','Paquete de 400g'),(2059,'Salchichas Viena',NULL,1,55.00,40,NULL,'2024-06-18','10014',NULL,'FUD','Paquete de 12 unidades'),(2060,'Queso Panela',NULL,1,58.90,30,NULL,'2025-01-31','10015',NULL,'Lala','Pieza de 1kg'),(2061,'Jamón de Pierna',NULL,1,45.00,22,NULL,'2024-11-30','10016',NULL,'San Rafael','Paquete de 500g'),(2062,'Queso Gouda',NULL,1,72.50,18,NULL,'2024-10-26','10017',NULL,'El Mero','Pieza de 750g'),(2063,'Salchichas Rancheras',NULL,1,47.00,35,NULL,'2024-06-13','10018',NULL,'Zwan','Paquete de 8 unidades'),(2064,'Salchichas de Ternera',NULL,1,59.50,27,NULL,'2024-08-21','10019',NULL,'Rica','Paquete de 400g'),(2065,'Queso Asadero',NULL,1,52.00,33,NULL,'2025-04-22','10020',NULL,'Lala','Paquete de 500g'),(2066,'Chorizo Argentino Picante',NULL,1,65.00,20,NULL,'2025-03-29','10021',NULL,'Rica','Paquete de 500g'),(2067,'Jamón de Pavo Bajo en Grasa',NULL,1,40.00,15,NULL,'2025-01-15','10022',NULL,'Zwan','Paquete de 200g'),(2068,'Queso Cotija',NULL,1,62.00,28,NULL,'2025-02-20','10023',NULL,'Lala','Pieza de 300g'),(2069,'Salchichas de Pavo',NULL,1,43.50,25,NULL,'2025-02-15','10024',NULL,'San Rafael','Paquete de 400g'),(2070,'Salchichas de Res al Chipotle',NULL,1,57.80,40,NULL,'2025-02-10','10025',NULL,'FUD','Paquete de 6 unidades'),(2071,'Salchichas de Cerdo con Queso',NULL,1,49.90,35,NULL,'2025-05-16','10026',NULL,'Zwan','Paquete de 500g'),(2072,'Queso Crema',NULL,1,45.00,20,NULL,'2025-02-17','10027',NULL,'Philadelphia','Paquete de 250g'),(2073,'Jamón Serrano',NULL,1,60.50,25,NULL,'2025-04-25','10028',NULL,'La Española','Paquete de 300g'),(2074,'Salchichas de Tofu Ahumadas',NULL,1,35.50,18,NULL,'2024-07-16','10029',NULL,'H-E-B','Paquete de 350g'),(2075,'Salchichas de Pavo con Jalapeño',NULL,1,47.00,30,NULL,'2024-10-15','10030',NULL,'San Rafael','Paquete de 450g'),(2076,'Queso Fresco',NULL,1,54.00,40,NULL,'2024-09-01','10031',NULL,'Los Altos','Pieza de 500g'),(2077,'Chorizo Español Extra',NULL,1,69.00,15,NULL,'2025-04-20','10032',NULL,'El Mero','Paquete de 300g'),(2078,'Jamón de Pavo en Rebanadas',NULL,1,42.50,22,NULL,'2024-12-18','10033',NULL,'Zwan','Paquete de 200g'),(2079,'Salchichas de Res con Chipotle',NULL,1,58.80,28,NULL,'2025-05-11','10034',NULL,'FUD','Paquete de 400g'),(2080,'Salchichas de Cerdo a la Parrilla',NULL,1,45.20,32,NULL,'2025-04-21','10035',NULL,'Zwan','Paquete de 350g'),(2081,'Queso Cheddar',NULL,1,67.00,24,NULL,'2025-02-04','10036',NULL,'Kraft','Paquete de 450g'),(2082,'Salchichas de Ternera con Hierbas',NULL,1,63.50,40,NULL,'2025-03-11','10037',NULL,'Rica','Paquete de 500g'),(2083,'Salchichas de Pavo con Queso',NULL,1,49.00,30,NULL,'2024-10-23','10038',NULL,'San Rafael','Paquete de 600g'),(2084,'Salchichas de Res con Mostaza',NULL,1,56.70,20,NULL,'2025-03-30','10039',NULL,'FUD','Paquete de 300g'),(2085,'Salchichas de Cerdo con Tocino',NULL,1,52.30,25,NULL,'2025-03-06','10040',NULL,'Zwan','Paquete de 400g'),(2086,'Queso Provolone',NULL,1,70.00,32,NULL,'2024-09-02','10041',NULL,'El Mero','Pieza de 600g'),(2087,'Chorizo Argentino Extra',NULL,1,67.50,18,NULL,'2024-06-01','10042',NULL,'Rica','Paquete de 450g'),(2088,'Jamón de Pavo Extra Magro',NULL,1,43.00,24,NULL,'2025-03-19','10043',NULL,'Zwan','Paquete de 250g'),(2089,'Salchichas de Tofu a la Barbacoa',NULL,1,39.50,35,NULL,'2025-02-14','10044',NULL,'H-E-B','Paquete de 500g'),(2090,'Salchichas de Pavo con Chile',NULL,1,45.00,28,NULL,'2024-11-18','10045',NULL,'San Rafael','Paquete de 350g'),(2091,'Queso Gruyere',NULL,1,65.00,20,NULL,'2025-02-06','10046',NULL,'El Mero','Paquete de 400g'),(2092,'Salchichas de Res con Chipotle',NULL,1,59.80,32,NULL,'2025-04-22','10047',NULL,'FUD','Paquete de 450g'),(2093,'Salchichas de Cerdo con Queso',NULL,1,48.50,27,NULL,'2024-10-31','10048',NULL,'Zwan','Paquete de 550g'),(2094,'Queso Havarti',NULL,1,56.00,22,NULL,'2024-07-13','10049',NULL,'Los Altos','Pieza de 500g'),(2095,'Chorizo Argentino',NULL,1,63.00,30,NULL,'2025-04-03','10050',NULL,'Rica','Paquete de 500g'),(2096,'Jamón de Pavo Extra Ahumado',NULL,1,49.80,18,NULL,'2024-05-30','10051',NULL,'Zwan','Paquete de 350g'),(2097,'Jamón de Pavo Bajo en Grasa',NULL,1,42.00,20,NULL,'2024-10-09','10052',NULL,'San Rafael','Paquete de 300g'),(2098,'Jamón de Pavo Ahumado en Rebanadas',NULL,1,45.00,22,NULL,'2024-12-26','10053',NULL,'FUD','Paquete de 250g'),(2099,'Jamón de Pavo en Rodajas',NULL,1,39.50,25,NULL,'2025-04-14','10054',NULL,'Zwan','Paquete de 200g'),(2100,'Jamón de Pavo Bajo en Sodio',NULL,1,44.50,30,NULL,'2024-12-14','10055',NULL,'San Rafael','Paquete de 350g'),(2101,'Cecina Natural',NULL,1,62.00,20,NULL,'2025-01-21','10056',NULL,'El Mexicano','Paquete de 500g'),(2102,'Cecina Adobada',NULL,1,58.50,18,NULL,'2024-11-17','10057',NULL,'Carnes Gómez','Paquete de 400g'),(2103,'Tasajo',NULL,1,55.00,15,NULL,'2024-10-08','10058',NULL,'Carnicería San José','Paquete de 600g'),(2104,'Tocino Ahumado',NULL,1,48.00,25,NULL,'2024-07-27','10059',NULL,'Smithfield','Paquete de 300g'),(2105,'Tocino de Pavo',NULL,1,42.00,28,NULL,'2024-06-27','10060',NULL,'Zwan','Paquete de 250g'),(2106,'Queso Gouda',NULL,1,65.00,30,NULL,'2025-01-06','10061',NULL,'El Mero','Pieza de 600g'),(2107,'Queso Havarti',NULL,1,58.00,35,NULL,'2025-05-13','10062',NULL,'Los Altos','Pieza de 500g'),(2108,'Queso Cheddar Añejo',NULL,1,70.00,22,NULL,'2024-11-05','10063',NULL,'Lala','Pieza de 750g'),(2109,'Queso Cotija',NULL,1,55.00,20,NULL,'2025-02-06','10064',NULL,'El Mexicano','Pieza de 400g'),(2110,'Queso de Bola Edam',NULL,1,72.00,18,NULL,'2024-07-31','10065',NULL,'El Mero','Pieza de 800g'),(2111,'Queso Manchego Viejo',NULL,1,65.00,15,NULL,'2024-10-02','10066',NULL,'Los Altos','Pieza de 500g'),(2112,'Queso de Oveja',NULL,1,68.00,20,NULL,'2024-05-23','10067',NULL,'Castañeda','Pieza de 600g'),(2113,'Queso de Cabra',NULL,1,60.00,25,NULL,'2024-10-23','10068',NULL,'El Mero','Pieza de 450g'),(2114,'Queso Azul',NULL,1,63.00,28,NULL,'2024-07-09','10069',NULL,'Lala','Pieza de 400g'),(2115,'Queso Gruyère',NULL,1,67.00,20,NULL,'2024-12-24','10070',NULL,'Los Altos','Pieza de 550g'),(2116,'Queso Feta',NULL,1,58.00,22,NULL,'2025-02-02','10071',NULL,'El Mexicano','Pieza de 350g'),(2117,'Queso Roquefort',NULL,1,65.00,18,NULL,'2025-03-09','10072',NULL,'El Mero','Pieza de 300g'),(2118,'Queso Parmesano',NULL,1,60.00,20,NULL,'2024-08-25','10073',NULL,'Lala','Pieza de 450g'),(2119,'Queso Provolone',NULL,1,62.00,25,NULL,'2025-01-01','10074',NULL,'Los Altos','Pieza de 500g'),(2120,'Queso Edam',NULL,1,65.00,30,NULL,'2024-12-12','10075',NULL,'El Mero','Pieza de 600g'),(2121,'Queso Emmental',NULL,1,68.00,22,NULL,'2025-03-30','10076',NULL,'Los Altos','Pieza de 400g'),(2122,'Queso de Cabra Curado',NULL,1,63.00,18,NULL,'2024-07-30','10077',NULL,'Castañeda','Pieza de 350g'),(2123,'Queso Mozzarella',NULL,1,60.00,20,NULL,'2025-01-03','10078',NULL,'Lala','Pieza de 450g'),(2124,'Queso de Búfala',NULL,1,65.00,25,NULL,'2024-12-18','10079',NULL,'El Mero','Pieza de 550g'),(2125,'Queso Fresco',NULL,1,58.00,28,NULL,'2025-03-18','10080',NULL,'Los Altos','Pieza de 500g'),(2126,'Queso Panela',NULL,1,62.00,30,NULL,'2025-04-08','10081',NULL,'El Mexicano','Pieza de 600g'),(2127,'Queso Crema',NULL,1,48.00,22,NULL,'2025-04-16','10082',NULL,'Philadelphia','Paquete de 300g'),(2128,'Queso Cottage',NULL,1,55.00,20,NULL,'2025-03-09','10083',NULL,'Lala','Paquete de 400g'),(2129,'Queso Ricotta',NULL,1,60.00,25,NULL,'2024-11-25','10084',NULL,'El Mexicano','Paquete de 500g'),(2130,'Queso Asadero',NULL,1,57.00,28,NULL,'2024-09-16','10085',NULL,'Lala','Paquete de 450g'),(2131,'Queso Panela Light',NULL,1,58.00,30,NULL,'2025-05-04','10086',NULL,'El Mexicano','Paquete de 600g'),(2132,'Queso Oaxaca',NULL,1,50.00,22,NULL,'2024-10-30','10087',NULL,'Lala','Paquete de 350g'),(2133,'Queso Cotija',NULL,1,58.00,28,NULL,'2024-08-06','10088',NULL,'El Mexicano','Paquete de 400g'),(2134,'Queso Reblochon',NULL,1,65.00,20,NULL,'2025-03-16','10089',NULL,'Los Altos','Paquete de 500g'),(2135,'Queso Gouda Ahumado',NULL,1,68.00,25,NULL,'2024-08-19','10090',NULL,'El Mero','Paquete de 550g'),(2136,'Queso Camembert',NULL,1,70.00,30,NULL,'2025-01-15','10091',NULL,'Los Altos','Paquete de 600g'),(2137,'Queso Cheddar',NULL,1,58.00,22,NULL,'2024-06-28','10092',NULL,'Lala','Paquete de 450g'),(2138,'Queso Manchego',NULL,1,62.00,28,NULL,'2024-12-31','10093',NULL,'El Mexicano','Paquete de 400g'),(2139,'Queso Azul Gourmet',NULL,1,65.00,32,NULL,'2024-07-19','10094',NULL,'Los Altos','Paquete de 500g'),(2140,'Queso de Cabra a las Finas Hierbas',NULL,1,68.00,35,NULL,'2024-12-16','10095',NULL,'Castañeda','Paquete de 600g'),(2141,'Queso Brie',NULL,1,60.00,25,NULL,'2024-08-11','10096',NULL,'Los Altos','Paquete de 450g'),(2142,'Queso Emmental Light',NULL,1,55.00,20,NULL,'2024-12-18','10097',NULL,'El Mero','Paquete de 400g'),(2143,'Queso Gruyère Viejo',NULL,1,65.00,28,NULL,'2025-04-01','10098',NULL,'Los Altos','Paquete de 500g'),(2144,'Queso de Búfala Light',NULL,1,62.00,30,NULL,'2025-01-24','10099',NULL,'El Mero','Paquete de 550g'),(2145,'Queso Mozzarella en Lonchas',NULL,1,50.00,22,NULL,'2024-09-07','10100',NULL,'Lala','Paquete de 300g'),(2146,'Manzana Gala',NULL,2,25.00,100,NULL,'2024-06-09','20001',NULL,'Productores Unidos','1 kg'),(2147,'Plátano Dominico',NULL,2,15.00,150,NULL,'2024-09-03','20002',NULL,'Bananera Nacional','1 kg'),(2148,'Naranja Valencia',NULL,2,20.00,120,NULL,'2024-06-13','20003',NULL,'Cítricos del Sur','1 kg'),(2149,'Pera Bartlett',NULL,2,30.00,80,NULL,'2024-10-09','20004',NULL,'Productores Unidos','1 kg'),(2150,'Fresa Fresca',NULL,2,12.00,200,NULL,'2025-03-13','20005',NULL,'Fresuras S.A.','250 g'),(2151,'Piña Golden',NULL,2,18.00,90,NULL,'2025-03-27','20006',NULL,'Frutas del Caribe','1 unidad'),(2152,'Melón Cantalupo',NULL,2,22.00,70,NULL,'2025-01-03','20007',NULL,'Frutas del Campo','1 unidad'),(2153,'Sandía Dulce',NULL,2,25.00,60,NULL,'2024-07-24','20008',NULL,'Sandier S.A.','1 unidad'),(2154,'Uva Roja',NULL,2,35.00,120,NULL,'2024-08-11','20009',NULL,'Frutas del Valle','500 g'),(2155,'Durazno Amarillo',NULL,2,28.00,80,NULL,'2025-02-21','20010',NULL,'Cosecha Fresca','1 kg'),(2156,'Kiwi Importado',NULL,2,40.00,100,NULL,'2025-01-23','20011',NULL,'Exóticos S.A.','1 kg'),(2157,'Mango Ataulfo',NULL,2,20.00,150,NULL,'2024-11-28','20012',NULL,'Mangos del Pacífico','1 kg'),(2158,'Aguacate Hass',NULL,2,50.00,90,NULL,'2024-08-13','20013',NULL,'Aguacateros Unidos','1 kg'),(2159,'Tomate Saladette',NULL,2,18.00,200,NULL,'2024-11-22','20014',NULL,'Hortalizas Nacionales','1 kg'),(2160,'Pepino Cohombro',NULL,2,12.00,120,NULL,'2024-09-05','20015',NULL,'Hortalizas del Campo','1 kg'),(2161,'Zanahoria Dulce',NULL,2,15.00,150,NULL,'2024-05-30','20016',NULL,'Hortalizas Nacionales','1 kg'),(2162,'Pimiento Rojo',NULL,2,30.00,100,NULL,'2024-06-20','20017',NULL,'Hortalizas del Campo','1 kg'),(2163,'Calabacín Verde',NULL,2,25.00,80,NULL,'2024-08-12','20018',NULL,'Hortalizas Nacionales','1 kg'),(2164,'Cebolla Blanca',NULL,2,10.00,120,NULL,'2025-02-04','20019',NULL,'Hortalizas del Campo','1 kg'),(2165,'Lechuga Romana',NULL,2,8.00,150,NULL,'2025-05-07','20020',NULL,'Hortalizas Frescas','1 unidad'),(2166,'Espinaca Fresca',NULL,2,12.00,100,NULL,'2025-03-21','20021',NULL,'Hortalizas del Campo','250 g'),(2167,'Repollo Verde',NULL,2,10.00,80,NULL,'2024-10-23','20022',NULL,'Hortalizas Nacionales','1 kg'),(2168,'Papa Blanca',NULL,2,12.00,200,NULL,'2025-04-28','20023',NULL,'Papas del Norte','1 kg'),(2169,'Camote Naranja',NULL,2,15.00,120,NULL,'2024-09-28','20024',NULL,'Hortalizas Frescas','1 kg'),(2170,'Acelga Fresca',NULL,2,10.00,100,NULL,'2025-01-25','20025',NULL,'Hortalizas del Campo','250 g'),(2171,'Brócoli Fresco',NULL,2,18.00,80,NULL,'2025-04-22','20026',NULL,'Hortalizas Nacionales','1 unidad'),(2172,'Coliflor Blanca',NULL,2,20.00,90,NULL,'2024-06-13','20027',NULL,'Hortalizas Frescas','1 unidad'),(2173,'Judía Verde',NULL,2,15.00,120,NULL,'2025-02-10','20028',NULL,'Hortalizas del Campo','500 g'),(2174,'Chayote Fresco',NULL,2,10.00,100,NULL,'2024-09-10','20029',NULL,'Hortalizas Nacionales','1 kg'),(2175,'Haba Tierna',NULL,2,22.30,80,NULL,'2024-10-03','20030',NULL,'Hortalizas del Campo','1 kg'),(2176,'Pan Blanco',NULL,3,15.00,40,NULL,'2024-07-19','30001',NULL,'La Central','1 unidad'),(2177,'Bollos de Leche',NULL,3,18.00,30,NULL,'2024-06-05','30002',NULL,'Panadería San José','6 unidades'),(2178,'Conchas',NULL,3,10.00,35,NULL,'2025-05-19','30003',NULL,'Panadería Santa María','1 unidad'),(2179,'Rosca de Reyes',NULL,3,50.00,25,NULL,'2024-07-27','30004',NULL,'Panadería Las Delicias','1 unidad'),(2180,'Croissants',NULL,3,20.00,30,NULL,'2025-04-13','30005',NULL,'La Francia','4 unidades'),(2181,'Pan de Nuez',NULL,3,25.00,20,NULL,'2024-09-23','30006',NULL,'Panadería Nueces y Miel','1 unidad'),(2182,'Bollos de Canela',NULL,3,22.00,25,NULL,'2025-04-28','30007',NULL,'Panadería Canela y Azúcar','6 unidades'),(2183,'Baguette',NULL,3,12.00,40,NULL,'2024-08-13','30008',NULL,'Panadería Parisienne','1 unidad'),(2184,'Pan Integral',NULL,3,18.00,35,NULL,'2024-07-23','30009',NULL,'La Central','1 unidad'),(2185,'Pan de Queso',NULL,3,20.00,30,NULL,'2024-08-03','30010',NULL,'Panadería Queso y Mantequilla','1 unidad'),(2186,'Pan de Chocolate',NULL,3,22.00,25,NULL,'2025-04-30','30011',NULL,'La Francia','1 unidad'),(2187,'Empanadas de Pollo',NULL,3,30.00,20,NULL,'2024-09-30','30012',NULL,'Panadería Las Empanadas','6 unidades'),(2188,'Pan de Centeno',NULL,3,16.00,30,NULL,'2024-08-01','30013',NULL,'Panadería Centeno Fresco','1 unidad'),(2189,'Pan de Avena',NULL,3,18.00,35,NULL,'2025-01-26','30014',NULL,'Panadería Avena y Miel','1 unidad'),(2190,'Pan de Pasas',NULL,3,22.00,25,NULL,'2024-11-15','30015',NULL,'La Francia','1 unidad'),(2191,'Conchas Rellenas de Crema',NULL,3,20.00,30,NULL,'2024-11-30','30016',NULL,'Panadería Santa María','4 unidades'),(2192,'Pan de Centeno Integral',NULL,3,16.00,30,NULL,'2024-08-04','30017',NULL,'Panadería Centeno Fresco','1 unidad'),(2193,'Pan de Nuez y Pasas',NULL,3,24.00,25,NULL,'2025-04-16','30018',NULL,'Panadería Nueces y Miel','1 unidad'),(2194,'Pan de Elote',NULL,3,20.00,20,NULL,'2024-12-13','30019',NULL,'Panadería Maíz y Dulzura','1 unidad'),(2195,'Bollos de Vainilla',NULL,3,18.00,30,NULL,'2024-11-19','30020',NULL,'Panadería Vainilla y Azúcar','6 unidades'),(2196,'Galletas de Avena',NULL,4,20.00,50,NULL,'2024-12-30','40001',NULL,'Quaker','Paquete de 300g'),(2197,'Galletas de Chocolate',NULL,4,25.00,40,NULL,'2025-01-08','40002',NULL,'Chips Ahoy!','Paquete de 350g'),(2198,'Galletas de Vainilla',NULL,4,18.00,45,NULL,'2025-04-05','40003',NULL,'María','Paquete de 400g'),(2199,'Galletas de Mantequilla',NULL,4,15.00,35,NULL,'2025-01-03','40004',NULL,'Keebler','Paquete de 250g'),(2200,'Galletas de Chocolate y Nueces',NULL,4,22.00,30,NULL,'2024-05-24','40005',NULL,'Pepperidge Farm','Paquete de 300g'),(2201,'Cereal de Maíz',NULL,4,28.00,40,NULL,'2025-04-09','40006',NULL,'Kellogg\'s','Caja de 500g'),(2202,'Cereal de Trigo',NULL,4,26.00,35,NULL,'2024-09-21','40007',NULL,'Post','Caja de 450g'),(2203,'Cereal de Avena',NULL,4,30.00,45,NULL,'2024-07-24','40008',NULL,'Quaker','Caja de 400g'),(2204,'Cereal de Arroz Inflado',NULL,4,24.00,50,NULL,'2024-06-29','40009',NULL,'Kellogg\'s','Caja de 350g'),(2205,'Cereal de Miel y Almendras',NULL,4,32.00,40,NULL,'2025-04-28','40010',NULL,'Nature Valley','Caja de 400g'),(2206,'Cereal de Frutas',NULL,4,26.00,45,NULL,'2025-04-08','40011',NULL,'Special K','Caja de 350g'),(2207,'Galletas Integrales',NULL,4,18.00,30,NULL,'2025-02-27','40012',NULL,'Fiber One','Paquete de 300g'),(2208,'Galletas de Coco',NULL,4,16.00,35,NULL,'2025-03-08','40013',NULL,'Keebler','Paquete de 250g'),(2209,'Galletas de Avena y Pasas',NULL,4,20.00,40,NULL,'2025-01-24','40014',NULL,'Quaker','Paquete de 300g'),(2210,'Galletas de Chispas de Chocolate',NULL,4,24.00,45,NULL,'2024-06-30','40015',NULL,'Chips Deluxe','Paquete de 350g'),(2211,'Cereal de Granola',NULL,4,28.00,35,NULL,'2025-01-10','40016',NULL,'Bear Naked','Caja de 400g'),(2212,'Cereal de Salvado',NULL,4,26.00,40,NULL,'2024-08-06','40017',NULL,'Post','Caja de 450g'),(2213,'Cereal de Maíz con Miel',NULL,4,30.00,50,NULL,'2024-07-23','40018',NULL,'Kellogg\'s','Caja de 500g'),(2214,'Cereal de Arroz con Chocolate',NULL,4,25.00,40,NULL,'2025-04-19','40019',NULL,'Cocoa Pebbles','Caja de 350g'),(2215,'Cereal de Trigo Integral',NULL,4,28.00,45,NULL,'2025-02-22','40020',NULL,'Fiber One','Caja de 400g'),(2216,'Galletas Rellenas de Crema',NULL,4,22.00,30,NULL,'2025-01-28','40021',NULL,'Oreo','Paquete de 350g'),(2217,'Galletas de Miel',NULL,4,18.00,35,NULL,'2025-01-12','40022',NULL,'Nature Valley','Paquete de 300g'),(2218,'Galletas de Mantequilla de Maní',NULL,4,16.00,40,NULL,'2024-09-08','40023',NULL,'Nutter Butter','Paquete de 250g'),(2219,'Galletas de Canela',NULL,4,24.00,45,NULL,'2024-12-25','40024',NULL,'Cinnamon Toast Crunch','Paquete de 350g'),(2220,'Cereal de Hojuelas de Maíz',NULL,4,26.00,35,NULL,'2024-08-17','40025',NULL,'Corn Flakes','Caja de 400g'),(2221,'Cereal de Hojuelas de Avena',NULL,4,28.00,40,NULL,'2024-12-22','40026',NULL,'Quaker','Caja de 450g'),(2222,'Cereal de Hojuelas de Trigo',NULL,4,30.00,50,NULL,'2024-09-29','40027',NULL,'Kellogg\'s','Caja de 500g'),(2223,'Cereal de Hojuelas de Arroz',NULL,4,25.00,40,NULL,'2025-03-26','40028',NULL,'Rice Krispies','Caja de 350g'),(2224,'Galletas de Jengibre',NULL,4,20.00,45,NULL,'2025-03-26','40029',NULL,'Ginger Snaps','Paquete de 300g'),(2225,'Galletas de Limón',NULL,4,18.00,30,NULL,'2024-06-26','40030',NULL,'Lemon Coolers','Paquete de 250g'),(2226,'Galletas de Chocolate Blanco',NULL,4,24.00,35,NULL,'2025-03-28','40031',NULL,'White Chocolate Macadamia','Paquete de 350g'),(2227,'Cereal de Hojuelas de Maíz con Azúcar',NULL,4,26.00,45,NULL,'2024-07-27','40032',NULL,'Frosted Flakes','Caja de 400g'),(2228,'Cereal de Maíz con Miel y Almendras',NULL,4,30.00,50,NULL,'2025-03-25','40033',NULL,'Honey Bunches of Oats','Caja de 450g'),(2229,'Cereal de Maíz con Chocolate',NULL,4,28.00,40,NULL,'2024-08-04','40034',NULL,'Chocolate Frosted Flakes','Caja de 500g'),(2230,'Cereal de Arroz con Miel',NULL,4,25.00,45,NULL,'2024-11-25','40035',NULL,'Honey Smacks','Caja de 350g'),(2231,'Cereal de Arroz con Chocolate y Malvaviscos',NULL,4,26.00,35,NULL,'2024-06-19','40036',NULL,'Cocoa Krispies','Caja de 400g'),(2232,'Cereal de Trigo con Miel',NULL,4,28.00,40,NULL,'2024-06-04','40037',NULL,'Honey Nut Cheerios','Caja de 450g'),(2233,'Cereal de Trigo con Miel y Almendras',NULL,4,32.00,50,NULL,'2024-11-20','40038',NULL,'Honey Bunches of Oats with Almonds','Caja de 500g'),(2234,'Cereal de Trigo con Pasas',NULL,4,26.00,40,NULL,'2025-01-22','40039',NULL,'Raisin Bran','Caja de 400g'),(2235,'Cereal de Trigo con Chocolate',NULL,4,28.00,45,NULL,'2024-07-23','40040',NULL,'Chocolate Cheerios','Caja de 450g'),(2236,'Galletas de Avena y Chocolate',NULL,4,22.00,30,NULL,'2024-11-18','40041',NULL,'Quaker','Paquete de 350g'),(2237,'Galletas de Vainilla y Limón',NULL,4,20.00,35,NULL,'2024-08-24','40042',NULL,'Vanilla Lemon Wafers','Paquete de 300g'),(2238,'Galletas de Naranja y Jengibre',NULL,4,18.00,40,NULL,'2024-09-17','40043',NULL,'Orange Ginger Snaps','Paquete de 250g'),(2239,'Galletas de Avena y Pasas',NULL,4,24.00,45,NULL,'2025-04-05','40044',NULL,'Raisin Oatmeal Cookies','Paquete de 350g'),(2240,'Cereal de Hojuelas de Avena con Pasas',NULL,4,26.00,35,NULL,'2024-09-27','40045',NULL,'Quaker','Caja de 400g'),(2241,'Cereal de Hojuelas de Trigo con Frutas',NULL,4,28.00,40,NULL,'2025-04-26','40046',NULL,'Kellogg\'s','Caja de 450g'),(2242,'Cereal de Hojuelas de Arroz con Miel y Almendras',NULL,4,30.00,50,NULL,'2024-06-17','40047',NULL,'Honey Bunches of Oats with Almonds','Caja de 500g'),(2243,'Cereal de Maíz con Miel y Nueces',NULL,4,26.00,40,NULL,'2025-03-10','40048',NULL,'Honey Bunches of Oats with Pecan Bunches','Caja de 400g'),(2244,'Cereal de Arroz Inflado con Chocolate',NULL,4,28.00,45,NULL,'2024-12-21','40049',NULL,'Cocoa Puffs','Caja de 450g'),(2245,'Cereal de Trigo con Miel y Nueces',NULL,4,30.00,50,NULL,'2025-02-03','40050',NULL,'Honey Nut Cheerios','Caja de 500g'),(2246,'Refresco de Cola',NULL,5,15.00,150,NULL,'2024-08-14','50001',NULL,'Coca-Cola','Botella de 2 litros'),(2247,'Agua Mineral',NULL,5,10.00,120,NULL,'2024-11-16','50002',NULL,'Ciel','Botella de 1.5 litros'),(2248,'Jugo de Naranja',NULL,5,20.00,80,NULL,'2025-03-18','50003',NULL,'Del Valle','Botella de 1 litro'),(2249,'Té Verde',NULL,5,18.00,60,NULL,'2024-09-19','50004',NULL,'Lipton','Botella de 500ml'),(2250,'Cerveza Lager',NULL,5,22.00,50,NULL,'2024-10-22','50005',NULL,'Corona','Lata de 355ml'),(2251,'Vino Tinto',NULL,5,35.00,40,NULL,'2024-11-23','50006',NULL,'Casillero del Diablo','Botella de 750ml'),(2252,'Whisky Escocés',NULL,5,50.00,30,NULL,'2024-10-22','50007',NULL,'Johnnie Walker','Botella de 750ml'),(2253,'Ron Dorado',NULL,5,40.00,35,NULL,'2024-10-01','50008',NULL,'Havana Club','Botella de 750ml'),(2254,'Tequila Reposado',NULL,5,45.00,25,NULL,'2024-09-13','50009',NULL,'José Cuervo','Botella de 750ml'),(2255,'Refresco de Limón',NULL,5,15.00,90,NULL,'2025-04-17','50010',NULL,'Sprite','Botella de 2 litros'),(2256,'Agua Mineral con Gas',NULL,5,12.00,80,NULL,'2024-08-02','50011',NULL,'Perrier','Botella de 750ml'),(2257,'Jugo de Manzana',NULL,5,20.00,70,NULL,'2025-03-01','50012',NULL,'Del Valle','Botella de 1 litro'),(2258,'Té Negro',NULL,5,18.00,55,NULL,'2024-05-30','50013',NULL,'Twinings','Botella de 500ml'),(2259,'Cerveza IPA',NULL,5,25.00,45,NULL,'2024-08-26','50014',NULL,'Heineken','Lata de 355ml'),(2260,'Vino Blanco',NULL,5,30.00,35,NULL,'2024-06-18','50015',NULL,'Santa Carolina','Botella de 750ml'),(2261,'Vodka',NULL,5,40.00,40,NULL,'2025-01-04','50016',NULL,'Absolut','Botella de 750ml'),(2262,'Ginebra',NULL,5,35.00,30,NULL,'2024-11-12','50017',NULL,'Bombay Sapphire','Botella de 750ml'),(2263,'Whisky Bourbon',NULL,5,45.00,25,NULL,'2024-09-28','50018',NULL,'Jack Daniel\'s','Botella de 750ml'),(2264,'Tequila Blanco',NULL,5,50.00,20,NULL,'2024-06-29','50019',NULL,'Patrón','Botella de 750ml'),(2265,'Refresco de Toronja',NULL,5,15.00,85,NULL,'2025-05-04','50020',NULL,'Fanta','Botella de 2 litros'),(2266,'Agua Mineral Saborizada',NULL,5,10.00,75,NULL,'2025-04-14','50021',NULL,'Dasani','Botella de 500ml'),(2267,'Jugo de Uva',NULL,5,20.00,65,NULL,'2024-07-11','50022',NULL,'Del Valle','Botella de 1 litro'),(2268,'Té de Hierbas',NULL,5,18.00,50,NULL,'2024-06-15','50023',NULL,'Tazo','Botella de 500ml'),(2269,'Cerveza Stout',NULL,5,28.00,40,NULL,'2024-11-13','50024',NULL,'Guinness','Lata de 355ml'),(2270,'Vino Rosado',NULL,5,25.00,30,NULL,'2025-02-03','50025',NULL,'Cono Sur','Botella de 750ml'),(2271,'Coñac',NULL,5,50.00,35,NULL,'2025-02-22','50026',NULL,'Hennessy','Botella de 750ml'),(2272,'Licor de Café',NULL,5,40.00,25,NULL,'2025-05-17','50027',NULL,'Kahlúa','Botella de 750ml'),(2273,'Rum Blanco',NULL,5,45.00,30,NULL,'2024-08-10','50028',NULL,'Bacardi','Botella de 750ml'),(2274,'Mezcal',NULL,5,55.00,20,NULL,'2025-03-04','50029',NULL,'Montelobos','Botella de 750ml'),(2275,'Refresco de Manzana',NULL,5,15.00,80,NULL,'2024-11-23','50030',NULL,'Sidral Mundet','Botella de 2 litros'),(2276,'Agua de Coco',NULL,5,12.00,70,NULL,'2024-10-31','50031',NULL,'Vita Coco','Botella de 500ml'),(2277,'Jugo de Piña',NULL,5,20.00,60,NULL,'2024-08-06','50032',NULL,'Del Valle','Botella de 1 litro'),(2278,'Té Oolong',NULL,5,18.00,45,NULL,'2025-03-05','50033',NULL,'Foojoy','Botella de 500ml'),(2279,'Cerveza Pale Ale',NULL,5,26.00,35,NULL,'2024-12-22','50034',NULL,'Sierra Nevada','Lata de 355ml'),(2280,'Detergente Líquido para Ropa',NULL,6,35.00,50,NULL,'2024-11-24','60001',NULL,'Ariel','1 litro'),(2281,'Limpiador Multiusos',NULL,6,25.00,40,NULL,'2024-10-19','60002',NULL,'Clorox','1 litro'),(2282,'Desinfectante de Superficies',NULL,6,30.00,45,NULL,'2025-02-20','60003',NULL,'Lysol','500 ml'),(2283,'Jabón para Trastes',NULL,6,20.00,35,NULL,'2024-12-19','60004',NULL,'Dawn','750 ml'),(2284,'Limpiavidrios',NULL,6,18.00,40,NULL,'2025-04-09','60005',NULL,'Windex','500 ml'),(2285,'Desengrasante',NULL,6,22.00,30,NULL,'2024-09-04','60006',NULL,'Mr. Muscle','750 ml'),(2286,'Quitamanchas',NULL,6,28.00,25,NULL,'2024-05-24','60007',NULL,'Vanish','500 ml'),(2287,'Blanqueador',NULL,6,15.00,45,NULL,'2024-06-24','60008',NULL,'Cloralex','1 litro'),(2288,'Suavizante de Telas',NULL,6,30.00,50,NULL,'2025-04-01','60009',NULL,'Downy','1 litro'),(2289,'Toallas de Papel',NULL,6,12.00,55,NULL,'2024-09-01','60010',NULL,'Scott','Rollo de 2 paquetes'),(2290,'Servilletas de Papel',NULL,6,8.00,60,NULL,'2024-07-25','60011',NULL,'Viva','Paquete de 100 unidades'),(2291,'Bolsas de Basura',NULL,6,20.00,40,NULL,'2024-09-22','60012',NULL,'Hefty','Rollo de 30 bolsas'),(2292,'Esponjas para Limpieza',NULL,6,10.00,50,NULL,'2024-08-30','60013',NULL,'Scotch-Brite','Paquete de 5 unidades'),(2293,'Trapo de Microfibra',NULL,6,6.00,45,NULL,'2024-12-09','60014',NULL,'Quickie','1 unidad'),(2294,'Cepillo para Inodoro',NULL,6,8.00,30,NULL,'2024-11-01','60015',NULL,'Scotch-Brite','1 unidad'),(2295,'Escoba',NULL,6,18.00,35,NULL,'2025-03-07','60016',NULL,'Swiffer','1 unidad'),(2296,'Recogedor',NULL,6,10.00,40,NULL,'2024-05-29','60017',NULL,'Libman','1 unidad'),(2297,'Plumero',NULL,6,12.00,45,NULL,'2024-05-25','60018',NULL,'O-Cedar','1 unidad'),(2298,'Mopa',NULL,6,25.00,50,NULL,'2024-07-04','60019',NULL,'Rubbermaid','1 unidad'),(2299,'Desodorante de Ambiente',NULL,6,15.00,55,NULL,'2024-10-11','60020',NULL,'Glade','250 ml'),(2300,'Desinfectante de Manos',NULL,6,10.00,60,NULL,'2024-10-20','60021',NULL,'Purell','250 ml'),(2301,'Limpiador de Baños',NULL,6,20.00,45,NULL,'2025-04-14','60022',NULL,'Tilex','500 ml'),(2302,'Limpiador de Cocina',NULL,6,18.00,40,NULL,'2024-08-29','60023',NULL,'Easy-Off','750 ml'),(2303,'Limpiador de Pisos',NULL,6,20.00,50,NULL,'2024-12-02','60024',NULL,'Pine-Sol','1 litro'),(2304,'Limpiador de Muebles',NULL,6,22.00,35,NULL,'2024-06-15','60025',NULL,'Old English','500 ml'),(2305,'Desatascador de Cañerías',NULL,6,25.00,30,NULL,'2024-11-07','60026',NULL,'Liquid Plumr','500 ml'),(2306,'Pastillas para Lavavajillas',NULL,6,30.00,55,NULL,'2024-08-07','60027',NULL,'Finish','Paquete de 20 unidades'),(2307,'Aromatizante de Telas',NULL,6,15.00,40,NULL,'2024-12-26','60028',NULL,'Febreze','250 ml'),(2308,'Desengrasante para Cocina',NULL,6,20.00,35,NULL,'2025-04-21','60029',NULL,'Easy-Off','500 ml'),(2309,'Limpiador de Vidrios y Espejos',NULL,6,18.00,40,NULL,'2024-12-24','60030',NULL,'Windex','500 ml'),(2310,'Limpiador de Superficies de Acero Inoxidable',NULL,6,22.00,45,NULL,'2024-11-23','60031',NULL,'Weiman','500 ml'),(2311,'Desinfectante de Juguetes',NULL,6,12.00,30,NULL,'2024-12-15','60032',NULL,'Clorox','250 ml'),(2312,'Limpia Alfombras',NULL,6,20.00,35,NULL,'2025-02-11','60033',NULL,'Resolve','500 ml'),(2313,'Removedor de Manchas de Tela',NULL,6,18.00,40,NULL,'2024-06-21','60034',NULL,'Shout','500 ml'),(2314,'Desinfectante de Zapatos',NULL,6,15.00,45,NULL,'2025-01-11','60035',NULL,'Lysol','250 ml'),(2315,'Limpiador de Azulejos',NULL,6,20.00,50,NULL,'2025-04-27','60036',NULL,'Tilex','500 ml'),(2316,'Limpiador de Acero Inoxidable',NULL,6,25.00,55,NULL,'2025-01-03','60037',NULL,'Bar Keepers Friend','500 ml'),(2317,'Limpiador de Pisos Laminados',NULL,6,18.00,40,NULL,'2024-09-23','60038',NULL,'Bona','750 ml'),(2318,'Limpiador de Alfombras',NULL,6,20.00,45,NULL,'2024-08-23','60039',NULL,'Bissell','500 ml'),(2319,'Removedor de Olores para Mascotas',NULL,6,15.00,30,NULL,'2024-06-14','60040',NULL,'Nature\'s Miracle','500 ml'),(2320,'Limpiador de Desagües',NULL,6,22.00,35,NULL,'2025-02-22','60041',NULL,'Drano','500 ml'),(2321,'Blanqueador de Juntas',NULL,6,18.00,40,NULL,'2025-04-09','60042',NULL,'Clorox','500 ml'),(2322,'Limpiador de Hornos',NULL,6,25.00,45,NULL,'2024-07-05','60043',NULL,'Easy-Off','500 ml'),(2323,'Desinfectante de Alfombras',NULL,6,20.00,50,NULL,'2024-09-07','60044',NULL,'Lysol','500 ml'),(2324,'Limpiador de Persianas',NULL,6,15.00,55,NULL,'2024-05-21','60045',NULL,'Swiffer','500 ml'),(2325,'Limpiador de Cuero',NULL,6,25.00,30,NULL,'2025-02-17','60046',NULL,'Lexol','500 ml'),(2326,'Removedor de Olores para Ropa',NULL,6,18.00,35,NULL,'2025-01-25','60047',NULL,'Febreze','500 ml'),(2327,'Limpiador de Computadoras',NULL,6,22.00,40,NULL,'2025-03-01','60048',NULL,'Falcon','500 ml'),(2328,'Limpiador de Zapatos',NULL,6,20.00,45,NULL,'2025-05-08','60049',NULL,'Kiwi','250 ml'),(2329,'Limpiador de Paredes',NULL,6,25.00,50,NULL,'2025-01-12','60050',NULL,'Magic Eraser','500 ml'),(2330,'Papas Fritas Clásicas',NULL,7,25.00,50,NULL,'2025-05-01','70001',NULL,'Lay\'s','Bolsa de 200g'),(2331,'Doritos Nacho',NULL,7,28.00,45,NULL,'2025-01-05','70002',NULL,'Doritos','Bolsa de 220g'),(2332,'Cheetos Crunchy',NULL,7,30.00,40,NULL,'2025-04-25','70003',NULL,'Cheetos','Bolsa de 250g'),(2333,'Papas Fritas Sabor Jalapeño',NULL,7,22.00,35,NULL,'2024-11-19','70004',NULL,'Sabritas','Bolsa de 180g'),(2334,'Fritos',NULL,7,24.00,30,NULL,'2024-11-02','70005',NULL,'Fritos','Bolsa de 200g'),(2335,'Papas Fritas Onduladas',NULL,7,27.00,35,NULL,'2024-09-17','70006',NULL,'Ruffles','Bolsa de 220g'),(2336,'Cacahuates Enchilados',NULL,7,35.00,40,NULL,'2024-10-15','70007',NULL,'Manzanita Sol','Bolsa de 300g'),(2337,'Tostitos Salsa Picante',NULL,7,30.00,45,NULL,'2025-03-14','70008',NULL,'Tostitos','Bolsa de 250g'),(2338,'Papas Fritas de Camote',NULL,7,32.00,50,NULL,'2024-11-16','70009',NULL,'Terra','Bolsa de 250g'),(2339,'Doritos Flamin\' Hot',NULL,7,29.00,40,NULL,'2025-05-17','70010',NULL,'Doritos','Bolsa de 240g'),(2340,'Cheetos Puffs',NULL,7,33.00,35,NULL,'2025-03-03','70011',NULL,'Cheetos','Bolsa de 280g'),(2341,'Papas Fritas con Sabor a Barbacoa',NULL,7,28.00,45,NULL,'2024-06-16','70012',NULL,'Lay\'s','Bolsa de 220g'),(2342,'Doritos Cool Ranch',NULL,7,27.00,30,NULL,'2024-07-30','70013',NULL,'Doritos','Bolsa de 230g'),(2343,'Cacahuates Salados',NULL,7,35.00,35,NULL,'2025-04-21','70014',NULL,'Manzanita Sol','Bolsa de 300g'),(2344,'Papas Fritas de Calabaza',NULL,7,25.00,40,NULL,'2024-08-23','70015',NULL,'Terra','Bolsa de 200g'),(2345,'Takis Fuego',NULL,7,29.00,45,NULL,'2024-10-15','70016',NULL,'Barcel','Bolsa de 240g'),(2346,'Cheetos Mix',NULL,7,32.00,50,NULL,'2024-12-03','70017',NULL,'Cheetos','Bolsa de 300g'),(2347,'Papas Fritas Sabor Limón',NULL,7,28.00,40,NULL,'2025-02-14','70018',NULL,'Lay\'s','Bolsa de 220g'),(2348,'Doritos Salsa Verde',NULL,7,27.00,35,NULL,'2024-11-06','70019',NULL,'Doritos','Bolsa de 230g'),(2349,'Cacahuates Japoneses',NULL,7,33.00,30,NULL,'2024-09-27','70020',NULL,'Manzanita Sol','Bolsa de 280g'),(2350,'Papas Fritas de Plátano',NULL,7,32.00,35,NULL,'2025-01-11','70021',NULL,'Terra','Bolsa de 250g'),(2351,'Tostitos Queso',NULL,7,31.00,40,NULL,'2024-12-16','70022',NULL,'Tostitos','Bolsa de 260g'),(2352,'Cheetos Pelotazos',NULL,7,33.00,45,NULL,'2025-01-03','70023',NULL,'Cheetos','Bolsa de 280g'),(2353,'Papas Fritas Sabor a Queso',NULL,7,30.00,50,NULL,'2025-02-09','70024',NULL,'Lay\'s','Bolsa de 240g'),(2354,'Doritos Dinamita',NULL,7,29.00,40,NULL,'2025-02-24','70025',NULL,'Doritos','Bolsa de 250g'),(2355,'Cacahuates con Chile y Limón',NULL,7,35.00,35,NULL,'2025-02-21','70026',NULL,'Manzanita Sol','Bolsa de 300g'),(2356,'Papas Fritas de Remolacha',NULL,7,28.00,30,NULL,'2025-03-30','70027',NULL,'Terra','Bolsa de 220g'),(2357,'Takis Nitro',NULL,7,30.00,35,NULL,'2025-02-11','70028',NULL,'Barcel','Bolsa de 240g'),(2358,'Cheetos Bolitas',NULL,7,33.00,40,NULL,'2024-11-24','70029',NULL,'Cheetos','Bolsa de 280g'),(2359,'Papas Fritas Sabor a Chile',NULL,7,30.00,45,NULL,'2025-04-20','70030',NULL,'Lay\'s','Bolsa de 240g'),(2360,'Doritos Pizzerolas',NULL,7,28.00,30,NULL,'2024-12-29','70031',NULL,'Doritos','Bolsa de 230g'),(2361,'Cacahuates con Chile',NULL,7,33.00,35,NULL,'2025-04-27','70032',NULL,'Manzanita Sol','Bolsa de 280g'),(2362,'Papas Fritas de Yuca',NULL,7,32.00,40,NULL,'2024-06-09','70033',NULL,'Terra','Bolsa de 250g'),(2363,'Tostitos Salsa de Queso',NULL,7,31.00,45,NULL,'2025-01-12','70034',NULL,'Tostitos','Bolsa de 260g'),(2364,'Cheetos Torciditos',NULL,7,32.00,50,NULL,'2024-10-27','70035',NULL,'Cheetos','Bolsa de 300g'),(2365,'Papas Fritas con Sabor a Queso y Chile',NULL,7,31.00,55,NULL,'2024-06-15','70036',NULL,'Lay\'s','Bolsa de 260g'),(2366,'Takis Fuego Extra',NULL,7,30.00,60,NULL,'2024-08-07','70037',NULL,'Barcel','Bolsa de 250g'),(2367,'Doritos Salsa Brava',NULL,7,28.00,65,NULL,'2025-02-28','70038',NULL,'Doritos','Bolsa de 230g'),(2368,'Cacahuates con Chile y Limón y Sal',NULL,7,33.00,70,NULL,'2024-10-18','70039',NULL,'Manzanita Sol','Bolsa de 280g'),(2369,'Papas Fritas de Remolacha y Zanahoria',NULL,7,32.00,75,NULL,'2025-03-01','70040',NULL,'Terra','Bolsa de 250g'),(2370,'Tostitos Salsa de Queso Extra',NULL,7,31.00,80,NULL,'2025-04-16','70041',NULL,'Tostitos','Bolsa de 260g'),(2371,'Cheetos Torciditos Flamin\' Hot',NULL,7,32.00,85,NULL,'2024-11-15','70042',NULL,'Cheetos','Bolsa de 300g'),(2372,'Doritos Dinamita Salsa Brava',NULL,7,29.00,90,NULL,'2024-08-08','70043',NULL,'Doritos','Bolsa de 250g'),(2373,'Cacahuates con Chile Habanero',NULL,7,33.00,95,NULL,'2024-06-30','70044',NULL,'Manzanita Sol','Bolsa de 280g'),(2374,'Papas Fritas con Sabor a Queso y Chile Picante',NULL,7,31.00,100,NULL,'2025-04-01','70045',NULL,'Lay\'s','Bolsa de 260g'),(2375,'Takis Fuego Extreme',NULL,7,30.00,105,NULL,'2024-10-19','70046',NULL,'Barcel','Bolsa de 250g'),(2376,'Doritos Salsa Brava con Limón',NULL,7,28.00,110,NULL,'2024-11-12','70047',NULL,'Doritos','Bolsa de 230g'),(2377,'Cacahuates con Chile y Limón y Sal Picante',NULL,7,33.00,115,NULL,'2025-01-07','70048',NULL,'Manzanita Sol','Bolsa de 280g'),(2378,'Papas Fritas de Remolacha y Zanahoria Extra',NULL,7,32.00,120,NULL,'2025-04-23','70049',NULL,'Terra','Bolsa de 250g'),(2379,'Tostitos Salsa de Queso Extra Picante',NULL,7,31.00,125,NULL,'2025-03-24','70050',NULL,'Tostitos','Bolsa de 260g'),(2380,'Shampoo Anti-Caspa',NULL,8,60.00,60,NULL,'2025-02-27','80001',NULL,'Head & Shoulders','Botella de 400ml'),(2381,'Acondicionador Reparador',NULL,8,65.00,55,NULL,'2024-08-01','80002',NULL,'Pantene','Botella de 350ml'),(2382,'Jabón de Tocador',NULL,8,55.00,70,NULL,'2024-11-14','80003',NULL,'Dove','Paquete de 4 unidades'),(2383,'Desodorante Roll-On',NULL,8,75.00,65,NULL,'2024-05-27','80004',NULL,'Rexona','Frascos de 50ml'),(2384,'Crema Facial Hidratante',NULL,8,85.00,50,NULL,'2024-10-03','80005',NULL,'Neutrogena','Tubo de 75g'),(2385,'Cepillo de Dientes Eléctrico',NULL,8,165.00,40,NULL,'2024-12-08','80006',NULL,'Oral-B','Incluye 2 cabezales'),(2386,'Enjuague Bucal',NULL,8,75.00,55,NULL,'2025-02-03','80007',NULL,'Listerine','Botella de 500ml'),(2387,'Cera Capilar Mate',NULL,8,80.00,50,NULL,'2025-01-13','80008',NULL,'L\'Oréal','Frasco de 100g'),(2388,'Loción Astringente',NULL,8,70.00,45,NULL,'2025-01-25','80009',NULL,'Clean & Clear','Botella de 200ml'),(2389,'Gel de Baño Hidratante',NULL,8,60.00,60,NULL,'2024-08-03','80010',NULL,'Nivea','Botella de 500ml'),(2390,'Desodorante en Spray',NULL,8,70.00,70,NULL,'2025-03-11','80011',NULL,'Axe','Aerosol de 150ml'),(2391,'Mascarilla Capilar Nutritiva',NULL,8,55.00,50,NULL,'2024-12-30','80012',NULL,'Garnier Fructis','Sobre de 200ml'),(2392,'Toallas Desmaquillantes',NULL,8,65.00,65,NULL,'2024-11-24','80013',NULL,'Olay','Paquete de 30 unidades'),(2393,'Gel de Afeitar',NULL,8,70.00,60,NULL,'2024-11-27','80014',NULL,'Gillette','Botella de 200ml'),(2394,'Crema Hidratante Corporal',NULL,8,80.00,55,NULL,'2025-05-14','80015',NULL,'Aveeno','Botella de 400ml'),(2395,'Cepillo de Pelo',NULL,8,95.00,45,NULL,'2025-01-10','80016',NULL,'Tangle Teezer','Unidad'),(2396,'Delineador de Ojos',NULL,8,90.00,50,NULL,'2024-08-15','80017',NULL,'Maybelline','Lápiz de 1.5g'),(2397,'Perfume de Mujer',NULL,8,105.00,40,NULL,'2024-07-30','80018',NULL,'Calvin Klein','Frasco de 50ml'),(2398,'Perfume de Hombre',NULL,8,115.00,35,NULL,'2024-11-15','80019',NULL,'Hugo Boss','Frasco de 100ml'),(2399,'Esmalte de Uñas',NULL,8,80.00,60,NULL,'2025-02-10','80020',NULL,'Essie','Frasco de 13.5ml'),(2400,'Maquillaje Base',NULL,8,85.00,55,NULL,'2024-10-20','80021',NULL,'L\'Oréal Paris','Frasco de 30ml'),(2401,'Desodorante en Barra',NULL,8,65.00,70,NULL,'2025-05-05','80022',NULL,'Dove','Envase de 75g'),(2402,'Gel de Ducha Exfoliante',NULL,8,60.00,60,NULL,'2024-11-25','80023',NULL,'Nivea Men','Botella de 400ml'),(2403,'Máscara de Pestañas',NULL,8,95.00,50,NULL,'2025-04-23','80024',NULL,'Maybelline','Tubo de 9.5ml'),(2404,'Crema para Manos',NULL,8,75.00,65,NULL,'2025-05-12','80025',NULL,'Neutrogena','Tubo de 50ml'),(2405,'Bálsamo Labial',NULL,8,65.00,60,NULL,'2025-01-24','80026',NULL,'Burt\'s Bees','Tubo de 4.25g'),(2406,'Shampoo Voluminizador',NULL,8,75.00,55,NULL,'2024-09-21','80027',NULL,'TRESemmé','Botella de 500ml'),(2407,'Máscara Capilar Reparadora',NULL,8,70.00,50,NULL,'2025-03-10','80028',NULL,'Dove','Tubo de 200ml'),(2408,'Desodorante en Crema',NULL,8,65.00,60,NULL,'2024-11-23','80029',NULL,'Nivea','Frascos de 50g'),(2409,'Gel Limpiador Facial',NULL,8,80.00,65,NULL,'2025-04-26','80030',NULL,'Cetaphil','Botella de 200ml'),(2410,'Cera para Depilación',NULL,8,70.00,45,NULL,'2025-03-11','80031',NULL,'Veet','Frasco de 100ml'),(2411,'Loción Post-Afeitado',NULL,8,65.00,55,NULL,'2024-11-20','80032',NULL,'Nivea Men','Botella de 100ml'),(2412,'Toallas Íntimas',NULL,8,60.00,70,NULL,'2025-05-01','80033',NULL,'Always','Paquete de 30 unidades'),(2413,'Bálsamo para Barba',NULL,8,80.00,60,NULL,'2024-07-21','80034',NULL,'Beardbrand','Frasco de 50ml'),(2414,'Crema Antimanchas',NULL,8,95.00,40,NULL,'2024-11-07','80035',NULL,'La Roche-Posay','Tubo de 30ml'),(2415,'Gel de Baño Energizante',NULL,8,70.00,55,NULL,'2024-10-29','80036',NULL,'Yves Rocher','Botella de 400ml'),(2416,'Desodorante Antitranspirante',NULL,8,80.00,50,NULL,'2024-06-26','80037',NULL,'Mitchum','Frascos de 150ml'),(2417,'Crema Antiedad',NULL,8,110.00,45,NULL,'2025-02-17','80038',NULL,'Olay','Tubo de 50ml'),(2418,'Afeitadora Desechable',NULL,8,75.00,60,NULL,'2025-03-08','80039',NULL,'Gillette Venus','Paquete de 5 unidades'),(2419,'Colonia Infantil',NULL,8,50.00,70,NULL,'2024-12-14','80040',NULL,'Johnson\'s Baby','Frasco de 100ml'),(2420,'Gel Fijador para Cabello',NULL,8,65.00,55,NULL,'2025-01-28','80041',NULL,'Garnier Fructis','Frasco de 200ml'),(2421,'Cepillo de Dientes',NULL,8,25.00,60,NULL,'2024-12-05','80042',NULL,'Colgate','Unidad'),(2422,'Tónico Facial',NULL,8,70.00,45,NULL,'2025-01-09','80043',NULL,'Thayers','Botella de 355ml'),(2423,'Acondicionador Sin Enjuague',NULL,8,60.00,50,NULL,'2025-01-05','80044',NULL,'Herbal Essences','Botella de 250ml'),(2424,'Crema para Pies',NULL,8,75.00,55,NULL,'2024-12-02','80045',NULL,'Scholl','Tubo de 100ml'),(2425,'Gel de Baño Relajante',NULL,8,70.00,60,NULL,'2024-08-20','80046',NULL,'Dove','Botella de 500ml'),(2426,'Desodorante en Spray Fresco',NULL,8,70.00,65,NULL,'2024-06-17','80047',NULL,'Speed Stick','Aerosol de 200ml'),(2427,'Crema Blanqueadora Dental',NULL,8,90.00,50,NULL,'2024-11-23','80048',NULL,'Crest','Tubo de 75ml'),(2428,'Mascarilla Facial Hidratante',NULL,8,100.00,40,NULL,'2025-03-15','80049',NULL,'Glamglow','Tubo de 50g'),(2429,'Aceite para Masajes',NULL,8,65.00,55,NULL,'2024-10-11','80050',NULL,'Vaseline','Botella de 200ml'),(2430,'Pizza de Pepperoni',NULL,9,70.00,40,NULL,'2025-05-06','90001',NULL,'DiGiorno','Caja de 400g'),(2431,'Hamburguesas de Res',NULL,9,100.00,35,NULL,'2025-02-06','90002',NULL,'BUBBA','Paquete de 10 unidades'),(2432,'Papas Fritas Congeladas',NULL,9,45.00,50,NULL,'2024-06-12','90003',NULL,'McCain','Bolsa de 1kg'),(2433,'Filetes de Pescado',NULL,9,80.00,30,NULL,'2025-01-26','90004',NULL,'Gorton\'s','Bolsa de 500g'),(2434,'Helado de Vainilla',NULL,9,60.00,25,NULL,'2024-11-20','90005',NULL,'Ben & Jerry\'s','Envase de 500ml'),(2435,'Vegetales Mixtos',NULL,9,50.00,45,NULL,'2024-05-21','90006',NULL,'Birds Eye','Bolsa de 800g'),(2436,'Nuggets de Pollo',NULL,9,70.00,40,NULL,'2024-12-14','90007',NULL,'Tyson','Bolsa de 750g'),(2437,'Pan de Ajo Congelado',NULL,9,40.00,55,NULL,'2025-01-01','90008',NULL,'New York Bakery Co.','Bolsa de 400g'),(2438,'Empanadas de Carne',NULL,9,90.00,60,NULL,'2024-12-07','90009',NULL,'Tasty Bite','Paquete de 6 unidades'),(2439,'Helado de Chocolate',NULL,9,65.00,30,NULL,'2025-04-04','90010',NULL,'Haagen-Dazs','Envase de 500ml'),(2440,'Tortellini de Queso',NULL,9,75.00,50,NULL,'2024-09-24','90011',NULL,'Buitoni','Bolsa de 400g'),(2441,'Papas a la Francesa',NULL,9,55.00,40,NULL,'2024-05-25','90012',NULL,'Ore-Ida','Bolsa de 900g'),(2442,'Pollo Empanizado',NULL,9,85.00,35,NULL,'2025-01-20','90013',NULL,'Perdue','Bolsa de 800g'),(2443,'Pasta Alfredo con Pollo',NULL,9,60.00,40,NULL,'2024-08-12','90014',NULL,'Lean Cuisine','Bolsa de 350g'),(2444,'Ensalada de Frutas Congeladas',NULL,9,50.00,45,NULL,'2024-10-12','90015',NULL,'Dole','Bolsa de 600g'),(2445,'Hamburguesas Vegetarianas',NULL,9,80.00,30,NULL,'2024-09-15','90016',NULL,'MorningStar Farms','Paquete de 8 unidades'),(2446,'Pizza de Queso',NULL,9,70.00,35,NULL,'2025-02-05','90017',NULL,'Red Baron','Caja de 400g'),(2447,'Camarones Cocidos',NULL,9,90.00,25,NULL,'2024-10-07','90018',NULL,'SeaPak','Bolsa de 500g'),(2448,'Helado de Fresa',NULL,9,55.00,30,NULL,'2025-05-17','90019',NULL,'Breyers','Envase de 500ml'),(2449,'Espinacas Picadas',NULL,9,45.00,40,NULL,'2024-10-14','90020',NULL,'Green Giant','Bolsa de 300g'),(2450,'Pechugas de Pollo',NULL,9,70.00,30,NULL,'2024-06-29','90021',NULL,'Foster Farms','Bolsa de 900g'),(2451,'Palitos de Mozzarella',NULL,9,60.00,35,NULL,'2024-11-25','90022',NULL,'Farm Rich','Bolsa de 400g'),(2452,'Ravioles de Carne',NULL,9,80.00,50,NULL,'2025-04-12','90023',NULL,'Celentano','Bolsa de 400g'),(2453,'Papas a la Gajo',NULL,9,50.00,45,NULL,'2024-08-10','90024',NULL,'Alexia','Bolsa de 800g'),(2454,'Pollo a la Parrilla',NULL,9,75.00,40,NULL,'2024-08-31','90025',NULL,'Perdue','Bolsa de 900g'),(2455,'Lasagna de Carne',NULL,9,85.00,30,NULL,'2025-01-21','90026',NULL,'Stouffer\'s','Bolsa de 400g'),(2456,'Helado de Cookies & Cream',NULL,9,60.00,25,NULL,'2024-05-24','90027',NULL,'Blue Bell','Envase de 500ml'),(2457,'Brócoli Congelado',NULL,9,40.00,50,NULL,'2024-12-16','90028',NULL,'Birds Eye','Bolsa de 500g'),(2458,'Empanadas de Pollo',NULL,9,80.00,55,NULL,'2025-05-05','90029',NULL,'Tasty Bite','Paquete de 6 unidades'),(2459,'Tarta de Manzana',NULL,9,70.00,40,NULL,'2025-05-18','90030',NULL,'Marie Callender\'s','Bolsa de 450g'),(2558,'producto',NULL,3,10.00,20,NULL,'2020-02-13','30021',NULL,'producto','15 gramos'),(2559,'Salchicha de pavo',NULL,1,47.80,20,NULL,'2020-02-15','10101',NULL,'FUD','8 salchichas');
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registroactividades`
--

DROP TABLE IF EXISTS `registroactividades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registroactividades` (
  `RegistroID` int NOT NULL AUTO_INCREMENT,
  `UsuarioID` int DEFAULT NULL,
  `Acción` varchar(255) DEFAULT NULL,
  `FechaAcción` datetime DEFAULT NULL,
  `Descripción` text,
  PRIMARY KEY (`RegistroID`),
  KEY `UsuarioID` (`UsuarioID`),
  CONSTRAINT `registroactividades_ibfk_1` FOREIGN KEY (`UsuarioID`) REFERENCES `usuario` (`UsuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registroactividades`
--

LOCK TABLES `registroactividades` WRITE;
/*!40000 ALTER TABLE `registroactividades` DISABLE KEYS */;
/*!40000 ALTER TABLE `registroactividades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transacciones_inventario`
--

DROP TABLE IF EXISTS `transacciones_inventario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transacciones_inventario` (
  `TransaccionID` int NOT NULL AUTO_INCREMENT,
  `ProductoID` int NOT NULL,
  `Fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Cantidad` int NOT NULL,
  `Tipo` varchar(10) DEFAULT NULL,
  `UsuarioID` int DEFAULT NULL,
  `Nota` text,
  PRIMARY KEY (`TransaccionID`),
  KEY `ProductoID` (`ProductoID`),
  KEY `UsuarioID` (`UsuarioID`),
  CONSTRAINT `transacciones_inventario_ibfk_1` FOREIGN KEY (`ProductoID`) REFERENCES `productos` (`ProductoID`),
  CONSTRAINT `transacciones_inventario_ibfk_2` FOREIGN KEY (`UsuarioID`) REFERENCES `usuario` (`UsuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transacciones_inventario`
--

LOCK TABLES `transacciones_inventario` WRITE;
/*!40000 ALTER TABLE `transacciones_inventario` DISABLE KEYS */;
/*!40000 ALTER TABLE `transacciones_inventario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `UsuarioID` int NOT NULL AUTO_INCREMENT,
  `NombreUsuario` varchar(255) DEFAULT NULL,
  `Contraseña` varchar(255) DEFAULT NULL,
  `Rol` varchar(50) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `NombreCompleto` varchar(255) DEFAULT NULL,
  `UltimoLogin` datetime DEFAULT NULL,
  `ContraseñaToken` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UsuarioID`),
  UNIQUE KEY `NombreUsuario` (`NombreUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (13,'Administrador','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','ADMINISTRADOR','brauliodamian80@gmail.com','Braulio Damian Gonzalez','2024-05-19 11:06:47',NULL),(17,'prueba','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','ADMINISTRADOR','damianlunaantonio@gmail.com','Prueba damian','2024-05-29 21:37:04','15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225'),(18,'empleado','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','EMPLEADO','damianlunaantonio@gmail.com','Empleado prueba','2024-05-16 08:50:08',NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas` (
  `VentaID` int NOT NULL AUTO_INCREMENT,
  `UsuarioID` int DEFAULT NULL,
  `FechaVenta` datetime DEFAULT NULL,
  `TotalVenta` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`VentaID`),
  KEY `UsuarioID` (`UsuarioID`),
  CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`UsuarioID`) REFERENCES `usuario` (`UsuarioID`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` VALUES (1,13,'2024-05-14 12:03:43',47.80),(2,13,'2024-05-14 12:25:59',143.40),(3,17,'2024-05-14 14:46:25',143.40),(4,17,'2024-05-16 08:35:43',62.14),(5,17,'2024-05-20 21:19:44',155.12),(6,17,'2024-05-20 23:32:37',196.73),(7,17,'2024-05-20 23:43:48',260.90),(8,17,'2024-05-20 23:45:34',204.25),(9,17,'2024-05-20 23:47:15',398.61),(10,17,'2024-05-21 21:19:14',196.83),(11,17,'2024-05-21 21:45:21',105.88),(12,17,'2024-05-21 21:46:41',401.08),(13,17,'2024-05-21 22:58:22',105.88),(14,17,'2024-05-22 08:03:37',204.25),(15,17,'2024-05-22 08:48:16',275.83),(16,17,'2024-05-23 11:00:36',386.35),(17,17,'2024-05-23 11:12:38',90.95),(18,17,'2024-05-23 11:20:15',147.60),(19,17,'2024-05-29 14:40:42',147.60),(20,17,'2024-05-29 18:17:34',56.65),(21,17,'2024-05-29 19:31:33',49.23),(22,17,'2024-05-29 19:41:23',49.23),(23,17,'2024-05-29 19:41:31',83.43),(24,17,'2024-05-29 20:11:56',49.23),(25,17,'2024-05-29 20:19:13',49.23),(26,17,'2024-05-29 20:19:32',56.65),(27,17,'2024-05-29 20:20:30',49.23),(28,17,'2024-05-29 20:20:41',56.65),(29,17,'2024-05-29 20:26:27',56.65),(30,17,'2024-05-29 20:29:45',49.23),(31,17,'2024-05-29 20:32:06',49.23),(32,17,'2024-05-29 20:32:13',41.72),(33,17,'2024-05-29 20:32:17',41.72),(34,17,'2024-05-29 21:13:17',41.72),(35,17,'2024-05-29 21:15:57',56.65),(36,17,'2024-05-29 21:16:05',56.65),(37,17,'2024-05-29 21:19:30',56.65),(38,17,'2024-05-29 21:19:34',56.65),(39,17,'2024-05-29 21:19:38',56.65),(40,17,'2024-05-29 21:19:42',98.37),(41,17,'2024-05-29 21:20:17',98.37),(42,17,'2024-05-29 21:23:08',56.65),(43,17,'2024-05-29 21:23:11',56.65),(44,17,'2024-05-29 21:23:14',56.65),(45,17,'2024-05-29 21:24:48',49.23),(46,17,'2024-05-29 21:32:06',41.72),(47,17,'2024-05-29 21:32:57',41.72),(48,17,'2024-05-29 21:33:41',49.23),(49,17,'2024-05-29 21:33:43',49.23),(50,17,'2024-05-29 21:37:09',41.72),(51,17,'2024-05-29 21:49:10',49.23);
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'dbtienda'
--

--
-- Dumping routines for database 'dbtienda'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-29 21:52:00
