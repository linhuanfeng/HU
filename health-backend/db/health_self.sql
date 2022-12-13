-- MySQL dump 10.13  Distrib 5.5.53, for Win32 (AMD64)
--
-- Host: 192.168.42.102    Database: health_diet
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
-- Table structure for table `diet_day`
--

DROP TABLE IF EXISTS `diet_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diet_day` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `brekfast` char(255) COLLATE utf8_unicode_ci DEFAULT '[]',
  `lunch` char(255) COLLATE utf8_unicode_ci DEFAULT '[]',
  `dinner` char(255) COLLATE utf8_unicode_ci DEFAULT '[]',
  `adds` char(255) COLLATE utf8_unicode_ci DEFAULT '[]',
  `day` char(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diet_day`
--

LOCK TABLES `diet_day` WRITE;
/*!40000 ALTER TABLE `diet_day` DISABLE KEYS */;
/*!40000 ALTER TABLE `diet_day` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diet_menu`
--

DROP TABLE IF EXISTS `diet_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diet_menu` (
  `cat_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类唯一di',
  `parent_cid` bigint DEFAULT NULL,
  `name` char(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分类名称',
  `cat_level` int DEFAULT NULL COMMENT '层级',
  `show_status` tinyint DEFAULT NULL COMMENT '是否显示',
  `sort` int DEFAULT NULL COMMENT '排序',
  `icon` char(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图标地址',
  `quantity_of_heat` char(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `carbon_water_ratio` char(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `proportion_of_fat` char(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `proportion_of_protein` char(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diet_menu`
--

LOCK TABLES `diet_menu` WRITE;
/*!40000 ALTER TABLE `diet_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `diet_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diet_recipes`
--

DROP TABLE IF EXISTS `diet_recipes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diet_recipes` (
  `id` bigint DEFAULT NULL,
  `parent_cid` bigint DEFAULT NULL,
  `name` char(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cat_level` int DEFAULT NULL,
  `shou_status` tinyint DEFAULT NULL,
  `icon` char(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sort` int DEFAULT NULL,
  `quantity_of_heat` char(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `carbon_water_ratio` char(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `proportion_of_fat` char(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `proportion_of_protein` char(50) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diet_recipes`
--

LOCK TABLES `diet_recipes` WRITE;
/*!40000 ALTER TABLE `diet_recipes` DISABLE KEYS */;
/*!40000 ALTER TABLE `diet_recipes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-13 20:16:08
