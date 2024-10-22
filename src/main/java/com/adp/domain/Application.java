package com.adp.domain;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long candidateId;

    private String candidateEmail;

    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "id", nullable = false)
    private Job job;

    @Column(name = "date_applied")
    private LocalDateTime dateApplied;

    @Lob
    @Column(name = "cover_letter")
    private String coverLetter;

    @Lob
    @Column(name = "custom_resume")
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

    @Lob
    @Column(name = "average_score")
    private String averageScore;

    @Lob
    @Column(name = "review")
    private String review;


    public void statisticsOnly(){
        this.customResume = null;
        this.coverLetter = null;
        this.job = null;
        this.review = null;
    }
}
