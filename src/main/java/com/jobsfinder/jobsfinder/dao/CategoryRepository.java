package com.jobsfinder.jobsfinder.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobsfinder.jobsfinder.model.Category;

public interface CategoryRepository  extends JpaRepository<Category, Integer>{

	void deleteAll();
	List<Category>findAll();
	Category findById(int id);
	Category deleteById(int id);
	
	@Transactional
	@Modifying
	@Query(" update Category ca Set ca.category= :category")
	void updateCategory(@Param("category") String category) ;	
	
}
