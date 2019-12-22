package com.jobsfinder.jobsfinder.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jobsfinder.jobsfinder.dao.EnrolledRepository;

import com.jobsfinder.jobsfinder.model.Enrolled;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Enrolled manager")
@RestController
public class EnrolledController {

	@Autowired
	private EnrolledRepository enrolled;
	
	@ApiOperation("Add new job")
	@PostMapping("/api/auth/enrolled")
	@PreAuthorize("hasRole('USER') ")
	ResponseEntity<Void> addEnrollement(@Valid @RequestBody Enrolled e) {
		Enrolled saved = enrolled.save(e);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(saved.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@ApiOperation("Show all company")
	@GetMapping(value = "/api/auth/enrolled")
	 @PreAuthorize("hasRole('USER') ")
	List<Enrolled>ShowAllCompanies(){
		return enrolled.findAll();
	}
	
	@ApiOperation("Show company by id")
	@GetMapping(value="/api/auth/enrolled/{id}")
	 @PreAuthorize("hasRole('USER') ")
	Enrolled ShowCompnayById(@PathVariable int id) {
		return enrolled.findById(id);
	}
	
	@ApiOperation("Delete all companies")
	@DeleteMapping(value = "/api/auth/enrolled")
	 @PreAuthorize("hasRole('USER') ")
	void deleteAllCategories() {
		enrolled.deleteAll();
	}
	
	@ApiOperation("Delete company by id")
	@DeleteMapping(value = "/api/auth/enrolled/{id}")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('PM')")
	Enrolled deleteCompanyById(@PathVariable int id) {
		return enrolled.deleteById(id);
	}
	
}
