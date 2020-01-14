package com.jobsfinder.jobsfinder.controller;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobsfinder.jobsfinder.dao.CompanyRepository;
import com.jobsfinder.jobsfinder.model.Company;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "Company management")
@RequestMapping(value="/rest/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {
	
	@Autowired
	private CompanyRepository companyrepo;
	
	@ApiOperation("Find all companies")
	@GetMapping(value = "/company")
	List<Company>findAllCompanies(){
		return companyrepo.findAll();
	}
	
	@ApiOperation("Find companies by id")
	@GetMapping(value = "/company/{id}")
	public ResponseEntity<Company>findCompanyById(@Valid @PathVariable int id)throws Exception{
		Company c = companyrepo.findById(id).orElseThrow(()-> new Exception("company 404"));
		return ResponseEntity.ok().body(c);
	}
	
	@ApiOperation("Add new company")
	@PostMapping(value = "/company")
	@PreAuthorize("hasRole('ROLE_ADMIN')and hasRole('ROLE_PM')")
	public @Valid Company addNewCompany(@Valid @RequestBody Company c){
		return companyrepo.save(c);
	}
	
	@ApiOperation("Delete company by ID")
	@PreAuthorize("hasRole('ROLE_ADMIN')and hasRole('ROLE_PM')")
	@DeleteMapping(value = "/company")
	public String DeleteComany(@Valid @PathVariable int id)throws Exception {
		Company c = companyrepo.findById(id).orElseThrow(()->new Exception("Company 404"));
		companyrepo.delete(c);
		return "Company deleted!";
	}
	
	@ApiOperation("Update company by id")
	@PutMapping(value = "/company/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')and hasRole('ROLE_PM')")
	public ResponseEntity<Company>updateCompany(@Valid @PathVariable int id, @RequestBody Company newC) throws Exception{
		Company c = companyrepo.findById(id).orElseThrow(()->new Exception("company 404"));
		c.setImage(newC.getImage());
		c.setLocal(newC.getLocal());
		c.setName(newC.getName());
		
		Company updatedC = companyrepo.save(c);
		return ResponseEntity.ok(updatedC);
	}
	
	
	
}
