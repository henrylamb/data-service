CREATE DATABASE  IF NOT EXISTS `user_service`;
USE `user_service`;
DROP TABLE IF EXISTS `USERS`;

CREATE TABLE `USERS` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `FULL_NAME` varchar(500) DEFAULT NULL, 
  `PASSWORD` varchar(500) DEFAULT NULL, 
  `EMAIL` varchar(500) DEFAULT NULL,
  `ADDRESS` varchar(100) DEFAULT NULL,
  `PHONE` varchar(250) DEFAULT NULL,
  `RESUME` varchar(500) DEFAULT NULL,
  `DEPARTMENT` varchar(500) DEFAULT NULL,
  `ROLE` varchar(500) DEFAULT NULL,
  `ADMIN_ID` int,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `USERS` WRITE;

INSERT INTO `USERS` (`FULL_NAME`, `PASSWORD`, `EMAIL`, `ADDRESS`, `PHONE`, `RESUME`, `DEPARTMENT`, `ROLE`, `ADMIN_ID`) VALUES
('admin', 'password', 'admin@admin.com', '123 Elm St, Springfield', '555-1234', 'alice_resume.pdf', 'Engineering', 'admin', NULL),
('Bob Smith', 'securepass456', 'bob.smith@example.com', '456 Oak St, Springfield', '555-5678', 'bob_resume.pdf', 'Engineering', 'hiring-manager', 1),
('Charlie Brown', 'mypassword789', 'charlie.brown@example.com', '789 Pine St, Springfield', '555-8765', 'charlie_resume.pdf', 'Sales', 'applicant', 2),
('Diana Prince', 'wonderpass111', 'diana.prince@example.com', '321 Maple St, Springfield', '555-4321', 'diana_resume.pdf', 'Marketing', 'applicant', 2),
('Edward Kenway', 'assassinpass222', 'edward.kenway@example.com', '654 Cedar St, Springfield', '555-9999', 'edward_resume.pdf', 'Engineering', 'admin', NULL);

UNLOCK TABLES;