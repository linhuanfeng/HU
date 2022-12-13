-- MySQL dump 10.13  Distrib 5.5.53, for Win32 (AMD64)
--
-- Host: 192.168.42.102    Database: health_sport
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
-- Table structure for table `sport_data`
--

DROP TABLE IF EXISTS `sport_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sport_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '运动数据编号',
  `date` date DEFAULT NULL COMMENT '运动日期',
  `type` bigint DEFAULT NULL COMMENT '运动类型',
  `duration` int DEFAULT NULL COMMENT '运动时长',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci COMMENT='运动数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sport_data`
--

LOCK TABLES `sport_data` WRITE;
/*!40000 ALTER TABLE `sport_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `sport_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sport_music`
--

DROP TABLE IF EXISTS `sport_music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sport_music` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '运动类型id',
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '音乐名称',
  `author` int DEFAULT NULL COMMENT '音乐作者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci COMMENT='运动音乐';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sport_music`
--

LOCK TABLES `sport_music` WRITE;
/*!40000 ALTER TABLE `sport_music` DISABLE KEYS */;
/*!40000 ALTER TABLE `sport_music` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sport_plan`
--

DROP TABLE IF EXISTS `sport_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sport_plan` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '运动计划id',
  `user_id` bigint DEFAULT NULL COMMENT '用户编号',
  `plan_per_day` int DEFAULT '0' COMMENT '每日运动时长计划（分钟）',
  `plan_per_week` int DEFAULT '0' COMMENT '每周运动时长计划（分钟）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci COMMENT='运动计划';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sport_plan`
--

LOCK TABLES `sport_plan` WRITE;
/*!40000 ALTER TABLE `sport_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `sport_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sport_type`
--

DROP TABLE IF EXISTS `sport_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sport_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '运动类型id',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '类型名字',
  `calorie_per_minute` int DEFAULT NULL COMMENT '每分钟消耗多少卡路里',
  `tip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '温馨提示',
  `level` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '强度等级',
  `small_img` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '小图标',
  `big_img` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '大图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci COMMENT='运动类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sport_type`
--

LOCK TABLES `sport_type` WRITE;
/*!40000 ALTER TABLE `sport_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `sport_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-13 20:16:34
