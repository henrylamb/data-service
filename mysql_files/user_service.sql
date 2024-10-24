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
  `ADMIN_ID` int
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `USERS` WRITE;

INSERT INTO `USERS` VALUES
('John doe', 'password', 'email@email.com', 'Suite 90', '692 743 4843', 'Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.', 'Services', 'ROLE_CANDIDATE', 1),
('Phelia Oles', 'mZ0)Anpz(MG', 'poles1@salon.com', 'PO Box 97110', '569 983 0739', 'Proin interdum mauris non ligula pellentesque ultrices.', 'Services', 'ROLE_CANDIDATE', 2),
('manager', 'managerpass', 'manager@manager.com', '19th Floor', '679 512 3716', 'Praesent lectus. Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis. Duis consequat dui nec nisi volutpat eleifend.', 'Business Development', 'ROLE_MANAGER', 3),
('Gardener Dax', 'uO4%8dM''Oo', 'gdax3@com.com', 'PO Box 2764', '713 790 1081', 'Phasellus in felis. Donec semper sapien a libero. Nam dui. Proin leo odio, porttitor id, consequat in, consequat ut, nulla. Sed accumsan felis. Ut at dolor quis odio consequat varius. Integer ac leo.', 'Marketing', 'ROLE_CANDIDATE', 4),
('Katy Trounson', 'bO1%90qfg)jJ!', 'ktrounson4@360.cn', 'Suite 7', '773 696 6231', 'Nam dui. Proin leo odio, porttitor id, consequat in, consequat ut, nulla. Sed accumsan felis. Ut at dolor quis odio consequat varius. Integer ac leo.', 'Sales', 'ROLE_CANDIDATE', 5),
('admin', 'adminpass', 'admin@admin.com', 'Suite 7', '773 696 6231', 'Nam dui. Proin leo odio, porttitor id, consequat in, consequat ut, nulla. Sed accumsan felis. Ut at dolor quis odio consequat varius. Integer ac leo.', 'Sales', 'ROLE_ADMIN', 5);


UNLOCK TABLES;
