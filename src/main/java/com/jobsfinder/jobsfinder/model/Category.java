package com.jobsfinder.jobsfinder.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "category")
public class Category {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String category;

	@JsonManagedReference
	@OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
	private List<Jobs> jobs;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Category() {
		super();
	}

	public List<Jobs> getJobs() {
		return jobs;
	}

	public void setJobs(List<Jobs> jobs) {
		this.jobs = jobs;
	}
	
	
	

}
