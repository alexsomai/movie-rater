-- MySQL dump 10.13  Distrib 5.7.10, for Win64 (x86_64)
--
-- Host: localhost    Database: mr_schema
-- ------------------------------------------------------
-- Server version	5.7.10-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `id`       BIGINT(20) NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(255)        DEFAULT NULL,
  `role`     VARCHAR(255)        DEFAULT NULL,
  `username` VARCHAR(255)        DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k8h1bgqoplx0rkngj01pm1rgp` (`username`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts`
VALUES (1, 'dbbbec3191fcb5899692843078a4547fc916d6aea33f75066787a0a05bb39fe6eeb5d13f6c4cb16e', 'ROLE_USER', 'user'),
  (2, 'a61d7e0bb3c2d7e7da5a933be8e5b6794e92cce3879c6c6113bc493b2aaf18d76bb2f08303464783', 'ROLE_ADMIN', 'admin'),
  (3, '28902933d52713dcdc03bad507b80d94445253defc314f414ae2a4b30590ca1b7540011196483877', 'ROLE_USER', 'username1'),
  (4, 'e50d4c276d2cbc2cb1f5a8eb93a3a1bc83bc40bdf0b52c10f2e39717aedbea2fd641679f4109160e', 'ROLE_USER', 'username2'),
  (5, 'a79e00c698647212c145a48340a1f19e34ec46b3614c4a8fce47cec21a24bcb83c12464e4f03a305', 'ROLE_USER', 'username3'),
  (6, '30561b2486706c8dec3a79fca1610c9b2d4740bed4efbdc3c78f248a51b1297ed8f61b4c3b9c3143', 'ROLE_USER', 'username4'),
  (7, '62da7a8bb34c6615bd1b6c431125c2f3950349476dc1659269818065e328dea130004c9155d8c855', 'ROLE_USER', 'username5'),
  (8, 'fb7979fc6f7407d5137d8fd9ea07dc1a100244ea98895a3fdd1e1ab8f68fdd86859fdab7a9f924a3', 'ROLE_USER', 'username6'),
  (9, 'c4529c9653cc1c7bb3304f09aafae80085bce2f7fd1b30971511ab6536846c80344f705b5f95983a', 'ROLE_USER', 'username7'),
  (10, '00bac1f8e8b63c8b437d998fb27a1dc9a08beff1c16fd09fdd511bd2a802bd19987637599b462a92', 'ROLE_USER', 'username8'),
  (11, 'a9639769f2c811c15d2a2ec1ae2aa30841f7a05825b979eeec6c013916abc9f147e83a8bbd8ee6f2', 'ROLE_USER', 'username9'),
  (12, 'b6eb9703d855fa17beac76cdde76f1ee2e0da640ff879cd844472aaf6cd65f50d5f301410b7ae04e', 'ROLE_USER', 'username10');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id`    BIGINT(20) NOT NULL AUTO_INCREMENT,
  `genre` VARCHAR(32)         DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 22
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories`
VALUES (1, 'Action'), (2, 'Adventure'), (3, 'Animation'), (4, 'Comedy'), (5, 'Crime'), (6, 'Documentary'), (7, 'Drama'),
  (8, 'Family'), (9, 'Fantasy'), (10, 'Film-Noir'), (11, 'History'), (12, 'Horror'), (13, 'Music'), (14, 'Musical'),
  (15, 'Mystery'), (16, 'Romance'), (17, 'Sci-Fi'), (18, 'Sport'), (19, 'Thriller'), (20, 'War'), (21, 'Western');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movies` (
  `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `description`   TEXT         NOT NULL,
  `no_of_ratings` INT(11)               DEFAULT NULL,
  `poster`        VARCHAR(255) NOT NULL,
  `rate`          DECIMAL(10, 2)        DEFAULT NULL,
  `release_date`  DATETIME     NOT NULL,
  `title`         VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_govm2dombrdnujupo3o7hvtep` (`title`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (1,
                             'Three decades after the defeat of the Galactic Empire, a new threat arises. The First Order attempts to rule the galaxy and only a rag-tag group of heroes can stop them, along with the help of the Resistance.',
                             9, '781337876751955_star_wars_the_force_awakens.jpg', 4.93, '2015-12-18 00:00:00',
                             'Star Wars: The Force Awakens'),
  (2, 'Clark Kent, one of the last of an extinguished race disguised as an unremarkable human, is forced to reveal his identity when Earth is invaded by an army of survivors who threaten to bring the planet to the brink of destruction.', 6, '782057016951414_man_of_steel.jpg', 3.84, '2013-06-14 00:00:00', 'Man of Steel'),
  (3, 'Lion cub and future king Simba searches for his identity. His eagerness to please others and penchant for testing his boundaries sometimes gets him into trouble.', 6, '782239449854409_the_lion_king.jpg', 3.88, '1994-06-24 00:00:00', 'The Lion King'),
  (4, 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity\'s survival.', 6, '782589234580493_interstellar.jpg', 3.07, '2014-11-07 00:00:00', 'Interstellar'),
  (5, 'With the help of a German bounty hunter, a freed slave sets out to rescue his wife from a brutal Mississippi plantation owner.', 7, '782667769006745_django.jpg', 4.42, '2012-12-25 00:00:00', 'Django Unchained'),
  (6, 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 6, '782845535766334_the_godfather.jpg', 4.47, '1972-03-24 00:00:00', 'The Godfather'),
  (7, 'A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.', 6, '783019039790004_avatar.jpg', 3.65, '2009-12-18 00:00:00', 'Avatar'),
  (8, 'Earth\'s mightiest heroes must come together and learn to fight as a team if they are to stop the mischievous Loki and his alien army from enslaving humanity.', 6, '783087224701902_avengers.jpg', 4.29, '2012-05-04 00:00:00', 'The Avengers'),
  (9, 'A young man who survives a disaster at sea is hurtled into an epic journey of adventure and discovery. While cast away, he forms an unexpected connection with another survivor: a fearsome Bengal tiger.', 5, '783157301533324_life_of_pi.jpg', 4.60, '2012-11-21 00:00:00', 'Life of Pi'),
  (10, 'After a near-fatal plane crash in WWII, Olympian Louis Zamperini spends a harrowing 47 days in a raft with two fellow crewmen before he\'s caught by the Japanese navy and sent to a prisoner-of-war camp.', 6, '783253629681126_unbroken.jpg', 3.94, '2014-12-25 00:00:00', 'Unbroken'),
  (11, 'United Nations employee Gerry Lane traverses the world in a race against time to stop the Zombie pandemic that is toppling armies and governments, and threatening to destroy humanity itself.', 6, '783318976768474_world_war_z.jpg', 4.19, '2013-06-21 00:00:00', 'World War Z'),
  (12,
   'A bored married couple is surprised to learn that they are both assassins hired by competing agencies to kill each other.',
   5, '783381809752206_mr_mrs_smith.jpg', 3.91, '2005-06-10 00:00:00', 'Mr. & Mrs. Smith'), (13,
                                                                                             'Following the Normandy Landings, a group of U.S. soldiers go behind enemy lines to retrieve a paratrooper whose brothers have been killed in action.',
                                                                                             5,
                                                                                             '783446505329053_saving_private_ryan.jpg',
                                                                                             3.88,
                                                                                             '1998-07-24 00:00:00',
                                                                                             'Saving Private Ryan'),
  (14,
   'Gandalf and Aragorn lead the World of Men against Sauron\'s army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.',
   5, '783544047469823_lotr_the_return_of_the_king.jpg', 4.31, '2003-12-17 00:00:00',
   'The Lord of the Rings: The Return of the King'), (15,
                                                      'Bilbo and Company are forced to engage in a war against an array of combatants and keep the Lonely Mountain from falling into the hands of a rising ',
                                                      6, '783618746224357_the_hobbit_the_battle_of_five_armies.jpg',
                                                      4.47, '2014-12-17 00:00:00',
                                                      'The Hobbit: The Battle of the Five Armies'), (16,
                                                                                                     'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.',
                                                                                                     5,
                                                                                                     '783717650388389_the_shawshank_redemption.jpg',
                                                                                                     4.63,
                                                                                                     '1994-10-14 00:00:00',
                                                                                                     'The Shawshank Redemption'),
  (17,
   'A U.S Marshal investigates the disappearance of a murderess who escaped from a hospital for the criminally insane.',
   5, '783793007672976_shutter_island.jpg', 3.66, '2010-02-19 00:00:00', 'Shutter Island'), (18,
                                                                                             'Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.',
                                                                                             5,
                                                                                             '783846637637075_the_wolf_of_wall_street.jpg',
                                                                                             3.97,
                                                                                             '2013-12-25 00:00:00',
                                                                                             'The Wolf of Wall Street'),
  (19,
   'A thief who steals corporate secrets through use of the dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.',
   5, '783915646784045_inception.jpg', 4.19, '2010-07-16 00:00:00', 'Inception'), (20,
                                                                                   'When the newly crowned Queen Elsa accidentally uses her power to turn things into ice to curse her home in infinite winter, her sister, Anna, teams up with a mountain man, his playful reindeer, and a snowman to change the weather condition.',
                                                                                   5, '783961757299346_frozen.jpg',
                                                                                   2.54, '2013-11-27 00:00:00',
                                                                                   'Frozen');
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies_to_accounts`
--

