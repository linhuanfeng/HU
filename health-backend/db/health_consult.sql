-- MySQL dump 10.13  Distrib 5.5.53, for Win32 (AMD64)
--
-- Host: 192.168.42.102    Database: health_consult
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `consult_ask`
--

DROP TABLE IF EXISTS `consult_ask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consult_ask` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '咨询id',
  `from_member_id` bigint DEFAULT NULL COMMENT '提问者id',
  `to_member_id` bigint DEFAULT NULL COMMENT '专家id',
  `content` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '提问的内容',
  `file` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '附件图片的url，以;分割',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consult_ask`
--

LOCK TABLES `consult_ask` WRITE;
/*!40000 ALTER TABLE `consult_ask` DISABLE KEYS */;
/*!40000 ALTER TABLE `consult_ask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consult_head`
--

DROP TABLE IF EXISTS `consult_head`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consult_head` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '咨询id',
  `head` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '咨询标题',
  `short_content` varchar(56) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '摘要',
  `content` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '内容',
  `img` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '封面图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consult_head`
--

LOCK TABLES `consult_head` WRITE;
/*!40000 ALTER TABLE `consult_head` DISABLE KEYS */;
/*!40000 ALTER TABLE `consult_head` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-13 20:14:25
