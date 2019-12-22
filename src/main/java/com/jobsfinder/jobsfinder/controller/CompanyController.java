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
	@GetMapping(value = "/api/auth/company")
	 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
	List<Company>ShowAllCompanies(){
		return companyrepo.findAll();
	}
	
	@ApiOperation("Show company by id")
	@GetMapping(value="/api/auth/company/{id}")
	 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
	Company ShowCompnayById(@PathVariable int id) {
		return companyrepo.findById(id);
	}
	
	@ApiOperation("Delete all companies")
	@DeleteMapping(value = "/api/auth/company")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('PM')")
	void deleteAllCategories() {
		companyrepo.deleteAll();
	}
	
	@ApiOperation("Delete company by id")
	@DeleteMapping(value = "/api/auth/company/{id}")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('PM')")
	Company deleteCompanyById(@PathVariable int id) {
		return companyrepo.deleteById(id);
	}
	@ApiOperation("Add new company")
	@PostMapping(value="/api/auth/company")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('PM')")
	public ResponseEntity<Void>  AddNewCompany(@Valid @RequestBody Company company){
		
		Company savedCompany = companyrepo.save(company);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCompany.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	@ApiOperation("Update company")
	@PostMapping(value = "/api/auth/company/update/{id}")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('PM')")
	void updateCategory(@PathVariable String name , @PathVariable String local) {
		companyrepo.updateCompany(name, local);
	}

}
