-- bunmo.`member` definition

CREATE TABLE `member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active_type` enum('ACTIVE','BANNED','INACTIVE','PENDING','WITHDRAWAL') NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `gathering_open_count` int NOT NULL,
  `x` decimal(17,14) DEFAULT NULL,
  `y` decimal(16,14) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `gender` tinyint DEFAULT NULL,
  `profile_image_url` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','MEMBER') DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `uuid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `member_chk_1` CHECK ((`gender` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- bunmo.product_category definition (레거시 - gathering 테이블 재설계로 미사용)


-- bunmo.reported_user_history definition

CREATE TABLE `reported_user_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `reported_date` date NOT NULL,
  `reportee_id` bigint NOT NULL,
  `reporter_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- bunmo.social_account definition

CREATE TABLE `social_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `provider` enum('APPLE','KAKAO') NOT NULL,
  `provider_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- bunmo.gathering definition

CREATE TABLE `gathering` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` enum('ONLINE','MART','FREE') NOT NULL,
  `active_type` enum('ACTIVE','DELETED','REPORTED','CLOSED') NOT NULL,
  `category` enum('FOOD','DAILY_SUPPLIES','ELECTRONICS','BEAUTY','BABY','KITCHEN','HOBBY','PET','FASHION','FURNITURE','SPORTS','AUTO','BOOK','OFFICE') NOT NULL,
  `name` varchar(20) NOT NULL,
  `introduction` varchar(500) NOT NULL,
  `open_chat_link` varchar(200) NOT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `product_link` varchar(200) DEFAULT NULL,
  `meeting_date` date NOT NULL,
  `meeting_time` time NOT NULL,
  `x` decimal(17,14) NOT NULL,
  `y` decimal(16,14) NOT NULL,
  `address` varchar(20) NOT NULL,
  `max_participant_count` int NOT NULL,
  `owner_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- bunmo.gathering_participant definition

CREATE TABLE `gathering_participant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `joined_at` datetime(6) NOT NULL,
  `member_id` bigint DEFAULT NULL,
  `gathering_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmm1de6rmnptu6nd6qris5vy4g` (`gathering_id`),
  CONSTRAINT `FKmm1de6rmnptu6nd6qris5vy4g` FOREIGN KEY (`gathering_id`) REFERENCES `gathering` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;