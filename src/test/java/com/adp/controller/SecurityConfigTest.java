package com.adp.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.adp.config.SecurityConfig;
import com.adp.domain.Job;
import com.adp.service.JobService;

@WebMvcTest(JobController.class)
@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @BeforeEach
    public void setup(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testGetJob() throws Exception {
        long jobId = 1L;
        Job job = new Job();
        job.setId(jobId);
        job.setListingTitle("Software Engineer");

        Mockito.when(jobService.getJob(jobId)).thenReturn(Optional.of(job));

        mockMvc.perform(get("/job/{id}", jobId)
                .with(jwt().jwt(jwt -> jwt.claim("role", "CANDIDATE")))) // This creates a JWT with the role "CANDIDATE"
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) jobId)))
                .andExpect(jsonPath("$.listingTitle", is("Software Engineer")));
    }
}