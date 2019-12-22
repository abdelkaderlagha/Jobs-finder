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

import com.jobsfinder.jobsfinder.dao.CategoryRepository;
import com.jobsfinder.jobsfinder.model.Category;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Category management")
@RestController
public class CategoryController {

	@Autowired
	private CategoryRepository categoryrepo;
	
	
	@ApiOperation("Show all categories")
	@GetMapping(value = "/api/auth/category")
	 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
	List<Category>ShowAllCategories(){
		return categoryrepo.findAll();
	}
	
	@ApiOperation("Show category by id")
	@GetMapping(value="/api/auth/category/{id}")
	 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
	Category ShowCategoryById(@PathVariable int id) {
		return categoryrepo.findById(id);
	}
	
	@ApiOperation("Delete all categories")
	@DeleteMapping(value = "/api/auth/category")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('PM')")
	void deleteAllCategories() {
		categoryrepo.deleteAll();
	}
	
	@ApiOperation("Delete category by id")
	@DeleteMapping(value = "/api/auth/category/{id}")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('PM')")
	Category deleteCategoryById(@PathVariable int id) {
		return categoryrepo.deleteById(id);
	}
	@ApiOperation("Add new job")
	@PostMapping(value="/api/auth/category")
	 @PreAuthorize(" hasRole('ADMIN') or hasRole('PM')")
	public ResponseEntity<Void>  AddNewCategory(@Valid @RequestBody Category category){
		
		Category savedCategory = categoryrepo.save(category);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCategory.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	@ApiOperation("Update category")
	@PostMapping(value = "/api/auth/category/update/{id}")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('PM')")
	void updateCategory(@PathVariable String category) {
		categoryrepo.updateCategory(category);
	}
}
