-- TODO- should the tables have a drop method to drop the tables if they already exist?

-- Create JOB table
CREATE TABLE JOBS (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT DEFAULT NULL,
    department VARCHAR(25) DEFAULT NULL,
    listing_title VARCHAR(100) DEFAULT NULL,
    date_listed TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_closed TIMESTAMP DEFAULT NULL,
    job_title VARCHAR(45) DEFAULT NULL,
    job_description TEXT,
    additional_information TEXT,
    listing_status VARCHAR(25) DEFAULT NULL,
    experience_level VARCHAR(100) DEFAULT NULL,
    model_resume TEXT,
    model_cover_letter TEXT,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci; 

-- Create an Application table
CREATE TABLE APPLICATIONS (
  `id` int NOT NULL AUTO_INCREMENT,
  `candidate_id` int DEFAULT NULL,
  `candidate_email` varchar(255) DEFAULT NULL,
  `job_id` int DEFAULT NULL,
  `date_applied` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `cover_letter` text,
  `custom_resume` text,
  `application_status` varchar(45) DEFAULT NULL,
  `years_of_experience` int,
  `match_job_description_score` int,
  `past_experience_score` int,
  `motivation_score` int,
  `acedemic_achievement_score` int,
  `pedigree_score` int,
  `trajectory_score` int,
  `extenuating_circumstances_score` int,
  `average_score` text,
  `review` text,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_application_job` FOREIGN KEY (`job_id`) REFERENCES `job`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- CONSTRAINT: just a label that makes it easier to identify this foreign key relationship, while the foreign key itself (job_id) defines the actual link between an application and a specific job.
