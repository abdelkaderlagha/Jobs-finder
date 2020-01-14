package com.jobsfinder.jobsfinder.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jobsfinder.jobsfinder.dao.JobsRepository;
import com.jobsfinder.jobsfinder.model.Jobs;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@RestController
@Api(description = "Jobs management")
@RequestMapping(value="/rest/api")
@CrossOrigin(origins = "http://localhost:4200")
public class JobsController {

	@Autowired
	private JobsRepository jobrepo ;
	
	@ApiOperation("Show all jobs")
	@GetMapping(value = "/jobs")
	List<Jobs>showAllJobs(){
		return jobrepo.findAll();
	}
	
	@ApiOperation("Show jobs by id")
	@GetMapping(value = "/jobs/{id}")
	public ResponseEntity<Jobs>ShowJobsById(@Valid @PathVariable int id) throws Exception{
		Jobs job= jobrepo.findById(id).orElseThrow(()-> new Exception("Jobs 404"));
		return ResponseEntity.ok().body(job);
	}
	
	@ApiOperation("Add new job")
	@PreAuthorize("hasRole('ROLE_ADMIN')and hasRole('ROLE_PM')")
	@PostMapping(value = "/jobs")
	Jobs addNewJob(@Valid @RequestBody Jobs job) {
		return jobrepo.save(job);
	}
	
	@ApiOperation("Update job")
	@PreAuthorize("hasRole('ROLE_ADMIN')and HasRole('ROLE_PM') ")
	@PostMapping(value = "/jobs/{id}")
	public ResponseEntity<Jobs> updateJob(@Valid @PathVariable int id , @RequestBody Jobs newJ) throws Exception{
		Jobs j = jobrepo.findById(id).orElseThrow(()-> new Exception("Jobs 404"));
		j.setCategory(newJ.getCategory());
		j.setCompany(newJ.getCompany());
		j.setCreated_at(newJ.getCreated_at());
		j.setDeadline(newJ.getDeadline());
		j.setDescription(newJ.getDescription());
		j.setTitle(newJ.getTitle());
		
		Jobs updatedJob = jobrepo.save(j);
		return ResponseEntity.ok(updatedJob);
	}
	
	@ApiOperation("Delete job")
	@PreAuthorize("hasRole('ROLE_ADMIN')and HasRole('ROLE_PM') ")
	@DeleteMapping(value = "/jobs/{id}")
	public String deleteJob(@Valid @PathVariable int id) throws Exception{
		Jobs j = jobrepo.findById(id).orElseThrow(()-> new Exception("Jobs 404"));
		jobrepo.delete(j);
		return "Job deleted!";
	}
	
	}
