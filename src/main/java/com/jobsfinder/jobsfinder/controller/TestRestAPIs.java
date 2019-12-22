package com.jobsfinder.jobsfinder.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class TestRestAPIs {
  
  @GetMapping("/api/auth/user")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
  public String userAccess() {
    return ">>> User Contents!";
  }
  
  @GetMapping("/api/auth/pm")
  @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
  public String projectManagementAccess() {
    return ">>> Board Management Project";
  }
  
  @GetMapping("/api/auth/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return ">>> Admin Contents";
  }
}