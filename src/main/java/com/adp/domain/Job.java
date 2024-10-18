package com.adp.domain;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;

public class Job {
    private Long id;
    private Long userId;
    private String department;
    private String listingTitle;
    private Instant dateListed;
    private Instant dateClosed;
    private String jobTitle;
    private String jobDescription;
    private String additionalInformation;
    private String listingStatus;
    private String experienceLevel;
    private String modelResume;
    private String modelCoverLetter;

    // Constructor
    public Job() {}

    public Job(int id, Integer userId, String department, String listingTitle, Timestamp dateListed,
               Timestamp dateClosed, String jobTitle, String jobDescription,
               String additionalInformation, String listingStatus, String experienceLevel,
               String modelResume, String modelCoverLetter) {
        this.id = id;
        this.userId = userId;
        this.department = department;
        this.listingTitle = listingTitle;
        this.dateListed = dateListed;
        this.dateClosed = dateClosed;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.additionalInformation = additionalInformation;
        this.listingStatus = listingStatus;
        this.experienceLevel = experienceLevel;
        this.modelResume = modelResume;
        this.modelCoverLetter = modelCoverLetter;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    public Timestamp getDateListed() {
        return dateListed;
    }

    public void setDateListed(Timestamp dateListed) {
        this.dateListed = dateListed;
    }

    public Timestamp getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Timestamp dateClosed) {
        this.dateClosed = dateClosed;
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