DROP TABLE IF EXISTS `movies_to_accounts`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movies_to_accounts` (
  `rated_at`   DATE       NOT NULL,
  `stars`      DECIMAL(10, 2) DEFAULT NULL,
  `account_id` BIGINT(20) NOT NULL,
  `movie_id`   BIGINT(20) NOT NULL,
  PRIMARY KEY (`account_id`, `movie_id`),
  KEY `FK_opgwsswqe1t8l44silg96wu2u` (`account_id`),
  KEY `FK_d4qwvjadtfe6h9bdc0tylisat` (`movie_id`),
  CONSTRAINT `FK_d4qwvjadtfe6h9bdc0tylisat` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
  CONSTRAINT `FK_opgwsswqe1t8l44silg96wu2u` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies_to_accounts`
--

LOCK TABLES `movies_to_accounts` WRITE;
/*!40000 ALTER TABLE `movies_to_accounts` DISABLE KEYS */;
INSERT INTO `movies_to_accounts`
VALUES ('2015-12-29', 5.00, 3, 1), ('2015-12-29', 4.00, 3, 2), ('2015-12-29', 3.00, 3, 5), ('2015-12-29', 2.50, 3, 8),
  ('2015-12-29', 4.00, 3, 9), ('2015-12-29', 2.50, 3, 10), ('2015-12-29', 4.00, 3, 11), ('2015-12-29', 4.00, 3, 14),
  ('2015-12-29', 4.50, 3, 16), ('2015-12-29', 5.00, 3, 18), ('2015-12-29', 4.00, 3, 19), ('2015-12-29', 2.00, 3, 20),
  ('2015-12-29', 4.50, 4, 1), ('2015-12-29', 5.00, 4, 2), ('2015-12-29', 1.00, 4, 3), ('2015-12-29', 3.50, 4, 4),
  ('2015-12-29', 5.00, 4, 6), ('2015-12-29', 4.00, 4, 7), ('2015-12-29', 2.00, 4, 12), ('2015-12-29', 3.50, 4, 13),
  ('2015-12-29', 4.50, 4, 15), ('2015-12-29', 5.00, 4, 17), ('2015-12-29', 4.00, 5, 3), ('2015-12-29', 4.50, 5, 4),
  ('2015-12-29', 4.50, 5, 5), ('2015-12-29', 4.00, 5, 6), ('2015-12-29', 4.50, 5, 7), ('2015-12-29', 3.50, 5, 8),
  ('2015-12-29', 4.50, 5, 9), ('2015-12-29', 4.50, 5, 10), ('2015-12-29', 4.50, 5, 15), ('2015-12-29', 4.50, 5, 18),
  ('2015-12-29', 5.00, 5, 19), ('2015-12-29', 1.50, 5, 20), ('2015-12-29', 5.00, 6, 1), ('2015-12-29', 5.00, 6, 2),
  ('2015-12-29', 4.50, 6, 3), ('2015-12-29', 4.00, 6, 4), ('2015-12-29', 4.50, 6, 5), ('2015-12-29', 5.00, 6, 6),
  ('2015-12-29', 5.00, 6, 7), ('2015-12-29', 4.50, 6, 9), ('2015-12-29', 4.50, 6, 10), ('2015-12-29', 4.00, 6, 11),
  ('2015-12-29', 4.50, 6, 12), ('2015-12-29', 3.50, 6, 13), ('2015-12-29', 5.00, 6, 14), ('2015-12-29', 3.50, 6, 16),
  ('2015-12-29', 3.50, 6, 17), ('2015-12-29', 1.50, 7, 1), ('2015-12-29', 0.50, 7, 3), ('2015-12-29', 4.50, 7, 5),
  ('2015-12-29', 5.00, 7, 6), ('2015-12-29', 3.50, 7, 8), ('2015-12-29', 4.00, 7, 11), ('2015-12-29', 5.00, 7, 12),
  ('2015-12-29', 3.50, 7, 13), ('2015-12-29', 2.00, 7, 14), ('2015-12-29', 5.00, 7, 15), ('2015-12-29', 5.00, 7, 16),
  ('2015-12-29', 5.00, 7, 17), ('2015-12-29', 5.00, 7, 18), ('2015-12-29', 2.50, 7, 20), ('2015-12-29', 4.50, 8, 1),
  ('2015-12-29', 1.00, 8, 2), ('2015-12-29', 0.50, 8, 4), ('2015-12-29', 2.50, 8, 7), ('2015-12-29', 2.00, 8, 8),
  ('2015-12-29', 4.00, 8, 9), ('2015-12-29', 3.50, 8, 10), ('2015-12-29', 3.50, 8, 11), ('2015-12-29', 2.50, 8, 12),
  ('2015-12-29', 4.00, 8, 13), ('2015-12-29', 4.00, 8, 17), ('2015-12-29', 5.00, 8, 18), ('2015-12-29', 5.00, 8, 19),
  ('2015-12-29', 5.00, 8, 20), ('2015-12-29', 5.00, 9, 1), ('2015-12-29', 4.50, 9, 2), ('2015-12-29', 4.50, 9, 3),
  ('2015-12-29', 5.00, 9, 4), ('2015-12-29', 5.00, 9, 5), ('2015-12-29', 3.00, 9, 6), ('2015-12-29', 5.00, 9, 10),
  ('2015-12-29', 4.00, 9, 11), ('2015-12-29', 5.00, 9, 14), ('2015-12-29', 5.00, 9, 15), ('2015-12-29', 5.00, 9, 16),
  ('2015-12-29', 3.00, 9, 19), ('2015-12-29', 5.00, 10, 1), ('2015-12-29', 4.00, 10, 2), ('2015-12-29', 4.50, 10, 8),
  ('2015-12-29', 3.50, 10, 10), ('2015-12-29', 4.50, 10, 11), ('2015-12-29', 4.50, 10, 12),
  ('2015-12-29', 4.50, 10, 14), ('2015-12-29', 4.50, 10, 16), ('2015-12-29', 3.00, 10, 18), ('2015-12-29', 5.00, 11, 1),
  ('2015-12-29', 4.50, 11, 3), ('2015-12-29', 5.00, 11, 5), ('2015-12-29', 3.00, 11, 7), ('2015-12-29', 5.00, 11, 8),
  ('2015-12-29', 5.00, 11, 9), ('2015-12-29', 4.00, 11, 13), ('2015-12-29', 4.00, 11, 15), ('2015-12-29', 5.00, 12, 1),
  ('2015-12-29', 2.50, 12, 4), ('2015-12-29', 4.00, 12, 5), ('2015-12-29', 5.00, 12, 6), ('2015-12-29', 4.00, 12, 7),
  ('2015-12-29', 4.50, 12, 15), ('2015-12-29', 3.00, 12, 17), ('2015-12-29', 4.50, 12, 19),
  ('2015-12-29', 1.50, 12, 20);
