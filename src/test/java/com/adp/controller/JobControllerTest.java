package com.adp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.adp.domain.Customer;
import com.adp.domain.Job;
import com.adp.service.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = JobController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class JobControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JobService jobService;

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
    Job job1 = createMockJob(1L, "Department 1", "Listing Title 1", "Job Title 1", "Job Description 1", "Additional Information 1", "Listing Status 1", "Experience Level 1", "Model Resume 1", "Model Cover Letter 1");

    Job job2 = createMockJob(2L, "Department 2", "Listing Title 2", "Job Title 2", "Job Description 2", "Additional Information 2", "Listing Status 2", "Experience Level 2", "Model Resume 2", "Model Cover Letter 2");

    // Mock what the service should do 
    when(jobService.getAll())
        .thenReturn(Arrays.asList(job1, job2));

    // Assert
    mockMvc.perform(get("/job"))
        .andExpect(status().isOk())
        .andExpect(content().json("[{'id':1,'department':'Department 1','listingTitle':'Listing Title 1','jobTitle':'Job Title 1','jobDescription':'Job Description 1','additionalInformation':'Additional Information 1','listingStatus':'Listing Status 1','experienceLevel':'Experience Level 1','modelResume':'Model Resume 1','modelCoverLetter':'Model Cover Letter 1'},{'id':2,'department':'Department 2','listingTitle':'Listing Title 2','jobTitle':'Job Title 2','jobDescription':'Job Description 2','additionalInformation':'Additional Information 2','listingStatus':'Listing Status 2','experienceLevel':'Experience Level 2','modelResume':'Model Resume 2','modelCoverLetter':'Model Cover Letter 2'}]"));
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

  // @Test
  // @Disabled
  // public void testPutCustomer() throws Exception {
  //   // Arrange
  //   Customer existingCustomer = new Customer();
  //   existingCustomer.setId(1L);
  //   existingCustomer.setName("John Doe");
  //   existingCustomer.setEmail("john.doe@example.com");
  //   existingCustomer.setPassword("password");

  //   Customer updatedCustomer = new Customer();
  //   updatedCustomer.setId(1L);
  //   updatedCustomer.setName("John Doe Updated");
  //   updatedCustomer.setEmail("john.doe.updated@example.com");
  //   updatedCustomer.setPassword("newpassword");

  //   // Assign
  //   when(customerService.getCustomer(1L)).thenReturn(Optional.of(existingCustomer));
  //   when(customerService.saveCustomer(any(Customer.class))).thenReturn(null);

  //   // Act & Assert
  //   mockMvc.perform(put("/customers/1")
  //       .contentType(MediaType.APPLICATION_JSON)
  //       .content(new ObjectMapper().writeValueAsString(updatedCustomer)))
  //       .andExpect(status().isOk())
  //       .andExpect(content().json(
  //           "{'id':1,'name':'John Doe Updated','email':'john.doe.updated@example.com','password':'newpassword'}"));
  // }

  // @Test
  // @Disabled
  // public void testPutCustomerNotFound() throws Exception {
  //   // Arrange
  //   Customer updatedCustomer = new Customer();
  //   updatedCustomer.setId(1L);
  //   updatedCustomer.setName("John Doe Updated");
  //   updatedCustomer.setEmail("john.doe.updated@example.com");
  //   updatedCustomer.setPassword("newpassword");

  //   // Assign
  //   when(customerService.getCustomer(1L)).thenReturn(Optional.empty());

  //   // Act & Assert
  //   mockMvc.perform(put("/customers/1")
  //       .contentType(MediaType.APPLICATION_JSON)
  //       .content(new ObjectMapper().writeValueAsString(updatedCustomer)))
  //       .andExpect(status().isBadRequest())
  //       .andExpect(content().string("Bad Request"));
  // }

  // @Test
  // @Disabled
  // public void testPutCustomerInvalid() throws Exception {
  //   // Arrange
  //   Customer existingCustomer = new Customer();
  //   existingCustomer.setId(1L);
  //   existingCustomer.setName("John Doe");
  //   existingCustomer.setEmail("john.doe@example.com");
  //   existingCustomer.setPassword("password");

  //   Customer invalidCustomer = new Customer();
  //   invalidCustomer.setId(1L);
  //   invalidCustomer.setName(""); // Invalid name

  //   // Assign
  //   when(customerService.getCustomer(1L)).thenReturn(Optional.of(existingCustomer));

  //   // Act & Assert
  //   mockMvc.perform(put("/customers/1")
  //       .contentType(MediaType.APPLICATION_JSON)
  //       .content(new ObjectMapper().writeValueAsString(invalidCustomer)))
  //       .andExpect(status().isBadRequest())
  //       .andExpect(content().string("Bad Request"));
  // }

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
