CREATE DATABASE  IF NOT EXISTS `foodorder` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `foodorder`;
-- MySQL dump 10.13  Distrib 5.6.13, for osx10.6 (i386)
--
-- Host: localhost    Database: foodorder
-- ------------------------------------------------------
-- Server version	5.5.38

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
-- Table structure for table `giftcard`
--

DROP TABLE IF EXISTS `giftcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `giftcard` (
  `idgiftcard` int(11) NOT NULL,
  `giftcode` varchar(45) DEFAULT NULL,
  `giftbalance` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `usedUserName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idgiftcard`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `giftcard`
--

LOCK TABLES `giftcard` WRITE;
/*!40000 ALTER TABLE `giftcard` DISABLE KEYS */;
INSERT INTO `giftcard` VALUES (0,'55','50',1,'hello');
/*!40000 ALTER TABLE `giftcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `menuid` int(11) NOT NULL AUTO_INCREMENT,
  `restid` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `pic` varchar(450) DEFAULT NULL,
  `des` varchar(450) DEFAULT NULL,
  `price` decimal(6,2) DEFAULT '0.99',
  PRIMARY KEY (`menuid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,1,'menu1','http://www4.timhortons.com/ca/images/general/tim-hortons-takeout-cup.jpg','coffee',0.99),(2,1,'guu','http://travel.openrice.com/UserPhoto/photo/0/1N/000BRG976B894A7371DDABl.jpg','guu food',1.49);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderline`
--

DROP TABLE IF EXISTS `orderline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderline` (
  `idorderline` int(11) NOT NULL AUTO_INCREMENT,
  `qty` int(11) DEFAULT NULL,
  `menuid` int(11) DEFAULT NULL,
  `des` varchar(450) DEFAULT NULL,
  `orderid` int(11) DEFAULT NULL,
  PRIMARY KEY (`idorderline`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderline`
--

LOCK TABLES `orderline` WRITE;
/*!40000 ALTER TABLE `orderline` DISABLE KEYS */;
INSERT INTO `orderline` VALUES (17,3,2,NULL,22),(18,1,3,NULL,22),(19,10,1,NULL,22),(20,32,3213,NULL,22),(21,3,321,NULL,22),(22,3,2,NULL,23),(23,1,3,NULL,23),(24,10,1,NULL,23),(25,3213,3213,NULL,23),(26,3,321,NULL,23),(27,32,32,NULL,23),(28,3,2,NULL,24),(29,1,3,NULL,24),(30,10,1,NULL,24),(31,3213,3213,NULL,24),(32,3,321,NULL,24),(33,32,32,NULL,24),(34,3,2,NULL,25),(35,1,3,NULL,25),(36,10,1,NULL,25),(37,3213,3213,NULL,25),(38,3,321,NULL,25),(39,32,32,NULL,25);
/*!40000 ALTER TABLE `orderline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `idorder` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `orderTime` datetime DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`idorder`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,2,'2014-10-04 14:29:32','alex',0),(2,2,'2014-10-04 14:29:34','alex',0),(3,2,'2014-10-04 14:29:35','alex',0),(4,2,'2014-10-04 14:32:49','alex',0),(5,2,'2014-10-04 17:48:51','alex',1),(6,2,'2014-10-04 19:45:26','alex',0),(7,2,'2014-10-04 19:45:26','',0),(8,2,'2014-10-05 20:42:14','alex',0),(9,2,'2014-10-05 20:43:08','alex',0),(10,2,'2014-10-05 20:46:01','alex',0),(11,2,'2014-10-05 20:48:53','alex',0),(12,2,'2014-10-05 20:49:19','alex',0),(13,2,'2014-10-05 21:02:03','alex',0),(14,2,'2014-10-05 21:02:03','',0),(15,2,'2014-10-05 21:02:35','alex',0),(16,2,'2014-10-05 21:04:29','alex',0),(17,2,'2014-10-05 21:05:02','alex',0),(18,2,'2014-10-05 21:05:16','alex',0),(19,2,'2014-10-05 21:06:48','alex',0),(20,2,'2014-10-05 21:07:24','alex',0),(21,2,'2014-10-05 21:07:25','',0),(22,2,'2014-10-05 21:08:14','alex',0),(23,2,'2014-10-05 21:09:59','alex',0),(24,2,'2014-10-05 21:10:00','',0),(25,2,'2014-10-05 21:12:06','alex',0),(26,2,'2014-10-08 01:26:03','alex',0),(27,1,'2014-10-24 03:33:12','Alex',0),(28,1,'2014-10-24 03:34:49','Alex',0),(29,1,'2014-10-24 03:41:48','Alex',0),(30,1,'2014-10-24 03:59:14','Alex',0),(31,1,'2014-10-24 03:59:26','Alex',0),(32,1,'2014-10-26 00:57:21','Alex',0),(33,1,'2014-10-26 03:22:34','Alex',0),(34,1,'2014-10-26 14:46:41','Alex',0),(35,1,'2014-10-26 14:48:06','Alex',0),(36,1,'2014-10-26 14:52:38','Alex',0),(37,1,'2014-10-26 15:17:41','Alex',0),(38,1,'2014-10-26 15:18:49','Alex',0),(39,1,'2014-10-26 15:18:50','Alex',0),(40,1,'2014-10-26 18:56:27','Alex',0),(41,1,'2014-10-26 18:56:28','Alex',0),(42,1,'2014-10-26 18:56:29','Alex',1),(43,1,'2014-10-27 01:00:02','Alex',0),(44,1,'2014-10-27 20:40:44','Alex',0),(45,1,'2014-10-29 20:17:32','Alex',0),(46,1,'2014-10-30 12:46:06','Alex',0),(47,1,'2014-11-03 20:03:50','Alex',0),(48,1,'2014-11-06 20:19:43','Alex',0),(49,1,'2014-11-07 17:05:59','Alex',0),(50,1,'2014-11-07 19:07:12','Alex',0),(51,1,'2014-11-07 19:10:13','Alex',0),(52,1,'2014-11-07 19:11:12','Alex',0),(53,1,'2014-11-07 19:16:41','Alex',0),(54,1,'2014-11-07 19:45:54','Alex',0),(55,1,'2014-11-07 19:47:33','Alex',0),(56,1,'2014-11-07 19:50:28','Alex',0),(57,1,'2014-11-07 20:05:45','Alex',0),(58,1,'2014-11-07 20:08:10','Alex',0),(59,1,'2014-11-07 20:13:52','Alex',0),(60,1,'2014-11-07 20:13:52','Alex',0),(61,1,'2014-11-07 20:17:04','Alex',0),(62,1,'2014-11-07 20:18:43','Alex',0),(63,1,'2014-11-07 20:29:54','Alex',0),(64,1,'2014-11-07 20:33:06','Alex',0),(65,1,'2014-11-07 20:33:07','Alex',0),(66,1,'2014-11-07 20:35:14','Alex',0),(67,1,'2014-11-07 23:23:54','Alex',0),(68,1,'2014-11-07 23:33:27','Alex',0),(69,1,'2014-11-07 23:37:23','Alex',0),(70,1,'2014-11-07 23:40:36','Alex',0),(71,1,'2014-11-09 01:54:27','Alex',0),(72,1,'2014-11-10 03:08:48','Alex',0);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rejection`
--

DROP TABLE IF EXISTS `rejection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rejection` (
  `idrejection` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) DEFAULT NULL,
  `rejectReason` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`idrejection`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rejection`
--

LOCK TABLES `rejection` WRITE;
/*!40000 ALTER TABLE `rejection` DISABLE KEYS */;
/*!40000 ALTER TABLE `rejection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rest`
--

DROP TABLE IF EXISTS `rest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest` (
  `idrest` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `pic` varchar(450) DEFAULT NULL,
  `des` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`idrest`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rest`
--

LOCK TABLES `rest` WRITE;
/*!40000 ALTER TABLE `rest` DISABLE KEYS */;
INSERT INTO `rest` VALUES (1,'pizza','http://www.seattleorganicrestaurants.com/vegan-whole-foods/images/Food-Guidelines.jpg','foooood'),(2,'fruit','http://kamloopsunited.ca/wp-content/uploads/2013/10/world-food-day-heart.jpg','fruit');
/*!40000 ALTER TABLE `rest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `pwd` varchar(45) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `lastlogin` datetime DEFAULT NULL,
  PRIMARY KEY (`userid`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'alex','96e79218965eb72c92a549dd5a330112',97,'12@12.com','416833','2014-10-04 13:57:42'),(5,'alex','96e79218965eb72c92a549dd5a330112',0,'12@12.com','416833','2014-10-04 17:48:51'),(6,'alex','96e79218965eb72c92a549dd5a330112',0,'12@12.com','416833','2014-10-04 19:54:45'),(7,'','d41d8cd98f00b204e9800998ecf8427e',0,'12@12.com','416833','2014-10-04 19:54:45'),(8,'alex','96e79218965eb72c92a549dd5a330112',0,'12@12.com','416833','2014-10-08 00:35:21');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-10 16:08:44
/*Tested on server side*/
