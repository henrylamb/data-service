package com.adp.request;

import com.adp.domain.Application;

public class ApplicationRequest {
    private Long candidateId;
    private String candidateEmail;
    private Long jobId; // Field to capture the job ID from the client request
    private String coverLetter;
    private String customResume;
    private String applicationStatus;
    private Integer yearsOfExperience;

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getCustomResume() {
        return customResume;
    }

    public void setCustomResume(String customResume) {
        this.customResume = customResume;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Integer getMatchJobDescriptionScore() {
        return matchJobDescriptionScore;
    }

    public void setMatchJobDescriptionScore(Integer matchJobDescriptionScore) {
        this.matchJobDescriptionScore = matchJobDescriptionScore;
    }

    public Integer getPastExperienceScore() {
        return pastExperienceScore;
    }

    public void setPastExperienceScore(Integer pastExperienceScore) {
        this.pastExperienceScore = pastExperienceScore;
    }

    public Integer getMotivationScore() {
        return motivationScore;
    }

    public void setMotivationScore(Integer motivationScore) {
        this.motivationScore = motivationScore;
    }

    public Integer getAcademicAchievementScore() {
        return academicAchievementScore;
    }

    public void setAcademicAchievementScore(Integer academicAchievementScore) {
        this.academicAchievementScore = academicAchievementScore;
    }

    public Integer getPedigreeScore() {
        return pedigreeScore;
    }

    public void setPedigreeScore(Integer pedigreeScore) {
        this.pedigreeScore = pedigreeScore;
    }

    public Integer getTrajectoryScore() {
        return trajectoryScore;
    }

    public void setTrajectoryScore(Integer trajectoryScore) {
        this.trajectoryScore = trajectoryScore;
    }

    public Integer getExtenuatingCircumstancesScore() {
        return extenuatingCircumstancesScore;
    }

    public void setExtenuatingCircumstancesScore(Integer extenuatingCircumstancesScore) {
        this.extenuatingCircumstancesScore = extenuatingCircumstancesScore;
    }

    public Integer getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    private Integer matchJobDescriptionScore;
    private Integer pastExperienceScore;
    private Integer motivationScore;
    private Integer academicAchievementScore;
    private Integer pedigreeScore;
    private Integer trajectoryScore;
    private Integer extenuatingCircumstancesScore;
    private Integer averageScore;
    private String review;

    // Getters and Setters

    public Application convertToApplication() {
        Application application = new Application();
        application.setCandidateId(this.candidateId);
        application.setCandidateEmail(this.candidateEmail);
        application.setCoverLetter(this.coverLetter);
        application.setCustomResume(this.customResume);
        application.setApplicationStatus(this.applicationStatus);
        application.setYearsOfExperience(this.yearsOfExperience);
        application.setMatchJobDescriptionScore(this.matchJobDescriptionScore);
        application.setPastExperienceScore(this.pastExperienceScore);
        application.setMotivationScore(this.motivationScore);
        application.setAcademicAchievementScore(this.academicAchievementScore);
        application.setPedigreeScore(this.pedigreeScore);
        application.setTrajectoryScore(this.trajectoryScore);
        application.setExtenuatingCircumstancesScore(this.extenuatingCircumstancesScore);
        application.setAverageScore(this.averageScore);
        application.setReview(this.review);
        return application;
    }
}
