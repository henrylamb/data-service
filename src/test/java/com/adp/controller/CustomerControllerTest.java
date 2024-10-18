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
import java.util.Arrays;
import java.util.Optional;

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
import com.adp.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerService customerService;

  @Test
  public void testGetAll() throws Exception {
    // Arrange
    Customer customer1 = new Customer();
    customer1.setId(1L);
    customer1.setName("John Doe");
    Customer customer2 = new Customer();
    customer2.setId(2L);
    customer2.setName("Harry Potter");

    // Assign
    when(customerService.getAll())
        .thenReturn(Arrays.asList(customer1, customer2));

    // Assert
    mockMvc.perform(get("/customers"))
        .andExpect(status().isOk())
        .andExpect(content().json("[{'id':1,'name':'John Doe'},{'id':2,'name':'Harry Potter'}]"));
  }

  @Test
  public void testGetCustomer() throws Exception {
    // Arrange
    Customer customer = new Customer();
    customer.setId(1L);
    customer.setName("John Doe");

    // Assign
    when(customerService.getCustomer(1L)).thenReturn(Optional.of(customer));

    // Act & Assert
    mockMvc.perform(get("/customers/1"))
        .andExpect(status().isOk())
        .andExpect(content().json("{'id':1,'name':'John Doe'}"));
  }

  @Test
  public void testAddCustomer() throws Exception {
    // Arrange
    URI location = new URI("/customers/1");

    // Assign
    when(customerService.saveCustomer(any(Customer.class))).thenReturn(location);

    // Act & Assert
    mockMvc.perform(post("/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"John Doe\", \"password\":\"test\", \"email\":\"test@test.com\"}"))
        .andExpect(header().string("Location", "/customers/1"));
  }

  @Test
  public void testAddCustomerInvalid() throws Exception {
    // Arrange
    Customer invalidCustomer = new Customer();
    invalidCustomer.setId(1L);
    invalidCustomer.setName(""); // Invalid name

    // Act & Assert
    mockMvc.perform(post("/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidCustomer)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testPutCustomer() throws Exception {
    // Arrange
    Customer existingCustomer = new Customer();
    existingCustomer.setId(1L);
    existingCustomer.setName("John Doe");
    existingCustomer.setEmail("john.doe@example.com");
    existingCustomer.setPassword("password");

    Customer updatedCustomer = new Customer();
    updatedCustomer.setId(1L);
    updatedCustomer.setName("John Doe Updated");
    updatedCustomer.setEmail("john.doe.updated@example.com");
    updatedCustomer.setPassword("newpassword");

    // Assign
    when(customerService.getCustomer(1L)).thenReturn(Optional.of(existingCustomer));
    when(customerService.saveCustomer(any(Customer.class))).thenReturn(null);

    // Act & Assert
    mockMvc.perform(put("/customers/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updatedCustomer)))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "{'id':1,'name':'John Doe Updated','email':'john.doe.updated@example.com','password':'newpassword'}"));
  }

  @Test
  public void testPutCustomerNotFound() throws Exception {
    // Arrange
    Customer updatedCustomer = new Customer();
    updatedCustomer.setId(1L);
    updatedCustomer.setName("John Doe Updated");
    updatedCustomer.setEmail("john.doe.updated@example.com");
    updatedCustomer.setPassword("newpassword");

    // Assign
    when(customerService.getCustomer(1L)).thenReturn(Optional.empty());

    // Act & Assert
    mockMvc.perform(put("/customers/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updatedCustomer)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Bad Request"));
  }

  @Test
  public void testPutCustomerInvalid() throws Exception {
    // Arrange
    Customer existingCustomer = new Customer();
    existingCustomer.setId(1L);
    existingCustomer.setName("John Doe");
    existingCustomer.setEmail("john.doe@example.com");
    existingCustomer.setPassword("password");

    Customer invalidCustomer = new Customer();
    invalidCustomer.setId(1L);
    invalidCustomer.setName(""); // Invalid name

    // Assign
    when(customerService.getCustomer(1L)).thenReturn(Optional.of(existingCustomer));

    // Act & Assert
    mockMvc.perform(put("/customers/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidCustomer)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Bad Request"));
  }

  @Test
  public void testDeleteCustomer() throws Exception {
    // Arrange
    Customer existingCustomer = new Customer();
    existingCustomer.setId(1L);
    existingCustomer.setName("John Doe");
    existingCustomer.setEmail("john.doe@example.com");
    existingCustomer.setPassword("password");

    // Assign
    when(customerService.getCustomer(1L)).thenReturn(Optional.of(existingCustomer));

    // Act & Assert
    mockMvc.perform(delete("/customers/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testDeleteCustomerNotFound() throws Exception {
    // Assign
    when(customerService.getCustomer(1L)).thenReturn(Optional.empty());

    // Act & Assert
    mockMvc.perform(delete("/customers/1"))
        .andExpect(status().isBadRequest());
  }
}
