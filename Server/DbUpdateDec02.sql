--
-- change price type of menu
--
ALTER TABLE `menu` CHANGE `price` `price` DECIMAL(6,2) NULL DEFAULT '0.99';

--
-- add role Type to UserTable
-- 0-admin ,1 -vender Manager, 2- user, 3- staff, 4-others
--

DROP TABLE IF EXISTS `rest`;
CREATE TABLE `rest` (
`idrest` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `pic` varchar(450) DEFAULT NULL,
  `des` varchar(450) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rest`
--

INSERT INTO `rest` (`idrest`, `name`, `pic`, `des`) VALUES
(1, 'Tim Hortons', 'https://33.media.tumblr.com/avatar_cc74f394a4f7_128.png', 'Tim Hortons Inc. is a Canadian multinational fast casual restaurant known for its coffee and doughnuts.'),
(2, 'Subway', 'http://image.cdn.ispot.tv/brand/A6N/subway.png', 'Subway is an American fast food restaurant franchise that primarily sells submarine sandwiches and salads. It is owned and operated by Doctor''s Associates, Inc.');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `rest`
--
ALTER TABLE `rest`
 ADD PRIMARY KEY (`idrest`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `rest`
--
ALTER TABLE `rest`
MODIFY `idrest` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;




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
INSERT INTO `operator` (`name`, `empId`, `idrest`, `isManager`, `pwd`) VALUES ('manager', '0000011', 1, true, MD5('1234'));
INSERT INTO `operator` (`name`, `empId`, `idrest`, `isManager`, `pwd`) VALUES ('op2', '0000002', 2, false, MD5('1234'));
INSERT INTO `operator` (`name`, `empId`, `idrest`, `isManager`, `pwd`) VALUES ('man2', '0000022', 2, true, MD5('1234'));


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
INSERT INTO `user` VALUES (1,'alex',MD5('1234'),10000,'12@12.com','416833','2014-10-04 13:57:42'),(2,'customer',MD5('1234'),100000,'34@cencol.com','1234567890','2014-10-05 17:48:51'),(3,'consumer',MD5('1234'),100000,'56@cencol.com','345678901','2014-10-06 17:48:51'),(4,'test',MD5('1234'),100000,'78@cencol.com','789013456','2014-11-07 17:48:51'),(5,'victor',MD5('1234'),10000,'victor@victor.com','6472350909','2014-11-14 17:48:51'),(6,'angela',MD5('1234'),10000,'angela@angela.com','7891230456','2014-11-19 19:54:45'),(7,'kathleen',MD5('1234'),10000,'kathleen@cencol.ca','5671230987','2014-11-21 19:54:45'),(8,'estifanos',MD5('1234'),1000,'estifanos@cencol.com','1230981234','2014-11-28 00:35:21');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;