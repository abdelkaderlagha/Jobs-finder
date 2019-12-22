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

import com.jobsfinder.jobsfinder.dao.JobsRepository;
import com.jobsfinder.jobsfinder.model.Jobs;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(description = "Jobs management")
@RestController
public class JobsController {

	@Autowired
	private JobsRepository jobrepo ;
	
	
	@ApiOperation("Show All jobs")
	@GetMapping(value = "/jobs")
	 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
	public List<Jobs>ShowAllJobs(){
		return jobrepo.findAll();
	}
	
	@ApiOperation("Show job by id {id}")
	@GetMapping(value="/api/auth/jobs/{id}")
	 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
	public Jobs ShowJobById(@PathVariable int id){
		return jobrepo.findById(id);
	}
	
	@ApiOperation("Add new job")
	@PostMapping(value="/api/auth/jobs")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('PM')")
	public ResponseEntity<Void>  AddNewJob(@Valid @RequestBody Jobs job){
		
		Jobs savedJob = jobrepo.save(job);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedJob.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@ApiOperation("Delete all jobs")
	@DeleteMapping(value="/api/auth/jobs")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('PM')")
	public void deleteAllJobs() {
		jobrepo.deleteAll();
	}
	
	@ApiOperation("Delete job by id")
	@DeleteMapping(value="/api/auth/jobs/{id}")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('PM')")
	public Jobs deleteJobById(@PathVariable int id) {
		return jobrepo.deleteById(id);
	}
	@ApiOperation("Search job by category")
	@GetMapping(value = "/api/auth/jobsByName/category/{name}")
	 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
	List<String> SearchJoinByCategory(@PathVariable String name){
		return jobrepo.searchByCategory(name);
	}

	@ApiOperation("Search job by name")
	@GetMapping(value = "/api/auth/jobsByName/company/{name}")
	 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
	List<String> SearchJoinByCompany(@PathVariable String name){
		return jobrepo.searchByCompnayName(name);
	}
	
	@ApiOperation("Update jobs")
	@PostMapping(value="/api/auth/jobs/update/{id}")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('PM')")
	void updateJob(@PathVariable int id_j ,@PathVariable String title, @PathVariable String createdAt, @PathVariable String description, String deadline ) {
		 jobrepo.updateJob(title, createdAt, deadline, description, id_j);
	}

}
