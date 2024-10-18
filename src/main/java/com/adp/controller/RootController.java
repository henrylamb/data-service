package com.adp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {
  @GetMapping
  public ResponseEntity<?> getRoot(){
    return ResponseEntity.ok("Running all good");
  }
}
