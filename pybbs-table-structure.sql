-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: localhost    Database: pybbs
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.16.04.1

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
-- Table structure for table `pybbs_code`
--

DROP TABLE IF EXISTS `pybbs_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pybbs_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `is_used` bit(1) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_afiucwwjc2v5m1fu1y9f7e5db` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pybbs_collect`
--

DROP TABLE IF EXISTS `pybbs_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pybbs_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `in_time` datetime DEFAULT NULL,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrhe205klj223ef7438burgo4u` (`topic_id`),
  KEY `FK60is5xb7su3uy7xq7tbgv16cx` (`user_id`),
  CONSTRAINT `FK60is5xb7su3uy7xq7tbgv16cx` FOREIGN KEY (`user_id`) REFERENCES `pybbs_user` (`id`),
  CONSTRAINT `FKrhe205klj223ef7438burgo4u` FOREIGN KEY (`topic_id`) REFERENCES `pybbs_topic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pybbs_notification`
--

DROP TABLE IF EXISTS `pybbs_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pybbs_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(255) DEFAULT NULL,
  `content` text,
  `editor` varchar(255) DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  `is_read` bit(1) DEFAULT NULL,
  `target_user_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaivxata5lncht2isi5yi4b35t` (`target_user_id`),
  KEY `FKg8psse121wu5xi6jv060hoypy` (`topic_id`),
  KEY `FKg3nithi94rp1rf46n08q305ef` (`user_id`),
  CONSTRAINT `FKaivxata5lncht2isi5yi4b35t` FOREIGN KEY (`target_user_id`) REFERENCES `pybbs_user` (`id`),
  CONSTRAINT `FKg3nithi94rp1rf46n08q305ef` FOREIGN KEY (`user_id`) REFERENCES `pybbs_user` (`id`),
  CONSTRAINT `FKg8psse121wu5xi6jv060hoypy` FOREIGN KEY (`topic_id`) REFERENCES `pybbs_topic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pybbs_permission`
--

DROP TABLE IF EXISTS `pybbs_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pybbs_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pid` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pybbs_reply`
--

DROP TABLE IF EXISTS `pybbs_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pybbs_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `down` int(11) NOT NULL,
  `down_ids` text,
  `editor` varchar(255) DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  `up` int(11) NOT NULL,
  `up_down` int(11) NOT NULL,
  `up_ids` text,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaxucxvmn3vd0pdphi1i3m6re2` (`topic_id`),
  KEY `FKh083t089l3xqwct53ve13fjtp` (`user_id`),
  CONSTRAINT `FKaxucxvmn3vd0pdphi1i3m6re2` FOREIGN KEY (`topic_id`) REFERENCES `pybbs_topic` (`id`),
  CONSTRAINT `FKh083t089l3xqwct53ve13fjtp` FOREIGN KEY (`user_id`) REFERENCES `pybbs_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pybbs_role`
--

DROP TABLE IF EXISTS `pybbs_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pybbs_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pybbs_role_permission`
--

DROP TABLE IF EXISTS `pybbs_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pybbs_role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FKj6bo8r61gyclgb41ppuvrnxpi` (`permission_id`),
  CONSTRAINT `FKj6bo8r61gyclgb41ppuvrnxpi` FOREIGN KEY (`permission_id`) REFERENCES `pybbs_permission` (`id`),
  CONSTRAINT `FKksb0b8rm0k0b89trps3dd0doj` FOREIGN KEY (`role_id`) REFERENCES `pybbs_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pybbs_section`
--

DROP TABLE IF EXISTS `pybbs_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pybbs_section` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_36xiryn6obfp9e645q6vlgd77` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pybbs_topic`
--

DROP TABLE IF EXISTS `pybbs_topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pybbs_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `editor` varchar(255) DEFAULT NULL,
  `good` bit(1) NOT NULL,
  `in_time` datetime NOT NULL,
  `topic_lock` bit(1) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `reply_count` int(11) DEFAULT NULL,
  `tab` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `top` bit(1) NOT NULL,
  `up_ids` text,
  `view` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3h5tfiica9w93tm5iwt3jtn1y` (`title`),
  KEY `FKbcbkvgf4lg72q0cb3obblw1y4` (`user_id`),
  CONSTRAINT `FKbcbkvgf4lg72q0cb3obblw1y4` FOREIGN KEY (`user_id`) REFERENCES `pybbs_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pybbs_user`
--

DROP TABLE IF EXISTS `pybbs_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pybbs_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attempts` int(11) NOT NULL,
  `attempts_time` datetime DEFAULT NULL,
  `avatar` varchar(255) NOT NULL,
  `block` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `in_time` datetime NOT NULL,
  `password` varchar(255) NOT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pv3o1wtwekmwy2xwmcejx336e` (`email`),
  UNIQUE KEY `UK_nuca3smhxvl3x008m9y60v3f3` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pybbs_user_role`
--

DROP TABLE IF EXISTS `pybbs_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pybbs_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKd8byynux0fguwt59me1kc9fbj` (`role_id`),
  CONSTRAINT `FK5y0frqoe35ygvtr0dctublhdj` FOREIGN KEY (`user_id`) REFERENCES `pybbs_user` (`id`),
  CONSTRAINT `FKd8byynux0fguwt59me1kc9fbj` FOREIGN KEY (`role_id`) REFERENCES `pybbs_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-07 14:43:24