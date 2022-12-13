-- MySQL dump 10.13  Distrib 5.5.53, for Win32 (AMD64)
--
-- Host: 192.168.42.102    Database: health_ums
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
-- Table structure for table `ums_evaluation`
--

DROP TABLE IF EXISTS `ums_evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_evaluation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评估id',
  `choice_a` varchar(128) DEFAULT NULL,
  `choice_b` varchar(128) DEFAULT NULL,
  `choice_c` varchar(128) DEFAULT NULL,
  `choice_d` varchar(128) DEFAULT NULL,
  `origin` varchar(128) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_evaluation`
--

LOCK TABLES `ums_evaluation` WRITE;
/*!40000 ALTER TABLE `ums_evaluation` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_evaluation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_expert`
--

DROP TABLE IF EXISTS `ums_expert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_expert` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名字',
  `identity` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '职业',
  `field` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '领域',
  `university` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '大学',
  `introduction` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '简介',
  `tag` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '擅长标签',
  `score` decimal(1,1) DEFAULT NULL COMMENT '星级',
  `fare` decimal(6,2) DEFAULT NULL COMMENT '价格',
  `type` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '线上线下',
  `materials` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '证明材料',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_expert`
--

LOCK TABLES `ums_expert` WRITE;
/*!40000 ALTER TABLE `ums_expert` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_expert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_member`
--

DROP TABLE IF EXISTS `ums_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_member` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `level_id` bigint DEFAULT NULL COMMENT '会员等级id',
  `username` char(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `nickname` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `height` int DEFAULT '0' COMMENT '身高',
  `gender` tinyint DEFAULT NULL COMMENT '性别',
  `birth` date DEFAULT NULL COMMENT '生日',
  `city` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所在城市',
  `job` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '职业',
  `sign` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '个性签名',
  `question_count` bigint DEFAULT '0' COMMENT '发帖数量',
  `integration` int DEFAULT NULL COMMENT '积分',
  `growth` int DEFAULT NULL COMMENT '成长值',
  `status` tinyint DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `avatar` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户头像图片',
  `open_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '微信登录openid',
  `session_key` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '微信登录会话KEY',
  `province` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省份',
  `expect_id` bigint DEFAULT NULL COMMENT '专家id',
  `weight` int DEFAULT '0' COMMENT '体重',
  `like_count` bigint DEFAULT '0' COMMENT '点赞数',
  `comment_count` bigint DEFAULT '0' COMMENT '评论数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci COMMENT='会员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_member`
--

LOCK TABLES `ums_member` WRITE;
/*!40000 ALTER TABLE `ums_member` DISABLE KEYS */;
INSERT INTO `ums_member` VALUES (1,NULL,'微信用户',NULL,'微信用户',NULL,NULL,0,0,NULL,'',NULL,NULL,0,NULL,NULL,NULL,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132','oKGOZ4t1oyz4MrsjSX6JOsqtwswY','cHAp230/oUpuwYdSYpcwDA==','',NULL,0,0,15);
/*!40000 ALTER TABLE `ums_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_message`
--

DROP TABLE IF EXISTS `ums_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `from_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  `question_id` bigint DEFAULT NULL,
  `question_title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `status` tinyint DEFAULT '1' COMMENT '1未读->2已读->3已删除',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1602498540582469634 DEFAULT CHARSET=utf8mb3 COMMENT='点赞评论通知';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_message`
--

LOCK TABLES `ums_message` WRITE;
/*!40000 ALTER TABLE `ums_message` DISABLE KEYS */;
INSERT INTO `ums_message` VALUES (1602207309646270465,1,1,NULL,NULL,'评论',1,'2022-12-12 15:42:42'),(1602498540582469633,1,1,NULL,NULL,'评论',1,'2022-12-13 11:00:00');
/*!40000 ALTER TABLE `ums_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_report`
--

DROP TABLE IF EXISTS `ums_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_report` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '测评报告编号',
  `score` int DEFAULT NULL COMMENT '分数',
  `sport_suggestion` varchar(128) DEFAULT NULL COMMENT '运动建议',
  `sleep_suggestion` varchar(128) DEFAULT NULL COMMENT '睡眠建议',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `diet_suggestion` varchar(128) DEFAULT NULL COMMENT '饮食建议',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_report`
--

LOCK TABLES `ums_report` WRITE;
/*!40000 ALTER TABLE `ums_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_report` ENABLE KEYS */;
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

-- Dump completed on 2022-12-13 20:16:52
