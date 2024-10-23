package com.adp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String department;

    @Column(name = "listing_title", length = 100)
    private String listingTitle;

    @Column(name = "date_listed")
    private LocalDateTime dateListed;

    @Column(name = "date_closed")
    private LocalDateTime dateClosed;

    @Column(name = "job_title", length = 45)
    private String jobTitle;

    @Lob
    @Column(name = "job_description")
    private String jobDescription;

    @Lob
    @Column(name = "additional_information")
    private String additionalInformation;

    private String listingStatus;

    @Column(name = "experience_level", length = 100)
    private String experienceLevel;

    @Lob
    @Column(name = "model_resume")
    private String modelResume;

    @Lob
    @Column(name = "model_cover_letter")
    private String modelCoverLetter;

    @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    // @JsonIgnore
    private List<Application> applications; // Establishing one-to-many relationship
}