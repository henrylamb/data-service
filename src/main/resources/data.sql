-- Insert mock data into JOB table
INSERT INTO JOB (user_id, department, listing_title, date_listed, date_closed, job_title, job_description, additional_information, listing_status, experience_level, model_resume, model_cover_letter)
VALUES 
(1, 'Engineering', 'Software Engineer', '2023-01-01 10:00:00', NULL, 'Software Engineer', 'Develop and maintain software applications.', 'N/A', 'Open', 'Mid-level', 'Sample resume text', 'Sample cover letter text'),
(2, 'Marketing', 'Marketing Specialist', '2023-02-01 11:00:00', NULL, 'Marketing Specialist', 'Plan and execute marketing campaigns.', 'N/A', 'Open', 'Entry-level', 'Sample resume text', 'Sample cover letter text'),
(3, 'Sales', 'Sales Manager', '2023-03-01 12:00:00', NULL, 'Sales Manager', 'Lead the sales team to achieve targets.', 'N/A', 'Open', 'Senior-level', 'Sample resume text', 'Sample cover letter text');

-- Insert mock data into APPLICATION table
INSERT INTO APPLICATION (candidate_id, candidate_email, job_id, date_applied, cover_letter, custom_resume, application_status, years_of_experience, match_job_description_score, past_experience_score, motivation_score, academic_achievement_score, pedigree_score, trajectory_score, extenuating_circumstances_score, average_score, review)
VALUES 
(1, 'candidate1@example.com', 1, '2023-01-02 10:00:00', 'Cover letter text 1', 'Custom resume text 1', 'Submitted', 3, 80, 70, 75, 85, 90, 88, 70, '81.14', 'Review text 1'),
(2, 'candidate2@example.com', 2, '2023-02-02 11:00:00', 'Cover letter text 2', 'Custom resume text 2', 'Submitted', 1, 60, 65, 70, 75, 80, 78, 60, '69.71', 'Review text 2'),
(3, 'candidate3@example.com', 3, '2023-03-02 12:00:00', 'Cover letter text 3', 'Custom resume text 3', 'Submitted', 5, 90, 85, 80, 95, 100, 98, 90, '91.14', 'Review text 3');
(4, 'candidate1@example.com', 2, '2023-02-03 10:00:00', 'Cover letter text 4', 'Custom resume text 4', 'Submitted', 2, 70, 75, 80, 85, 90, 88, 75, '80.71', 'Review text 4'),
(5, 'candidate2@example.com', 3, '2023-03-03 11:00:00', 'Cover letter text 5', 'Custom resume text 5', 'Submitted', 4, 85, 80, 75, 90, 95, 93, 85, '86.71', 'Review text 5'),
(6, 'candidate3@example.com', 1, '2023-01-03 12:00:00', 'Cover letter text 6', 'Custom resume text 6', 'Submitted', 3, 75, 70, 65, 80, 85, 83, 70, '75.71', 'Review text 6'),
(7, 'candidate1@example.com', 3, '2023-03-04 10:00:00', 'Cover letter text 7', 'Custom resume text 7', 'Submitted', 5, 95, 90, 85, 100, 105, 103, 95, '96.71', 'Review text 7'),
(8, 'candidate2@example.com', 1, '2023-01-04 11:00:00', 'Cover letter text 8', 'Custom resume text 8', 'Submitted', 2, 65, 60, 55, 70, 75, 73, 60, '65.71', 'Review text 8');