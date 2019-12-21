package com.jobsfinder.jobsfinder.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jobsfinder.jobsfinder.dao.CompanyRepository;

import com.jobsfinder.jobsfinder.model.Company;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Company management")
@RestController
public class CompanyController {
	
	@Autowired
	private CompanyRepository companyrepo;
	
	@ApiOperation("Show all company")
	@GetMapping(value = "/company")
	List<Company>ShowAllCompanies(){
		return companyrepo.findAll();
	}
	
	@ApiOperation("Show company by id")
	@GetMapping(value="/company/{id}")
	Company ShowCompnayById(@PathVariable int id) {
		return companyrepo.findById(id);
	}
	
	@ApiOperation("Delete all companies")
	@DeleteMapping(value = "/company")
	void deleteAllCategories() {
		companyrepo.deleteAll();
	}
	
	@ApiOperation("Delete company by id")
	@DeleteMapping(value = "/company/{id}")
	Company deleteCompanyById(@PathVariable int id) {
		return companyrepo.deleteById(id);
	}
	@ApiOperation("Add new company")
	@PostMapping(value="/company")
	public ResponseEntity<Void>  AddNewCompany(@Valid @RequestBody Company company){
		
		Company savedCompany = companyrepo.save(company);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCompany.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	@ApiOperation("Update company")
	@PostMapping(value = "/company/update/{id}")
	void updateCategory(@PathVariable String name , @PathVariable String local) {
		companyrepo.updateCompany(name, local);
	}

}
