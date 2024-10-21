package com.adp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Lob;
import java.sql.Timestamp;


@Entity
@Table(name = "APPLICATION")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id")
    private Long candidateId;

    @Column(name = "candidate_email", length = 255)
    private String candidateEmail;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "date_applied", nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateApplied;

    @Lob
    @Column(name = "cover_letter")
    private String coverLetter;

    @Lob
    @Column(name = "custom_resume")
    private String customResume;

    @Column(name = "application_status", length = 45)
    private String applicationStatus;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "match_job_description_score")
    private Integer matchJobDescriptionScore;

    @Column(name = "past_experience_score")
    private Integer pastExperienceScore;

    @Column(name = "motivation_score")
    private Integer motivationScore;

    @Column(name = "acedemic_achievement_score")
    private Integer acedemicAchievementScore;

    @Column(name = "pedigree_score")
    private Integer pedigreeScore;

    @Column(name = "trajectory_score")
    private Integer trajectoryScore;

    @Column(name = "extenuating_circumstances_score")
    private Integer extenuatingCircumstancesScore;

    @Lob
    @Column(name = "average_score")
    private String averageScore;

    @Lob
    @Column(name = "review")
    private String review;

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

    public Timestamp getDateApplied() {
        return dateApplied;
    }

    public void setDateApplied(Timestamp dateApplied) {
        this.dateApplied = dateApplied;
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

    public Integer getAcedemicAchievementScore() {
        return acedemicAchievementScore;
    }

    public void setAcedemicAchievementScore(Integer acedemicAchievementScore) {
        this.acedemicAchievementScore = acedemicAchievementScore;
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

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
