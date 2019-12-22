package com.jobsfinder.jobsfinder.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jobsfinder.jobsfinder.model.Jobs;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, Integer>{
	
	List<Jobs> findAll();
	Jobs findById(int id);
	void deleteAll();
	Jobs deleteById(int id);
	
	
	@Query("select j.title from Jobs j , Category c where j.id = c.id and c.category = :name")
	List<String> searchByCategory(@Param(value = "name") String name);
	
	@Query("select j.title from Jobs j , Company c where j.id = c.id and c.name = :name")
	List<String> searchByCompnayName(@Param(value = "name") String name);
	
	@Modifying
	@Transactional
	@Query("update Jobs j Set j.created_at = :created_at , j.deadline = :deadline , j.description = :description , j.title = :title where j.id = :id ")
	void updateJob(@Param("created_at") String created_at ,@Param("deadline") String deadline, @Param("description") String description,@Param("title") String title, @Param("id") int id  );
}
