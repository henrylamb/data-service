CREATE TABLE JOB (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    user_id INTEGER DEFAULT NULL,
    department VARCHAR(25) DEFAULT NULL,
    listing_title VARCHAR(100) DEFAULT NULL,
    date_listed TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_closed TIMESTAMP DEFAULT NULL,
    job_title VARCHAR(45) DEFAULT NULL,
    job_description CLOB,
    additional_information CLOB,
    listing_status VARCHAR(25) DEFAULT NULL,
    experience_level VARCHAR(100) DEFAULT NULL,
    model_resume CLOB,
    model_cover_letter CLOB,
    PRIMARY KEY (id)
);

CREATE TABLE APPLICATION (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    candidate_id INTEGER DEFAULT NULL,
    candidate_email VARCHAR(255) DEFAULT NULL,
    job_id INTEGER DEFAULT NULL,
    date_applied TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    application_status VARCHAR(45) DEFAULT NULL,
    years_of_experience INTEGER,
    match_job_description_score INTEGER,
    past_experience_score INTEGER,
    motivation_score INTEGER,
    academic_achievement_score INTEGER,
    pedigree_score INTEGER,
    trajectory_score INTEGER,
    extenuating_circumstances_score INTEGER,
    average_score CLOB,
    review CLOB,
    custom_resume CLOB,
    cover_letter CLOB,
    PRIMARY KEY (id),
    FOREIGN KEY (job_id) REFERENCES JOB(id) ON DELETE CASCADE
);