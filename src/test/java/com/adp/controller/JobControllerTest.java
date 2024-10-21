package com.adp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.time.Instant;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.adp.domain.Customer;
import com.adp.domain.Job;
import com.adp.service.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = JobController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class JobControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    private Page<Job> jobPage;

    @BeforeEach
    void setup() {
    }

    @Test
    void testGetJobs() throws Exception {
        // Arrange
        List<Job> jobs = List.of(new Job(), new Job());  // Mock 2 Job objects as example
        jobPage = new PageImpl<>(jobs, PageRequest.of(0, 20), 2);

        // Assign
        when(jobService.getJobs(0, 20)).thenReturn(jobPage);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/job/")
                        .param("page", "0")
                        .param("items", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))  // Expecting 2 jobs in the content
                .andExpect(jsonPath("$.totalPages").value(1))  // Now expecting 1 total page
                .andExpect(jsonPath("$.totalElements").value(2));

        // Verifying the service method was called exactly once
        verify(jobService, times(1)).getJobs(0, 20);
    }

    // TODO figure out how to deal with the date issue
    private Job createMockJob(Long id, String department, String listingTitle, String jobTitle, String jobDescription, String additionalInformation, String listingStatus, String experienceLevel, String modelResume, String modelCoverLetter) {
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
    public void testGetAll() throws Exception {
        // Arrange
        // Create two jobs
        Job job1 = createMockJob(1L, "Engineering", "Frontend Developer", "React Developer", "Design and develop responsive user interfaces using React, JavaScript, and CSS.", "Work with the UX/UI team to create seamless user experiences.", "Open", "Mid-level", "Sample Resume for Frontend Developer", "Sample Cover Letter for Frontend Developer");

        Job job2 = createMockJob(2L, "Engineering", "Backend Developer", "Java Developer", "Develop scalable backend services using Java, Spring Boot, and MySQL.", "Collaborate with the front-end team to integrate APIs and optimize system performance.", "Open", "Mid-level", "Sample Resume for Backend Developer", "Sample Cover Letter for Backend Developer");

        // Mock what the service should do
        when(jobService.getAll())
                .thenReturn(Arrays.asList(job1, job2));

        // Assert
        mockMvc.perform(get("/job"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'department':'Engineering','listingTitle':'Frontend Developer','jobTitle':'React Developer','jobDescription':'Design and develop responsive user interfaces using React, JavaScript, and CSS.','additionalInformation':'Work with the UX/UI team to create seamless user experiences.','listingStatus':'Open','experienceLevel':'Mid-level','modelResume':'Sample Resume for Frontend Developer','modelCoverLetter':'Sample Cover Letter for Frontend Developer'}," + "{'id':2,'department':'Engineering','listingTitle':'Backend Developer','jobTitle':'Java Developer','jobDescription':'Develop scalable backend services using Java, Spring Boot, and MySQL.','additionalInformation':'Collaborate with the front-end team to integrate APIs and optimize system performance.','listingStatus':'Open','experienceLevel':'Mid-level','modelResume':'Sample Resume for Backend Developer','modelCoverLetter':'Sample Cover Letter for Backend Developer'}]")
                );
    }

    // @Test
    // @Disabled
    // public void testGetCustomer() throws Exception {
    //   // Arrange
    //   Customer customer = new Customer();
    //   customer.setId(1L);
    //   customer.setName("John Doe");

    //   // Assign
    //   when(customerService.getCustomer(1L)).thenReturn(Optional.of(customer));

    //   // Act & Assert
    //   mockMvc.perform(get("/customers/1"))
    //       .andExpect(status().isOk())
    //       .andExpect(content().json("{'id':1,'name':'John Doe'}"));
    // }

    // @Test
    // @Disabled
    // public void testAddCustomer() throws Exception {
    //   // Arrange
    //   URI location = new URI("/customers/1");

    //   // Assign
    //   when(customerService.saveCustomer(any(Customer.class))).thenReturn(location);

    //   // Act & Assert
    //   mockMvc.perform(post("/customers")
    //       .contentType(MediaType.APPLICATION_JSON)
    //       .content("{\"name\":\"John Doe\", \"password\":\"test\", \"email\":\"test@test.com\"}"))
    //       .andExpect(header().string("Location", "/customers/1"));
    // }

    // @Test
    // @Disabled
    // public void testAddJobInvalid() throws Exception {
    //   // Arrange
    //   Customer invalidCustomer = new Customer();
    //   invalidCustomer.setId(1L);
    //   invalidCustomer.setName(""); // Invalid name

    //   // Act & Assert
    //   mockMvc.perform(post("/job")
    //       .contentType(MediaType.APPLICATION_JSON)
    //       .content(new ObjectMapper().writeValueAsString(invalidCustomer)))
    //       .andExpect(status().isBadRequest());
    // }

  @Test
  public void testUpdateJob() throws Exception {
    // Arrange
    Job existingJob = createMockJob(1L, "Department 1", "Listing Title 1", "Job Title 1", "Job Description 1", "Additional Information 1", "Listing Status 1", "Experience Level 1", "Model Resume 1", "Model Cover Letter 1");

    Job updateJob = createMockJob(1L, "Department Updated", "Listing Title Updated", "Job Title Updated", "Job Description Updated", "Additional Information Updated", "Listing Status Updated", "Experience Level Updated", "Model Resume Updated", "Model Cover Letter Updated");

    // Assign
    when(jobService.getJob(1L)).thenReturn(Optional.of(existingJob));
    when(jobService.saveJob(any(Job.class))).thenReturn(null);

    // Act & Assert
    mockMvc.perform(put("/job/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updateJob)))
        .andExpect(status().isOk())
        .andExpect(content().json(
             "{'id':1,'department':'Department Updated','listingTitle':'Listing Title Updated','jobTitle':'Job Title Updated','jobDescription':'Job Description Updated','additionalInformation':'Additional Information Updated','listingStatus':'Listing Status Updated','experienceLevel':'Experience Level Updated','modelResume':'Model Resume Updated','modelCoverLetter':'Model Cover Letter Updated'}"));
}

@Test
public void testUpdateJobNotFound() throws Exception {
  // Arrange
  Job updatedJob = createMockJob(1L, "Department Updated", "Listing Title Updated", "Job Title Updated", "Job Description Updated", "Additional Information Updated", "Listing Status Updated", "Experience Level Updated", "Model Resume Updated", "Model Cover Letter Updated");

  // Assign
  when(jobService.getJob(1L)).thenReturn(Optional.empty());

  // Act & Assert
  mockMvc.perform(put("/job/1")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(updatedJob)))
      .andExpect(status().isBadRequest())
      .andExpect(content().string("Bad Request"));
}

@Test
public void testUpdateJobInvalid() throws Exception {
  // Arrange
  Job existingJob = createMockJob(1L, "Department 1", "Listing Title 1", "Job Title 1", "Job Description 1", "Additional Information 1", "Listing Status 1", "Experience Level 1", "Model Resume 1", "Model Cover Letter 1");

  // Invalid job with an empty jobTitle
  Job invalidJob = new Job();
  invalidJob.setId(1L);
  invalidJob.setDepartment("Department 1");
  invalidJob.setListingTitle("null");

  // Assign
  when(jobService.getJob(1L)).thenReturn(Optional.of(existingJob));

  // Act & Assert
  mockMvc.perform(put("/job/1")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(invalidJob)))
      .andExpect(status().isBadRequest())
      .andExpect(content().string("Bad Request"));
}

    // @Test
    // @Disabled
    // public void testDeleteCustomer() throws Exception {
    //   // Arrange
    //   Customer existingCustomer = new Customer();
    //   existingCustomer.setId(1L);
    //   existingCustomer.setName("John Doe");
    //   existingCustomer.setEmail("john.doe@example.com");
    //   existingCustomer.setPassword("password");

    //   // Assign
    //   when(customerService.getCustomer(1L)).thenReturn(Optional.of(existingCustomer));

    //   // Act & Assert
    //   mockMvc.perform(delete("/customers/1"))
    //       .andExpect(status().isNotFound());
    // }

    // @Test
    // @Disabled
    // public void testDeleteCustomerNotFound() throws Exception {
    //   // Assign
    //   when(customerService.getCustomer(1L)).thenReturn(Optional.empty());

    //   // Act & Assert
    //   mockMvc.perform(delete("/customers/1"))
    //       .andExpect(status().isBadRequest());
    // }
}