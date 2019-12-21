package com.jobsfinder.jobsfinder.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.jobsfinder.jobsfinder.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	void deleteAll();
	List<Company>findAll();
	Company findById(int id);
	Company deleteById(int id);
	
	@Transactional
	@Modifying
	@Query(" update Company c Set c.name= :name, c.local=:local")
	void updateCompany(@Param("name") String name, @Param("local")String local) ;	
	
	
}
