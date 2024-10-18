package com.adp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @TestPropertySource(properties = { "rsa.private-key=classpath:keys/testPrivate.pem", "rsa.public-key=classpath:keys/testPublic.pem" } )
@SuppressWarnings("rawtypes")
public class CustomerControllerSecurityTest {
  @Autowired TestRestTemplate template;

  @Test
  void getWhenUnauthenticated(){

    RequestEntity request = RequestEntity.get("/customers").build();
    
    ResponseEntity<String> responseEntity = template.exchange(request, String.class);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  // @Test
  // void getWhenAuthenticated() {
  //     // Arrange
  //     String validJwt = "your-valid-jwt-token"; // Replace with a method to generate or retrieve a valid JWT
  //     HttpHeaders headers = new HttpHeaders();
  //     headers.setBearerAuth(validJwt);

  //     RequestEntity<Void> request = RequestEntity.get("/customers")
  //         .headers(headers)
  //         .build();

  //     // Act
  //     ResponseEntity<String> responseEntity = template.exchange(request, String.class);

  //     // Assert
  //     assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
  //     // Add more assertions as needed to verify the response content
  // }
  
}
