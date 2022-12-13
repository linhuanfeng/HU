-- MySQL dump 10.13  Distrib 5.5.53, for Win32 (AMD64)
--
-- Host: 192.168.42.102    Database: health_community
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
-- Table structure for table `community_area`
--

DROP TABLE IF EXISTS `community_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `community_area` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '站点id',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名字',
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图片',
  `fan_count` text CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT '关注数',
  `creator` bigint NOT NULL COMMENT '创建者id',
  `question_count` int DEFAULT '0' COMMENT '发帖数',
  `view_count` int DEFAULT '0' COMMENT '浏览数',
  `like_count` int DEFAULT '0' COMMENT '点赞数',
  `tag` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '标签',
  `sticky` int DEFAULT '0' COMMENT '是否置顶',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community_area`
--

LOCK TABLES `community_area` WRITE;
/*!40000 ALTER TABLE `community_area` DISABLE KEYS */;
/*!40000 ALTER TABLE `community_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community_area_member`
--

DROP TABLE IF EXISTS `community_area_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `community_area_member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL COMMENT '会员id',
  `area_id` bigint DEFAULT NULL COMMENT '站点id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community_area_member`
--

LOCK TABLES `community_area_member` WRITE;
/*!40000 ALTER TABLE `community_area_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `community_area_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community_comment`
--

DROP TABLE IF EXISTS `community_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `community_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT '0',
  `question_id` bigint DEFAULT NULL COMMENT '帖子id',
  `creator_id` bigint DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `like_count` bigint DEFAULT '0' COMMENT '点赞数',
  `content` varchar(1024) DEFAULT NULL COMMENT '内容',
  `comment_count` int DEFAULT '0' COMMENT '评论数',
  `status` int DEFAULT NULL COMMENT '逻辑删除，折叠等等',
  `grand_parent_id` bigint DEFAULT '0',
  `creator_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community_comment`
--

LOCK TABLES `community_comment` WRITE;
/*!40000 ALTER TABLE `community_comment` DISABLE KEYS */;
INSERT INTO `community_comment` VALUES (1,0,1,1,'2022-12-10 16:14:55',0,'呜呜呜呜',3,0,0,'微信用户'),(13,1,1,1,'2022-12-12 11:04:53',0,'222',0,0,1,'微信用户'),(14,1,1,0,'2022-12-12 14:59:15',0,'333',0,0,1,'微信用户'),(15,0,1,1,'2022-12-12 15:03:26',0,'333',1,0,0,'微信用户'),(16,15,1,1,'2022-12-12 15:29:07',0,'333',0,0,15,'微信用户'),(18,1,1,1,'2022-12-12 15:42:41',0,'333',0,0,1,'微信用户'),(19,0,2,1,'2022-12-13 10:59:59',0,'5555',0,0,0,'微信用户');
/*!40000 ALTER TABLE `community_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community_question`
--

DROP TABLE IF EXISTS `community_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `community_question` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `title` varchar(50) DEFAULT '',
  `description` varchar(255) DEFAULT '',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` bigint DEFAULT '0',
  `comment_count` int DEFAULT '0' COMMENT '评论数',
  `view_count` int DEFAULT '0' COMMENT '浏览数',
  `like_count` int DEFAULT '0' COMMENT '点赞数',
  `tag` varchar(128) DEFAULT '',
  `sticky` int DEFAULT '0' COMMENT '是否置顶',
  `area_id` bigint DEFAULT '0',
  `img` varchar(256) DEFAULT '',
  `area_name` varchar(255) DEFAULT '',
  `creator_name` varchar(128) DEFAULT '',
  `type` int DEFAULT NULL COMMENT '0--医聊，1--咨询',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community_question`
--

LOCK TABLES `community_question` WRITE;
/*!40000 ALTER TABLE `community_question` DISABLE KEYS */;
INSERT INTO `community_question` VALUES (1,'巴萨军团遗憾止步八强','内马尔泪洒现场','2022-12-10 08:10:56','2022-12-13 09:46:20',1,6,0,0,'世界杯;巴西落败',0,0,'','足球;世界杯','hf',0),(2,'克罗地亚老兵不死','门将封神','2022-12-10 08:10:56','2022-12-13 03:00:57',1,1,0,0,'世界杯;克罗地亚',0,0,'','足球：世界杯','hf',NULL);
/*!40000 ALTER TABLE `community_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community_thumbs`
--

DROP TABLE IF EXISTS `community_thumbs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `community_thumbs` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '点赞id',
  `member_id` bigint NOT NULL COMMENT '会员id',
  `entity_id` bigint DEFAULT NULL COMMENT '帖子id或评论id',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '点赞类型，0表示帖子，1表示评论',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_idx_mid_eid_type` (`member_id`,`entity_id`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community_thumbs`
--

LOCK TABLES `community_thumbs` WRITE;
/*!40000 ALTER TABLE `community_thumbs` DISABLE KEYS */;
/*!40000 ALTER TABLE `community_thumbs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `undo_log` (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='AT transaction mode undo table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `undo_log`
--

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-13 20:13:54
