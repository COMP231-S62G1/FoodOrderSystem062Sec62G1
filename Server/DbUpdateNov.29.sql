--
-- change price type of menu
--
ALTER TABLE `menu` CHANGE `price` `price` DECIMAL(6,2) NULL DEFAULT '0.99';

--
-- add role Type to UserTable
-- 0-admin ,1 -vender Manager, 2- user, 3- staff, 4-others
--


DROP TABLE IF EXISTS `operator`;
CREATE TABLE `operator` (
    `idOper` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(45) default null,
    `empId` int(11) NOT default null,
    `idrest` int(11) NOT default null,
    `isManager` tinyint(4) DEFAULT false,
    `pwd` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`idOper`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

INSERT INTO `operator` (`name`, `empId`, `idrest`, `isManager`, `pwd`) VALUES ('operator', '0000001', 1, false, MD5('1234'));
INSERT INTO `operator` (`name`, `empId`, `idrest`, `isManager`, `pwd`) VALUES ('manager', '0000011', 1, false, MD5('1234'));


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
