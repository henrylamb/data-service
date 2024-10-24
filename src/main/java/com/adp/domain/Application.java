package com.adp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "APPLICATION")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id")
    private Long candidateId;

    private String candidateEmail;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Job job;

    @Column(name = "date_applied", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateApplied;

    @Lob
    @Column(name = "cover_letter", columnDefinition = "LONGTEXT")
    private String coverLetter;

    @Lob
    @Column(name = "custom_resume", columnDefinition = "LONGTEXT")
    private String customResume;

    private String applicationStatus;

    private Integer yearsOfExperience;

    private Integer matchJobDescriptionScore;

    private Integer pastExperienceScore;

    private Integer motivationScore;

    private Integer academicAchievementScore;

    private Integer pedigreeScore;

    private Integer trajectoryScore;

    private Integer extenuatingCircumstancesScore;

    private Integer averageScore;

    @Lob
    @Column(name = "review", columnDefinition = "LONGTEXT")
    private String review;

    public Job getJob() {
        return job;
    }

    public void statisticsOnly(){
        this.customResume = null;
        this.coverLetter = null;
        this.job = null;
        this.review = null;
    }

    public void setScores(Application application){
        this.averageScore = application.getAverageScore();
        this.academicAchievementScore = application.getAcademicAchievementScore();
        this.extenuatingCircumstancesScore = application.getExtenuatingCircumstancesScore();
        this.matchJobDescriptionScore = application.getMatchJobDescriptionScore();
        this.pastExperienceScore = application.getPastExperienceScore();
        this.pedigreeScore = application.getPedigreeScore();
        this.trajectoryScore = application.getTrajectoryScore();
        this.yearsOfExperience = application.getYearsOfExperience();
        this.review = application.getReview();
    }

    public Job getJob() {
        return this.job;
    }
}
