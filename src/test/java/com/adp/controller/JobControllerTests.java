package com.adp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

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

@WebMvcTest(controllers = JobController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class JobControllerTests {

     @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JobService jobService;

    @Test
  public void testPutJob() throws Exception {
    // Arrange
    Job existingJob = new Job();
    existingJob.setId(1L);
    existingJob.setJobTitle("Developer");
    existingJob.setJobDescription("Develops software applications");

    Job updateJob = new Job();
    updateJob.setId(1L);
    updateJob.setJobTitle("Senior Developer");
    updateJob.setJobDescription("Develops and oversees software applications");

    // Assign
    when(jobService.getJob(1L)).thenReturn(Optional.of(existingJob));
    when(jobService.saveJob(any(Job.class))).thenReturn(null);

    // Act & Assert
    mockMvc.perform(put("/job/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updateJob)))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "{'id':1,'jobTitle':'Senior Developer','jobDescription':'Develops and oversees software applications'}"));
}

  @Test
  public void testPutJobNotFound() throws Exception {
    // Arrange
    Job updateJob = new Job();
    updateJob.setId(1L);
    updateJob.setJobTitle("Senior Developer");
    updateJob.setJobDescription("Develops and oversees software applications");

    // Assign
    when(jobService.getJob(1L)).thenReturn(Optional.empty());

    // Act & Assert
    mockMvc.perform(put("/job/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updateJob)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Bad Request"));
  }

  @Test
  public void testPutJobInvalid() throws Exception {
    // Arrange
    Job existingJob = new Job();
    existingJob.setId(1L);
    existingJob.setJobTitle("Developer");
    existingJob.setJobDescription("Develops software applications");

    Job invalidJob = new Job();
    invalidJob.setId(1L);
    invalidJob.setJobTitle(""); // Invalid name

    // Assign
    when(jobService.getJob(1L)).thenReturn(Optional.of(existingJob));

    // Act & Assert
    mockMvc.perform(put("/job/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidJob)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Bad Request"));
  }
}
