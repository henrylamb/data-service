package com.adp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.util.Arrays;
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

    private Job backendEngineer;
    private Job frontendEngineer;
    private Job updatedEngineer;

    @BeforeEach
    void setup() {
        // Creating common Job instances for tests
        backendEngineer = createMockJob(1L, "Engineering", "Backend Developer", "Java Developer",
                "Develop scalable backend services using Java, Spring Boot, and MySQL.",
                "Collaborate with the front-end team to integrate APIs and optimize system performance.", "Open",
                "Mid-level", "Sample Resume for Backend Developer", "Sample Cover Letter for Backend Developer");

        frontendEngineer = createMockJob(2L, "Engineering", "Frontend Developer", "React Developer",
                "Design and develop responsive user interfaces using React, JavaScript, and CSS.",
                "Work with the UX/UI team to create seamless user experiences.", "Open", "Mid-level",
                "Sample Resume for Frontend Developer", "Sample Cover Letter for Frontend Developer");

        updatedEngineer = createMockJob(1L, "UPDATED", "UPDATED", "UPDATED", "UPDATED", "UPDATED", "UPDATED", "UPDATED",
                "UPDATED", "UPDATED");
    }

    private Job createMockJob(Long id, String department, String listingTitle, String jobTitle, String jobDescription,
            String additionalInformation, String listingStatus, String experienceLevel, String modelResume,
            String modelCoverLetter) {
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


    @Test
    @Disabled("Test disabled for pagination.")
    void testPaginationGetJobs() throws Exception {
        List<Job> jobs = List.of(backendEngineer, frontendEngineer); // Mock 2 Job objects as example
        Page<Job> jobPage = new PageImpl<>(jobs, PageRequest.of(0, 20), 2);

        when(jobService.getJobs(0, 20)).thenReturn(jobPage);

        mockMvc.perform(get("/job")
                .param("page", "0")
                .param("items", "20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2)) // Expecting 2 jobs in the content
                .andExpect(jsonPath("$.totalPages").value(1)) // Now expecting 1 total page
                .andExpect(jsonPath("$.totalElements").value(2));

        verify(jobService, times(1)).getJobs(0, 20);
    }

    @Test
    public void testGetAllJobs() throws Exception {
        when(jobService.getAll()).thenReturn(Arrays.asList(backendEngineer, frontendEngineer));

        mockMvc.perform(get("/job"))
        .andExpect(status().isOk())
        // Check that the json response to the API call is as the same as the JSON representation of backendEngineer and frontendEngineer
        .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(backendEngineer, frontendEngineer)))); 
        
    }

    @Test
    // @Disabled
    public void testGetJob() throws Exception {
        when(jobService.getJob(1L)).thenReturn(Optional.of(backendEngineer));

        mockMvc.perform(get("/job/" + backendEngineer.getId()))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(backendEngineer)));
    }

    @Test
    public void testAddJob() throws Exception {
        URI location = new URI("/job/1");
        when(jobService.saveJob(any(Job.class))).thenReturn(location);

        mockMvc.perform(post("/job")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(frontendEngineer)))
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
        when(jobService.getJob(1L)).thenReturn(Optional.of(backendEngineer));
        when(jobService.saveJob(any(Job.class))).thenReturn(null);

        mockMvc.perform(put("/job/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEngineer)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedEngineer)));
    }

    @Test
    public void testUpdateJobNotFound() throws Exception {
        when(jobService.getJob(1L)).thenReturn(Optional.empty()); // simulates the scenario where a job with ID 1 does not exist in the database.

        mockMvc.perform(put("/job/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEngineer)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateJobInvalid() throws Exception {
        when(jobService.getJob(1L)).thenReturn(Optional.of(backendEngineer));

        // Create an invalid job instance directly within the test
        Job invalidJob = new Job();
        invalidJob.setId(1L);
        invalidJob.setDepartment("Engineering");
        invalidJob.setListingTitle("null");

        mockMvc.perform(put("/job/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidJob)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bad Request"));
    }

    @Test
    public void testDeleteExistingJob() throws Exception {
        when(jobService.getJob(1L)).thenReturn(Optional.of(backendEngineer));
        doNothing().when(jobService).delete(backendEngineer);

        mockMvc.perform(delete("/job/1"))
                .andExpect(status().isNoContent());

        verify(jobService, times(1)).delete(backendEngineer);
    }

    @Test
    public void testDeleteJobNotFound() throws Exception {
        when(jobService.getJob(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/job/1"))
                .andExpect(status().isNotFound());
    }
}
