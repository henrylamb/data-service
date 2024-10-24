CREATE DATABASE  IF NOT EXISTS `your_database_name`;
USE `your_database_name`;

DROP TABLE IF EXISTS `JOB`;

CREATE TABLE `JOB` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `department` varchar(25) DEFAULT NULL,
  `listing_title` varchar(100) DEFAULT NULL,
  `date_listed` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `date_closed` timestamp NULL DEFAULT NULL,
  `job_title` VARCHAR(45) DEFAULT NULL,
  `job_description` text,
  `additional_information` text,
  `listing_status` VARCHAR(25) DEFAULT NULL,
  `experience_level` VARCHAR(100) DEFAULT NULL,
  `model_resume` text,
  `model_cover_letter` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `JOB` WRITE;

INSERT INTO `JOB`VALUES
(1, 1, 'Engineering', 'Software Engineer', CURRENT_TIMESTAMP, NULL, 'Software Engineer', 'Develop and maintain software applications.', 'Remote work available.', 'Open', 'Senior', 'resume1.pdf', 'cover_letter1.pdf'),
(2, 1, 'Marketing', 'Marketing Specialist', CURRENT_TIMESTAMP, NULL, 'Marketing Specialist', 'Assist in marketing campaigns.', 'Experience in digital marketing preferred.', 'Open', 'Mid-level', 'resume2.pdf', 'cover_letter2.pdf'),
(3, 1, 'Sales', 'Sales Associate', CURRENT_TIMESTAMP, NULL, 'Sales Associate', 'Manage customer accounts and sales.', 'Strong communication skills required.', 'Open', 'Entry-level', 'resume3.pdf', 'cover_letter3.pdf');
UNLOCK TABLES;

DROP TABLE IF EXISTS `APPLICATION`;

CREATE TABLE `APPLICATION` (
  `id` int NOT NULL AUTO_INCREMENT,
  `candidate_id` int DEFAULT NULL,
  `candidate_email` varchar(255) DEFAULT NULL,
  `job_id` int DEFAULT NULL,
  `date_applied` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `application_status` varchar(45) DEFAULT NULL,
  `years_of_experience` int DEFAULT NULL,
  `match_job_description_score` int DEFAULT NULL,
  `past_experience_score` int DEFAULT NULL,
  `motivation_score` int DEFAULT NULL,
  `academic_achievement_score` int DEFAULT NULL,
  `pedigree_score` int DEFAULT NULL,
  `trajectory_score` int DEFAULT NULL,
  `extenuating_circumstances_score` int DEFAULT NULL,
  `average_score` int DEFAULT NULL,
  `review` text,
  `custom_resume` text,
  `cover_letter` text,
  PRIMARY KEY (`id`),
  CONSTRAINT `job_application` FOREIGN KEY (`job_id`) REFERENCES `JOB` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `APPLICATION` WRITE;

INSERT INTO `APPLICATION` VALUES
(101, 3, 'candidate1@example.com', 1, CURRENT_TIMESTAMP, 'Applied', 5, 85, 80, 90, 75, 70, 80, 0, '80', 'Strong candidate with relevant experience.', 'resume1.pdf', 'cover_letter1.pdf'),
(102, 3, 'candidate2@example.com', 1, CURRENT_TIMESTAMP, 'Interviewed', 4, 70, 65, 80, 60, 75, 70, 0, '70', 'Good fit for the role.', 'resume1.pdf', 'cover_letter1.pdf'),
(103, 3, 'candidate3@example.com', 2, CURRENT_TIMESTAMP, 'Applied', 3, 65, 60, 75, 80, 50, 65, 1, '70', 'Solid background in marketing.', 'resume1.pdf', 'cover_letter1.pdf'),
(104, 3, 'candidate4@example.com', 3, CURRENT_TIMESTAMP, 'Rejected', 2, 50, 55, 60, 70, 45, 55, 2, '55', 'Needs more experience.', 'resume1.pdf', 'cover_letter1.pdf');

UNLOCK TABLES