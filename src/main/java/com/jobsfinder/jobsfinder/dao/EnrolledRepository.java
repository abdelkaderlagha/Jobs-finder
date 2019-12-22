package com.jobsfinder.jobsfinder.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobsfinder.jobsfinder.model.Enrolled;

public interface EnrolledRepository extends JpaRepository<Enrolled, Integer>{

	
	
	List<Enrolled>findAll();
	Enrolled findById(int id);
	void deleteAll();
	Enrolled deleteById(int id);
	
	
	
}