/*!40000 ALTER TABLE `movies_to_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies_to_categories`
--

DROP TABLE IF EXISTS `movies_to_categories`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movies_to_categories` (
  `movie_id`    BIGINT(20) NOT NULL,
  `category_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`movie_id`, `category_id`),
  KEY `FK_buemxgdwy68phm1fr7l5phtft` (`category_id`),
  KEY `FK_352eo8cr50l159r6u0t1u2w04` (`movie_id`),
  CONSTRAINT `FK_352eo8cr50l159r6u0t1u2w04` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
  CONSTRAINT `FK_buemxgdwy68phm1fr7l5phtft` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies_to_categories`
--

LOCK TABLES `movies_to_categories` WRITE;
/*!40000 ALTER TABLE `movies_to_categories` DISABLE KEYS */;
INSERT INTO `movies_to_categories`
VALUES (1, 1), (2, 1), (7, 1), (8, 1), (11, 1), (12, 1), (13, 1), (19, 1), (1, 2), (2, 2), (3, 2), (4, 2), (7, 2),
  (9, 2), (11, 2), (14, 2), (15, 2), (20, 2), (3, 3), (20, 3), (12, 4), (18, 4), (20, 4), (6, 5), (12, 5), (16, 5),
  (18, 5), (3, 7), (4, 7), (5, 7), (6, 7), (9, 7), (10, 7), (13, 7), (14, 7), (16, 7), (1, 9), (2, 9), (7, 9), (9, 9),
  (14, 9), (15, 9), (11, 12), (17, 15), (19, 15), (4, 17), (8, 17), (19, 17), (10, 18), (8, 19), (17, 19), (13, 20),
  (5, 21);
/*!40000 ALTER TABLE `movies_to_categories` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2015-12-29 12:43:16
