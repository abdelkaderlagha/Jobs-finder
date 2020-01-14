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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.jobsfinder.jobsfinder.dao.CategoryRepository;
import com.jobsfinder.jobsfinder.model.Category;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Category management")
@RestController
@RequestMapping(value="/rest/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryrepo;
	
	@ApiOperation("Get all categories")
	@GetMapping(value="/category")
	List<Category>showAllCategories(){
		return categoryrepo.findAll();
		}
	
	@ApiOperation("Get category by id {id}")
	@GetMapping(value="/category/{id}")
	 public ResponseEntity<Category> getCategorie(@PathVariable Integer id) throws Exception{
        Category c = categoryrepo.findById(id).orElseThrow(()->new Exception("Category 404"));
        return ResponseEntity.ok().body(c);
	}
	@ApiOperation("Add new category")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PM')")
	public Category addCategory(@Valid @RequestBody Category c) {
		return categoryrepo.save(c);
	}
	@ApiOperation("Update category")
	@PutMapping(value = "/category/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PM')")
	public ResponseEntity<Category>updateCategory(@Valid @PathVariable int id, @RequestBody Category newCat) throws Exception{
		Category c = categoryrepo.findById(id).orElseThrow(()-> new Exception("Category 404"));
		c.setCategory(newCat.getCategory());
		Category UpdatedC = categoryrepo.save(c);
		return ResponseEntity.ok(UpdatedC);
	}
	@ApiOperation("Delete Category by id")
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PM')")
	public String deleteCategory(@Valid @PathVariable int id)throws Exception{
		Category c = categoryrepo.findById(id).orElseThrow(()->new Exception("Category 404"));
		categoryrepo.delete(c);
		return "Category deleted!" ;
	}
}
	