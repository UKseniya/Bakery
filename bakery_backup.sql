-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: localhost    Database: bakery
-- ------------------------------------------------------
-- Server version	5.7.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary table structure for view `income`
--

DROP TABLE IF EXISTS `income`;
/*!50001 DROP VIEW IF EXISTS `income`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `income` AS SELECT 
 1 AS `total_income`,
 1 AS `month`,
 1 AS `year`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `line_item`
--

DROP TABLE IF EXISTS `line_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `line_item` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `product_price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`item_id`),
  KEY `order_id_fk` (`order_id`),
  KEY `product_id_fk` (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `line_item`
--

LOCK TABLES `line_item` WRITE;
/*!40000 ALTER TABLE `line_item` DISABLE KEYS */;
INSERT INTO `line_item` VALUES 
(9,10,3,3000,2),
(10,11,6,4200,1),
(11,11,2,4000,1),
(47,42,5,2900,1),
(46,41,3,3000,1),
(45,40,10,2700,2),
(44,39,2,3000,1),
(16,13,6,4200,1),
(17,13,10,2100,2),
(18,14,3,3000,1),
(19,15,8,3700,1),
(20,16,9,3400,1),
(21,17,7,3200,1),
(22,18,9,3400,1),
(23,19,4,3000,1),
(24,19,3,3000,1),
(25,20,5,3500,1),
(26,21,5,3500,1),
(27,22,7,3200,1),
(28,23,7,3200,1),
(29,24,5,3500,1),
(30,25,7,3200,1),
(31,26,7,3200,1),
(50,44,7,3000,1),
(49,43,4,2900,1),
(43,38,10,3000,2),
(42,37,5,3500,1),
(48,42,10,2700,1),
(51,45,12,2100,1),
(52,46,2,3000,1),
(53,46,12,2100,1),
(54,47,6,2800,1),
(55,48,8,2750,1),
(56,49,9,3000,1),
(57,50,4,2900,1),
(58,51,5,2900,1),
(59,52,4,3000,1),
(60,53,8,2750,1),
(61,54,4,2900,1),
(62,55,7,3000,1),
(63,55,12,2100,1),
(64,56,11,2000,1),
(65,57,10,2700,1),
(66,58,6,2800,1),
(67,59,3,3000,1),
(68,60,7,3000,1),
(69,61,4,3000,1),
(86,71,7,3200,1),
(93,76,8,3000,1),
(75,65,5,3500,2),
(73,63,6,4200,1),
(92,75,3,3000,2),
(89,74,2,4000,1),
(88,73,13,1400,1),
(90,74,3,3000,1),
(91,74,9,3400,2),
(94,77,6,4200,1),
(95,77,9,3000,1),
(96,78,9,3000,1),
(97,79,12,2100,3),
(98,80,7,3200,2),
(99,81,11,2000,2),
(100,82,9,3400,1),
(101,82,5,3500,1),
(102,82,10,2100,1),
(103,82,13,1400,2),
(104,83,10,2100,1),
(105,83,11,2600,1),
(106,83,9,3400,1),
(107,84,5,3500,1),
(108,84,10,2100,1),
(109,84,11,2600,1),
(110,85,6,4200,1),
(111,86,2,4000,1),
(112,87,5,2900,1),
(113,88,3,3000,1),
(114,89,10,2700,2),
(115,89,2,3000,1),
(116,90,6,4200,1),
(117,91,10,2100,2),
(118,92,3,3000,1),
(119,93,8,3700,1),
(120,94,9,3400,1),
(121,95,7,3200,1),
(122,96,9,3400,1),
(123,97,4,3000,1),
(124,98,3,3000,1),
(125,99,5,3500,1),
(126,100,5,3500,1),
(127,101,7,3200,1),
(128,102,7,3200,1),
(129,103,5,3500,1),
(130,104,7,3200,1),
(131,105,7,3200,1),
(132,106,7,3000,1),
(133,107,4,2900,1),
(134,108,10,3000,2),
(135,109,5,3500,1),
(136,110,10,2700,1),
(137,111,12,2100,1),
(138,112,2,3000,1),
(139,113,12,2100,1),
(140,114,6,2800,1),
(141,115,8,2750,1),
(142,116,9,3000,1),
(143,117,4,2900,1),
(144,118,5,2900,1),
(145,119,4,3000,1),
(146,120,8,2750,1),
(147,121,4,2900,1),
(148,122,7,3000,1),
(149,123,12,2100,1),
(150,124,11,2000,1),
(151,125,10,2700,1),
(152,126,6,2800,1),
(153,127,3,3000,1),
(154,128,7,3000,1),
(155,129,4,3000,1),
(156,130,7,3200,1),
(157,131,8,3000,1),
(158,132,5,3500,2),
(159,133,6,4200,1),
(160,134,3,3000,2),
(161,135,2,4000,1),
(162,136,13,1400,1),
(163,137,3,3000,1),
(164,138,9,3400,2),
(165,139,6,4200,1),
(166,140,9,3000,1),
(167,141,9,3000,1),
(168,142,12,2100,3),
(169,143,7,3200,2),
(170,144,11,2000,2),
(171,145,9,3400,1),
(172,146,10,2100,1),
(173,147,13,1400,2),
(174,148,10,2100,1),
(175,149,11,2600,1),
(176,150,9,3400,1),
(177,151,5,3500,1),
(178,152,10,2100,1),
(179,153,11,2600,1),
(180,154,7,3200,1),
(181,155,7,3200,1),
(182,156,7,3000,1),
(183,157,4,2900,1),
(184,158,10,3000,2),
(185,159,5,3500,1),
(186,160,10,2700,1),
(187,161,12,2100,1),
(188,162,2,3000,1),
(189,163,12,2100,1),
(190,164,6,2800,1),
(191,165,8,2750,1),
(192,166,9,3000,1),
(193,167,4,2900,1),
(194,168,5,2900,1),
(195,169,4,3000,1),
(196,170,8,2750,1),
(197,171,4,2900,1),
(198,172,7,3000,1),
(199,173,12,2100,1),
(200,174,11,2000,1),
(201,175,10,2700,1),
(202,176,6,2800,1),
(203,177,3,3000,1),
(204,178,7,3000,1),
(205,179,4,3000,1),
(206,180,7,3200,1),
(207,181,8,3000,1),
(208,182,5,3500,2),
(209,183,6,4200,1),
(210,184,3,3000,2),
(211,185,2,4000,1),
(212,186,13,1400,1),
(213,187,3,3000,1),
(214,188,9,3400,2),
(215,189,6,4200,1),
(216,190,9,3000,1),
(217,191,9,3000,1),
(218,192,12,2100,3),
(219,193,7,3200,2),
(220,194,11,2000,2),
(221,195,9,3400,1),
(122,196,10,2100,1),
(223,197,13,1400,2),
(224,198,10,2100,1),
(225,199,11,2600,1),
(226,200,9,3400,1),
(227,201,5,3500,1),
(228,202,10,2100,1),
(229,203,11,2600,1),
(230,204,11,2000,1),
(231,205,10,2700,1),
(232,206,6,2800,1),
(233,207,3,3000,1),
(234,208,7,3000,1),
(235,209,4,3000,1),
(236,210,7,3200,1),
(237,211,8,3000,1),
(238,212,5,3500,2),
(239,213,6,4200,1),
(240,214,3,3000,2),
(241,215,2,4000,1),
(242,216,13,1400,1),
(243,217,3,3000,1),
(244,218,9,3400,2),
(245,219,6,4200,1),
(246,220,9,3000,1),
(247,221,9,3000,1),
(248,222,12,2100,3),
(249,223,7,3200,2),
(250,224,11,2000,2),
(251,225,9,3400,1),
(152,226,10,2100,1),
(253,227,13,1400,2),
(254,228,10,2100,1),
(255,229,11,2600,1),
(256,230,9,3400,1),
(257,223,5,3500,1),
(258,205,10,2100,1),
(259,230,11,2600,1);
/*!40000 ALTER TABLE `line_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locale`
--

DROP TABLE IF EXISTS `locale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `locale` (
  `locale_id` int(11) NOT NULL AUTO_INCREMENT,
  `locale_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`locale_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locale`
--

LOCK TABLES `locale` WRITE;
/*!40000 ALTER TABLE `locale` DISABLE KEYS */;
INSERT INTO `locale` VALUES (1,'ru'),(2,'en');
/*!40000 ALTER TABLE `locale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_status`
--

DROP TABLE IF EXISTS `order_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_status` (
  `status_id` int(11) NOT NULL AUTO_INCREMENT,
  `locale_id` int(11) NOT NULL,
  `status_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`status_id`,`locale_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_status`
--

LOCK TABLES `order_status` WRITE;
/*!40000 ALTER TABLE `order_status` DISABLE KEYS */;
INSERT INTO `order_status` VALUES (1,2,'in progress'),(2,2,'completed'),(3,2,'closed'),(1,1,'в работе'),(2,1,'выполнен'),(3,1,'закрыт');
/*!40000 ALTER TABLE `order_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordering`
--

DROP TABLE IF EXISTS `ordering`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordering` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `comment` text COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_number` (`order_number`),
  KEY `status` (`status`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=85 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordering`
--

LOCK TABLES `ordering` WRITE;
/*!40000 ALTER TABLE `ordering` DISABLE KEYS */;
INSERT INTO `ordering` VALUES 
(54,'000054',13,'2019-01-08','',3),
(53,'000053',10,'2019-01-21','',3),
(52,'000052',13,'2019-01-11','',3),
(51,'000051',10,'2019-01-20','',3),
(50,'000050',13,'2019-01-10','',3),
(49,'000049',10,'2019-01-03','',3),
(48,'000048',13,'2019-01-05','',3),
(47,'000047',5,'2019-01-18','',3),
(46,'000046',12,'2019-01-28','',3),
(10,'000008',12,'2019-03-28','',1),
(11,'000009',5,'2019-02-18','',3),
(45,'000045',5,'2019-01-28','',3),
(13,'000013',10,'2019-02-03','',3),
(14,'000014',13,'2019-02-10','',3),
(15,'000015',10,'2019-02-20','',3),
(16,'000016',13,'2019-03-28','',1),
(17,'000017',10,'2019-04-21','',1),
(18,'000018',13,'2019-03-28','',1),
(19,'000019',10,'2019-02-01','',3),
(20,'000020',2,'2019-02-08','',3),
(21,'000021',5,'2019-02-01','',3),
(22,'000023',2,'2019-02-14','',3),
(23,'000022',5,'2019-02-14','',3),
(24,'000024',2,'2019-02-18','',3),
(25,'000025',5,'2019-02-17','',3),
(26,'000026',12,'2019-02-17','',3),
(44,'000044',2,'2019-01-11','',3),
(41,'000041',12,'2019-01-13','',3),
(42,'000042',5,'2019-01-26','',3),
(43,'000043',2,'2019-01-11','',3),
(40,'000040',14,'2019-01-28','',3),
(39,'000039',12,'2019-01-09','',3),
(38,'000038',12,'2019-01-06','',3),
(37,'000037',13,'2019-03-15','',1),
(55,'000055',10,'2019-01-01','',3),
(56,'000056',2,'2019-01-08','',3),
(57,'000057',5,'2019-01-01','',3),
(58,'000058',2,'2019-01-14','',3),
(59,'000059',5,'2019-01-14','',3),
(60,'000060',2,'2019-01-18','',3),
(61,'000001',2,'2019-03-27','',1),
(65,'000002',2,'2019-03-26','',1),
(63,'000003',2,'2019-03-27','',1),
(75,'000061',13,'2019-03-19','',2),
(73,'000007',2,'2019-03-26','',1),
(74,'000005',2,'2019-03-19','',2),
(76,'000062',3,'2019-03-19','',2),
(77,'000063',10,'2019-03-26','',1),
(78,'000064',5,'2019-03-26','',1),
(79,'000065',11,'2019-03-26','',1),
(80,'000066',12,'2019-03-26','',1),
(81,'000067',14,'2019-03-26','',1),
(82,'000004',2,'2019-03-27','',1),
(83,'000006',2,'2019-03-27','',1),
(84,'000010',2,'2019-03-28','',1),
(85,'000068',5,'2019-03-14','',3),
(86,'000069',7,'2019-03-17','',3),
(87,'000070',4,'2019-03-18','',3),
(88,'000071',8,'2019-03-14','',3),
(89,'000072',3,'2019-03-21','',1),
(90,'000073',9,'2019-03-23','',1),
(91,'000074',8,'2019-03-15','',3),
(92,'000075',3,'2019-03-22','',1),
(93,'000076',9,'2019-03-23','',1),
(94,'000077',10,'2019-03-26','',1),
(95,'000078',11,'2019-03-29','',1),
(96,'000079',12,'2019-03-27','',1),
(97,'000080',13,'2019-03-28','',1),
(98,'000081',14,'2019-03-30','',1),
(99,'000082',12,'2019-03-01','',3),
(100,'000083',13,'2019-03-01','',3),     
(101,'000084',8,'2019-03-05','',3),
(102,'000085',3,'2019-03-08','',3),
(103,'000086',2,'2019-03-09','',3),
(104,'000087',8,'2019-03-12','',3),
(105,'000088',3,'2019-03-11','',3),
(106,'000089',9,'2019-03-12','',3),
(107,'000090',10,'2019-03-13','',3),
(108,'000091',11,'2019-03-14','',3),
(109,'000092',7,'2019-03-15','',3),
(110,'000093',13,'2019-03-16','',3),
(111,'000094',4,'2019-03-20','',1),
(112,'000095',12,'2019-03-01','',3),
(113,'000096',13,'2019-03-02','',3),
(114,'000097',6,'2019-03-01','',3),
(115,'000098',8,'2019-03-05','',3),
(116,'000099',3,'2019-03-08','',3),
(117,'000100',2,'2019-03-02','',3),
(118,'000101',8,'2019-03-05','',3),
(119,'000102',3,'2019-03-06','',3),
(120,'000103',5,'2019-03-07','',3),
(121,'000104',10,'2019-03-08','',3),
(122,'000105',11,'2019-03-09','',3),
(123,'000106',12,'2019-03-12','',3),
(124,'000107',13,'2019-03-13','',3),
(125,'000108',14,'2019-03-12','',3),
(126,'000109',7,'2019-03-14','',3),
(127,'000110',13,'2019-03-02','',3),
(128,'000111',9,'2019-03-12','',3),
(129,'000112',10,'2019-03-13','',3),
(130,'000113',11,'2019-03-21','',1),
(131,'000114',10,'2019-03-20','',1),
(132,'000115',11,'2019-03-22','',1),
(133,'000116',2,'2019-03-23','',1),
(134,'000117',13,'2019-03-23','',1),
(135,'000118',14,'2019-03-29','',1),
(136,'000119',12,'2019-03-16','',3),
(137,'000120',3,'2019-03-02','',3),
(138,'000121',9,'2019-03-12','',3),
(139,'000122',10,'2019-03-13','',3),
(140,'000123',11,'2019-03-14','',3),
(151,'000124',2,'2019-04-05','',1),
(152,'000125',3,'2019-04-06','',1),
(153,'000126',4,'2019-04-09','',1),
(154,'000127',5,'2019-04-12','',1),
(155,'000128',6,'2019-04-11','',1),
(156,'000129',7,'2019-04-12','',1),
(157,'000130',8,'2019-04-13','',1),
(158,'000131',9,'2019-04-11','',1),
(159,'000132',10,'2019-04-17','',1),
(160,'000133',11,'2019-04-16','',1),
(161,'000134',12,'2019-04-20','',1),
(162,'000135',14,'2019-04-01','',1),
(163,'000136',13,'2019-04-02','',1),
(164,'000137',2,'2019-04-01','',1),
(165,'000138',3,'2019-04-05','',1),
(166,'000139',4,'2019-04-06','',1),
(167,'000140',5,'2019-04-02','',1),
(168,'000141',6,'2019-04-05','',1),
(169,'000142',7,'2019-04-06','',1),
(170,'000143',5,'2019-04-04','',1),
(171,'000144',10,'2019-04-03','',1),
(172,'000145',11,'2019-04-09','',1),
(173,'000146',12,'2019-04-12','',1),
(174,'000147',13,'2019-04-13','',1),
(175,'000148',14,'2019-04-12','',1),
(176,'000149',7,'2019-04-13','',1),
(177,'000150',13,'2019-04-02','',1),
(178,'000151',9,'2019-04-12','',1),
(179,'000152',10,'2019-04-13','',1),
(180,'000153',11,'2019-04-19','',1),
(181,'000154',10,'2019-04-20','',1),
(182,'000155',11,'2019-04-23','',1),
(183,'000156',2,'2019-04-19','',1),
(184,'000157',13,'2019-04-23','',1),
(185,'000158',14,'2019-04-19','',1),
(186,'000159',12,'2019-04-16','',1),
(187,'000160',3,'2019-04-02','',1),
(188,'000161',9,'2019-04-12','',1),
(189,'000162',10,'2019-04-13','',1),
(190,'000163',11,'2019-04-04','',1),
(191,'000164',8,'2019-04-05','',1),
(192,'000165',3,'2019-04-06','',1),
(193,'000166',2,'2019-04-09','',1),
(194,'000167',8,'2019-04-12','',1),
(195,'000168',3,'2019-04-11','',1),
(196,'000169',9,'2019-04-12','',1),
(197,'000170',10,'2019-04-13','',1),
(198,'000171',11,'2019-04-04','',1),
(199,'000172',7,'2019-04-05','',1),
(200,'000173',13,'2019-04-16','',1),
(201,'000174',4,'2019-04-20','',1),
(202,'000175',12,'2019-04-01','',1),
(203,'000176',13,'2019-04-02','',1),
(204,'000177',6,'2019-04-01','',1),
(205,'000178',8,'2019-04-05','',1),
(206,'000179',3,'2019-04-03','',1),
(207,'000180',2,'2019-04-02','',1),
(208,'000181',8,'2019-04-05','',1),
(209,'000182',3,'2019-04-06','',1),
(210,'000183',5,'2019-04-17','',1),
(211,'000184',10,'2019-04-18','',1),
(212,'000185',11,'2019-04-09','',1),
(213,'000186',12,'2019-04-12','',1),
(214,'000187',13,'2019-04-13','',1),
(215,'000188',14,'2019-04-12','',1),
(216,'000189',7,'2019-04-16','',1),
(217,'000190',13,'2019-04-02','',1),
(218,'000191',9,'2019-04-12','',1),
(219,'000192',10,'2019-04-13','',1),
(220,'000193',11,'2019-04-01','',1),
(221,'000194',10,'2019-04-20','',1),
(222,'000195',11,'2019-04-02','',1),
(223,'000196',2,'2019-04-23','',1),
(224,'000197',13,'2019-04-23','',1),
(225,'000198',14,'2019-04-30','',1),
(226,'000199',12,'2019-04-16','',1),
(227,'000200',3,'2019-04-02','',1),
(228,'000201',9,'2019-04-12','',1),
(229,'000202',10,'2019-04-13','',1),
(230,'000203',11,'2019-04-30','',1);
/*!40000 ALTER TABLE `ordering` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `price` double NOT NULL,
  `status` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`product_id`),
  KEY `status` (`status`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'BLF01',3500,'1'),(2,'REV01',4000,'1'),(3,'CHE01',3000,'1'),(4,'BFP01',3000,'1'),(5,'NAP01',3500,'1'),(6,'CAR01',4200,'1'),(7,'KIV01',3200,'1'),(8,'HON01',3700,'2'),(9,'PAV01',3400,'1'),(10,'MAC01',2100,'1'),(11,'BRO01',2600,'1'),(12,'ECL01',1600,'2'),(13,'CIN01',1400,'1');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_description`
--

DROP TABLE IF EXISTS `product_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_description` (
  `product_id` int(11) NOT NULL,
  `locale_id` int(11) NOT NULL,
  `product_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`product_id`,`locale_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_description`
--

LOCK TABLES `product_description` WRITE;
/*!40000 ALTER TABLE `product_description` DISABLE KEYS */;
INSERT INTO `product_description` VALUES (1,1,'\"Чёрный лес\"','Торт «Чёрный лес» - торт со взбитыми сливками и вишней. Он состоит из бисквитных шоколадных коржей. В качестве начинки используются взбитые сливки и вишня. Для украшения торта используются взбитые сливки, вишня и шоколадная стружка.'),(2,1,'\"Красный бархат\"','Это шоколадный торт ярко-красного цвета, который готовится как слоенный пирог с прослойкой из сливочного сыра.'),(3,1,'Чизкейк','Чизкейк это сладкий десерт, который состоит из двух слоев. Основная часть чизкейка, начинка — смесь мягкого сыра, сахара и сливок. Основание состоит из толчёного песочного печенья.'),(4,1,'Баноффи','Баноффи (англ. Banoffee pie) — английский пирог, приготовленный из бананов, сливок, карамели (из варёного сгущённого молока). Основа для баноффи готовится из измельчённого печенья с добавлением сливочного масла.'),(5,1,'«Наполеон»','«Наполеон» — слоёный торт, который готовится из слоеного теста с заварным кремом.'),(6,1,'Морковный торт','Морковный торт — торт, содержащий морковь, смешанную с тестом. Морковный торт включают в себя дополнительные ингредиенты, такие как орехи, изюм и кокосовую стружку. Глазурь для морковного торта делается из сливочного сыра (сахарная пудра, сливочное масло и сыр).'),(7,1,'Киевский торт','Торт состоит из двух воздушно-ореховых хрустящих коржей безе с прослойками заварного крема.'),(8,1,'Медовый торт','Основой торта служит мёд и сгущённое молоко. Торт состоит из слоев бисквитного теста с прослойками крема и посыпкой из орехов и биквитной крошки.'),(9,1,'Павлова','Павлова — торт-безе с хрустящей корочкой, который остается легким мягким внутри. Сверху торт украшается взбитыми сливками и свежими фруктами.'),(10,1,'Макарон (8 шт. в коробке)','Макарон — сладкое печенье на основе меренги, приготовленное из яичных белков, сахара и молотого миндаля. Для прослойки печенья используется ганаш, крем или варенье. '),(11,1,'Брауни (5 шт. в коробке)','Брауни (англ. Chocolate brownie) — прямоугольное шоколадное пирожное, которое грецкие орехи, шоколадные капли и другие ингредиенты.'),(12,1,'Эклер (4 шт. в коробке)','Эклер — французский десерт в виде продолговатого пирожка из заварного теста с заварным кремом, покрытый шоколадной глазурью.'),(1,2,'Black Forest Cake','Black Forest cake is a with whipped cream and a rich cherry filling. It consists of several layers of chocolate sponge cake sandwiched with whipped cream and cherries. It is decorated with additional whipped cream, maraschino cherries, and chocolate shavings.'),(2,2,'Red velvet Cake','It is a red colored chocolate layer cake, layered with white cream cheese.'),(3,2,'Cheesecake','Cheesecake is a sweet dessert consisting of two layers. The main, and thickest layer, consists of a mixture of soft, cream cheese, eggs, sugar and cream. The base is made from crushed shortbread biscuits.'),(4,2,'Banoffee Pie','Banoffee pie is an English dessert pie made from bananas, cream and toffee (made from boiled condensed milk), combined on a buttery biscuit base.'),(5,2,'Napoleon','Napoleon is a layer cake made of puff pastry and pastry cream (crème pâtissière).'),(6,2,'Carrot Cake','Carrot cake is a cake that contains carrots mixed into the batter. Additional ingredients include shredded coconut, raisins and nuts. Cream cheese is used as a topping.'),(7,2,'Kiev Cake','The cake consists of two airy crunchy layers of meringue with cashews and peanuts and custard.'),(8,2,'Honey Cake','The primary ingredients are honey and condensed milk. It consists of layers of sponge cake with a cream filling and is covered with nuts or crumbs.'),(9,2,'Pavlova','It is a meringue dessert with a crisp crust and soft, light inside, topped with fruit and whipped cream.'),(10,2,'Macarons (8 ea./box)','A macaron is a sweet meringue-based sandwich cookie made from egg white, sugar and ground almond and filled with ganache, buttercream or jam.'),(11,2,'Brownie (5 ea./box)','A chocolate brownie is a square, baked, chocolate dessert that includes walnuts, chocolate chips, and other ingredients.'),(12,2,'Eclair (4 ea./box)','An eclair is an oblong pastry made with choux dough filled with a custard and topped with chocolate icing.'),(23,2,'Cinnabon (4 ea.)','A sweetened roll with a thick cinnamon-sugar filling and a cream cheese frosting.'),(23,1,'Синнабон (4 шт.)','Сладкая булочка с корицей, покрытая глазурью из сливочного сыра.');
/*!40000 ALTER TABLE `product_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_status`
--

DROP TABLE IF EXISTS `product_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_status` (
  `status_id` int(11) NOT NULL AUTO_INCREMENT,
  `status_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_status`
--

LOCK TABLES `product_status` WRITE;
/*!40000 ALTER TABLE `product_status` DISABLE KEYS */;
INSERT INTO `product_status` VALUES (1,'available'),(2,'n/a');
/*!40000 ALTER TABLE `product_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `top_products`
--

DROP TABLE IF EXISTS `top_products`;
/*!50001 DROP VIEW IF EXISTS `top_products`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `top_products` AS SELECT 
 1 AS `month`,
 1 AS `year`,
 1 AS `product_id`,
 1 AS `quantity`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `login` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `role_id` int(50) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login` (`login`),
  KEY `role` (`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES 
(1,'Kseniya','Nazarova','kseniya','xBWw1ZZk51H1bmJwXdMoxK2vgg1G3xvi4970x+5s1Rc=w2W9BfbYJj0IrTGVElotKhDujswhJA','customer.support@test.com','77016076307',1),
(2,'Natalya','Ivanova','natalya','rpUujA6SrsDHfutcBg9F/gd0rvpe+pPP2s5JiKfiuZs=fhEhkoj0qK4yCU5poaaNQyp59ZU0Qq','natalya.ivanova@test.com','77017017171',2),
(3,'Александр','Пушкин','alex','ucNkP9biOHADv08Qw37IFbC2AbC/8W69LC58cZCH+34=tB4ppoSHgvgWuSwXyfZrkRHYcYjgmo','alex@test.com','77777077777',2),
(4,'Antonina','Senchenko','tonya','g7uH6o8u5ABcg9MalErm5u7JgMdcWPqVnhoIqsyqZkE=fOszFVkB8MBuwny6gBa4iuXTB0H7CR','antonina.senchenko@test.com','77016056565',2),
(5,'Vera','Ivanova','vera','YESM4q1LvZoBwls6itY7L5SchEuErT+4Hq3sswnP+nE=V4w6am3JNyMJ1bWoMomtKJPIDDXSnV','vera@test.com','77085425434',2),
(6,'Viktoriya','Strelkova','viktoriya.str','+OdErOfvxQxh7Q9NnT3RJzAQyBNislWevkp5IU55nwU=esLXWbUfNoHPtuqZQwcnPqkhT6eDrk','viktoriya.strelkova@test.com','77054326810',2),
(7,'Valery','Meladze','valery','hjOdDMWy9NWWUmiPf3M4O9Xv0tV3QQ0358+AkY45EYA=9cGsDDqYwZGFmjSYoAVzks6ETOzitN','valery@test.com','77770541487',2),
(8,'Antonina','Vernikova','antonina','iypZkSd1Zfb31fW9fTHYXmzOS26edgfh8TZubRwK2a0=u07D89WsBeD9sUm84j6Q0RI3GEFkq7','antonina.vernikova@test.com','87017777890',2),
(9,'Виктория','Верникова','viktoriya','Eaw7d7Yq/TF2rHlHPDAIJTVjRGiXKTdQOQFJoSmseLc=Sp3WkTb4pt4CZ3kFqC7CRF0dmOxswq','','87017777308',2),
(10, 'Barbara', 'Brown', 'barbara', 'Y7O7XdppSVSle7Emlz4niflvkBX/21guRmGNtwBURuc=u1fOv220qLs5qQcEHXmcIZ7VU6RWTA', 'barbara@test.com' '87017345308', 2),
(11, 'Svetlana', 'Petrova', 'svetlana', 'bZ7HgfFdEKXnGxaKWQAtGI/ZcYvNaHA7MToITeHMT8s=icXzASKVXU62nLNqHAsQPzSNLaa3g1', '', '87017745308', 2),
(12, 'Виктор', 'Ростов', 'viktor', 'yg/BrBsHDYFjAdDGkfTguB23NBK5uN+twJbgcSUV/+0=1QymoLqGHLzMzJ8AwsIsMCOEW4a93r', '', '87014385308', 2),
(13, 'Simon', 'Crown', 'simon', 'BrrQPSCTWMutXoz3Q01LGFinC6GEyMcd3xUNEBblQOY=zpKNIQHVL9IOVYMsWOGacCQl0Fb0kH', '', '87010971308', 2),
(14, 'Alexa', 'Davidson', 'alexa', 'eH67dmjZSjg2JcSN94Z//AY21z6gdenCe2cb1DKgJFs=0rP5rmIZKFqulhlImxHIO1RkPFimI2', '', '87017349264', 2);;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `income`
--

/*!50001 DROP VIEW IF EXISTS `income`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `income` AS select sum(`oi`.`order_total`) AS `total_income`,month(`o`.`date`) AS `month`,year(`o`.`date`) AS `year` from ((`bakery`.`ordering` `o` join (select `bakery`.`line_item`.`order_id` AS `order_id`,sum((`bakery`.`line_item`.`product_price` * `bakery`.`line_item`.`quantity`)) AS `order_total` from `bakery`.`line_item` group by `bakery`.`line_item`.`order_id`) `oi` on((`o`.`order_id` = `oi`.`order_id`))) join `bakery`.`order_status` `os` on((`o`.`status` = `os`.`status_id`))) where (`os`.`status_name` = 'closed') group by `year` desc,`month` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `top_products`
--

/*!50001 DROP VIEW IF EXISTS `top_products`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `top_products` AS select month(`o`.`date`) AS `month`,year(`o`.`date`) AS `year`,`li`.`product_id` AS `product_id`,sum(`li`.`quantity`) AS `quantity` from ((`ordering` `o` join `line_item` `li` on((`o`.`order_id` = `li`.`order_id`))) join `order_status` `os` on((`o`.`status` = `os`.`status_id`))) where (`os`.`status_name` = 'closed') group by `li`.`product_id`,`month`,`year` order by `year`,`month`,`li`.`product_id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-19 21:31:33
