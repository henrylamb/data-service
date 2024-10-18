package com.adp.controller;

import com.adp.domain.Application;
import com.adp.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationControllerTest {

    private final String port = "7000";

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ApplicationService applicationService;

    @Test
    public void deleteApplication_ShouldReturnNoContent() {
        long applicationId = 1L;

        Mockito.doNothing().when(applicationService).getApplication(applicationId);

        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/application/{id}",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class,
                applicationId
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void putApplication_ShouldReturnOk() {
        long applicationId = 1L;
        Application application = new Application();
        application.setId(applicationId);
        application.setCandidateEmail("Test Application");

        HttpEntity<Application> request = new HttpEntity<>(application);

        ResponseEntity<Application> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/application/{id}",
                HttpMethod.PUT,
                request,
                Application.class,
                applicationId
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getApplications_ShouldReturnPageOfApplications() {
        int page = 0;
        int items = 10;
        Pageable pageable = PageRequest.of(page, items);
        Application application = new Application();
        Page<Application> applicationPage = new PageImpl<>(Collections.singletonList(application), pageable, 1);

        Mockito.when(applicationService.repo.findAll(pageable)).thenReturn(applicationPage);

        ResponseEntity<Page> response = restTemplate.getForEntity(
                "/api/application/page?page={page}&items={items}",
                Page.class,
                page,
                items
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getTotalElements()).isEqualTo(1);
    }

}