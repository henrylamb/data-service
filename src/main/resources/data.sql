-- Inserting mock data into the JOB table
INSERT INTO JOB (user_id, department, listing_title, date_listed, date_closed, job_title, job_description, additional_information, listing_status, experience_level, model_resume, model_cover_letter)
VALUES
(1, 'Engineering', 'Software Engineer', CURRENT_TIMESTAMP, NULL, 'Software Engineer', 'Develop and maintain software applications.', 'Remote work available.', 'Open', 'Senior', 'resume1.pdf', 'cover_letter1.pdf'),
(2, 'Marketing', 'Marketing Specialist', CURRENT_TIMESTAMP, NULL, 'Marketing Specialist', 'Assist in marketing campaigns.', 'Experience in digital marketing preferred.', 'Open', 'Mid-level', 'resume2.pdf', 'cover_letter2.pdf'),
(3, 'Sales', 'Sales Associate', CURRENT_TIMESTAMP, NULL, 'Sales Associate', 'Manage customer accounts and sales.', 'Strong communication skills required.', 'Open', 'Entry-level', 'resume3.pdf', 'cover_letter3.pdf');

-- Inserting mock data into the APPLICATION table
INSERT INTO APPLICATION (candidate_id, candidate_email, job_id, date_applied, application_status, years_of_experience, match_job_description_score, past_experience_score, motivation_score, academic_achievement_score, pedigree_score, trajectory_score, extenuating_circumstances_score, average_score, review)
 VALUES
(101, 'candidate1@example.com', 1, CURRENT_TIMESTAMP, 'Applied', 5, 85, 80, 90, 75, 70, 80, 0, '80', 'Strong candidate with relevant experience.'),
(102, 'candidate2@example.com', 1, CURRENT_TIMESTAMP, 'Interviewed', 4, 70, 65, 80, 60, 75, 70, 0, '70', 'Good fit for the role.'),
 (103, 'candidate3@example.com', 2, CURRENT_TIMESTAMP, 'Applied', 3, 65, 60, 75, 80, 50, 65, 1, '70', 'Solid background in marketing.'),
 (104, 'candidate4@example.com', 3, CURRENT_TIMESTAMP, 'Rejected', 2, 50, 55, 60, 70, 45, 55, 2, '55', 'Needs more experience.');