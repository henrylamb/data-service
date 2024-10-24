package com.adp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManagerStatistics {
  
 private Integer totalJobs; // Total number of jobs under this manager
 private Integer openJobs; // Number of jobs with status "open"
 private Integer closedJobs; // Number of jobs with status "closed"
 private Integer totalApplications; // Total number of applications across all manager's jobs
 private Integer pendingApplications; // Applications with status "pending"
 private Integer reviewedApplications; // Applications with status "reviewed"
 private Integer acceptedApplications; // Applications with status "accepted"
 private Integer rejectedApplications; // Applications with status "rejected"
 private Integer pendingReviews; // Same as pendingApplications (applications needing review)
 private Integer openPositions; // Same as openJobs (positions still needing to be filled)
}
