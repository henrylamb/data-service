package com.adp.domain;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name="JOB")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id")
    private Long userId;
    private String department;
    @Column(name="user_id")
    private String listingTitle;
    @Column(name="date_listed",nullable = false, updatable = false)
    private Instant dateListed;
    @Column(name="date_closed",nullable = false, updatable = false)
    private Instant dateClosed;
    @Column(name="job_title")
    private String jobTitle;
    @Column(name="job_description")
    private String jobDescription;
    @Column(name="additional_information")
    private String additionalInformation;
    @Column(name="listing_status")
    private String listingStatus;
    @Column(name="experience_level")
    private String experienceLevel;
    @Column(name="model_resume")
    private String modelResume;
    @Column(name="model_cover_letter")
    private String modelCoverLetter;


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public Instant getDateListed() {
        return dateListed;
    }

    public Instant getDateClosed() {
        return dateClosed;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(String listingStatus) {
        this.listingStatus = listingStatus;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getModelResume() {
        return modelResume;
    }

    public void setModelResume(String modelResume) {
        this.modelResume = modelResume;
    }

    public String getModelCoverLetter() {
        return modelCoverLetter;
    }

    public void setModelCoverLetter(String modelCoverLetter) {
        this.modelCoverLetter = modelCoverLetter;
    }

}