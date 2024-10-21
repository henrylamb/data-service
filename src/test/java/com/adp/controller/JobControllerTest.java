package com.adp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.adp.domain.Application;
import com.adp.domain.Job;
import com.adp.service.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@WebMvcTest(controllers = JobController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Job job1;
    private Job job2;
    private Job updatedJob;

    @BeforeEach
    void setup() {
        // Creating common Job instances for tests
        job1 = createJob(1L, "Engineering", "Backend Developer", "Java Developer",
                "Develop scalable backend services using Java, Spring Boot, and MySQL.",
                "Collaborate with the front-end team to integrate APIs and optimize system performance.", "Open",
                "Mid-level", "Sample Resume for Backend Developer", "Sample Cover Letter for Backend Developer");

        job2 = createJob(2L, "Engineering", "Frontend Developer", "React Developer",
                "Design and develop responsive user interfaces using React, JavaScript, and CSS.",
                "Work with the UX/UI team to create seamless user experiences.", "Open", "Mid-level",
                "Sample Resume for Frontend Developer", "Sample Cover Letter for Frontend Developer");

        updatedJob = createJob(1L, "UPDATED", "UPDATED", "UPDATED", "UPDATED", "UPDATED", "UPDATED", "UPDATED",
                "UPDATED", "UPDATED");
    }

        
    private Job createJob(Long id, String department, String listingTitle, String jobTitle, String jobDescription, String additionalInformation, String listingStatus, String experienceLevel, String modelResume, String modelCoverLetter) {
        Job job = new Job();
        job.setId(id);
        job.setDepartment(department);
        job.setListingTitle(listingTitle);
        job.setJobTitle(jobTitle);
        job.setJobDescription(jobDescription);
        job.setAdditionalInformation(additionalInformation);
        job.setListingStatus(listingStatus);
        job.setExperienceLevel(experienceLevel);
        job.setModelResume(modelResume);
        job.setModelCoverLetter(modelCoverLetter);
        return job;
    }

    private Application createApplication(Long id, Long candidateId, String candidateEmail, Job job, String coverLetter, String customResume) {
        Application application = new Application();
        application.setId(id);
        application.setCandidateId(candidateId);
        application.setCandidateEmail(candidateEmail);
        application.setJob(job);
        application.setCoverLetter(coverLetter);
        application.setCustomResume(customResume);
        return application;
    }


    @Test
    @Disabled
    void testGetPaginatedJobs() throws Exception {
        // Arrange
        List<Job> jobs = List.of(job1, job2);  // Mock 2 Job objects as example
        Page<Job> jobPage = new PageImpl<>(jobs, PageRequest.of(0, 20), 2);

        // Assign
        when(jobService.getPaginatedJobs(0, 20)).thenReturn(jobPage);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/job?page=0&items=20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))  // Expecting 2 jobs in the content
                .andExpect(jsonPath("$.totalPages").value(1))  // Now expecting 1 total page
                .andExpect(jsonPath("$.totalElements").value(2));

        // Verifying the service method was called exactly once
        verify(jobService, times(1)).getPaginatedJobs(0, 20);
    }

    @Test
    @Disabled
    void testGetApplications() throws Exception {
        // Arrange

        Application application1 = createApplication(1L, 1L, "candidate1@example.com", job1, "Cover Letter 1", "Custom Resume 1");
        Application application2 = createApplication(2L, 2L, "candidate2@example.com", job1, "Cover Letter 2", "Custom Resume 2");

        job1.setApplications(List.of(application1, application2));

        List<Application> applications = Arrays.asList(application1, application2);

        // Assign
        when(jobService.getApplicationsOfGivenJobId(1L)).thenReturn(applications);

        // Act & Assert
        mockMvc.perform(get("/job/1/applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].candidateId").value(1L))
                .andExpect(jsonPath("$[0].candidateEmail").value("candidate1@example.com"))
                .andExpect(jsonPath("$[0].jobId").value(1L))
                .andExpect(jsonPath("$[0].coverLetter").value("Cover Letter 1"))
                .andExpect(jsonPath("$[0].customResume").value("Custom Resume 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].candidateId").value(2L))
                .andExpect(jsonPath("$[1].candidateEmail").value("candidate2@example.com"))
                .andExpect(jsonPath("$[1].jobId").value(1L))
                .andExpect(jsonPath("$[1].coverLetter").value("Cover Letter 2"))
                .andExpect(jsonPath("$[1].customResume").value("Custom Resume 2"));

        // Verifying the service method was called exactly once
        verify(jobService, times(1)).getApplicationsOfGivenJobId(1L);
    }

    @Test
    void testGetApplicationsNotFound() throws Exception {
        // Arrange
        Long jobId = 1L;

        // Assign
        when(jobService.getApplicationsOfGivenJobId(jobId)).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/job/{id}/applications", jobId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No applications found for the given job ID"));

        // Verifying the service method was called exactly once
        verify(jobService, times(1)).getApplicationsOfGivenJobId(jobId);
    }
    // TODO figure out how to deal with the date issue


    @Test
    @Disabled("Test disabled for pagination.")
    void testPaginationGetJobs() throws Exception {
        List<Job> jobs = List.of(job1, job2); // Mock 2 Job objects as example
        Page<Job> jobPage = new PageImpl<>(jobs, PageRequest.of(0, 20), 2);

        when(jobService.getPaginatedJobs(0, 20)).thenReturn(jobPage);

        mockMvc.perform(get("/job")
                .param("page", "0")
                .param("items", "20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2)) // Expecting 2 jobs in the content
                .andExpect(jsonPath("$.totalPages").value(1)) // Now expecting 1 total page
                .andExpect(jsonPath("$.totalElements").value(2));

        verify(jobService, times(1)).getPaginatedJobs(0, 20);
    }

    @Test
    public void testGetAllJobs() throws Exception {
        when(jobService.getAll()).thenReturn(Arrays.asList(job1, job2));

        mockMvc.perform(get("/job"))
        .andExpect(status().isOk())
        // Check that the json response to the API call is as the same as the JSON representation of backendEngineer and frontendEngineer
        .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(job1, job2)))); 
        
    }

    @Test
    // @Disabled
    public void testGetJob() throws Exception {
        when(jobService.getJob(1L)).thenReturn(Optional.of(job1));

        mockMvc.perform(get("/job/" + job1.getId()))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(job1)));
    }

    @Test
    public void testAddJob() throws Exception {
        URI location = new URI("/job/1");
        when(jobService.saveJob(any(Job.class))).thenReturn(location);

        mockMvc.perform(post("/job")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(job1)))
                .andExpect(header().string("Location", "/job/1"));
    }

    @Test
    public void testAddJobInvalid() throws Exception {
        // Create an invalid job instance directly within the test
        Job invalidJob = new Job();
        invalidJob.setId(1L);
        invalidJob.setDepartment("Engineering");
        invalidJob.setListingTitle("null");

        mockMvc.perform(post("/job")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidJob)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateJob() throws Exception {
        when(jobService.getJob(1L)).thenReturn(Optional.of(job1));
        when(jobService.saveJob(any(Job.class))).thenReturn(null);

        mockMvc.perform(put("/job/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedJob)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedJob)));
    }

    @Test
    public void testUpdateJobNotFound() throws Exception {
        when(jobService.getJob(1L)).thenReturn(Optional.empty()); // simulates the scenario where a job with ID 1 does not exist in the database.

        mockMvc.perform(put("/job/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedJob)))
                //updating the line
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateJobInvalid() throws Exception {
        when(jobService.getJob(1L)).thenReturn(Optional.of(job1));

        // Create an invalid job instance directly within the test
        Job invalidJob = new Job();
        invalidJob.setId(1L);
        invalidJob.setDepartment("Engineering");
        invalidJob.setListingTitle("null");

        mockMvc.perform(put("/job/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidJob)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteExistingJob() throws Exception {
        when(jobService.getJob(1L)).thenReturn(Optional.of(job1));
        doNothing().when(jobService).delete(job1);

        mockMvc.perform(delete("/job/1"))
                .andExpect(status().isNoContent());

        verify(jobService, times(1)).delete(job1);
    }

    @Test
    public void testDeleteJobNotFound() throws Exception {
        when(jobService.getJob(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/job/1"))
                .andExpect(status().isNotFound());
    }
}
