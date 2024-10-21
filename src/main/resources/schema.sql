-- TODO- should the tables have a drop method to drop the tables if they already exist?

-- CREATE TABLE "CUSTOMERS" ("ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "CUSTOMER_NAME" VARCHAR(255), "PASSWORD" VARCHAR(255),EMAIL VARCHAR(255));

-- Create JOB table
CREATE TABLE JOB (
    -- id INT NOT NULL AUTO_INCREMENT,
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
    user_id INTEGER DEFAULT NULL,
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
CREATE TABLE APPLICATION (
  -- `id` int NOT NULL AUTO_INCREMENT,
  "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
  `candidate_id` INTEGER DEFAULT NULL,
  `candidate_email` varchar(255) DEFAULT NULL,
  `job_id` INTEGER DEFAULT NULL,
  `date_applied` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `cover_letter` text,
  `custom_resume` text,
  `application_status` varchar(45) DEFAULT NULL,
  `years_of_experience` INTEGER,
  `match_job_description_score` INTEGER,
  `past_experience_score` INTEGER,
  `motivation_score` INTEGER,
  `acedemic_achievement_score` INTEGER,
  `pedigree_score` INTEGER,
  `trajectory_score` INTEGER,
  `extenuating_circumstances_score` INTEGER,
  `average_score` text,
  `review` text,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_application_job` FOREIGN KEY (`job_id`) REFERENCES `job`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- CONSTRAINT: just a label that makes it easier to identify this foreign key relationship, while the foreign key itself (job_id) defines the actual link between an application and a specific job.
