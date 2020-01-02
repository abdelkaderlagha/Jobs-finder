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
@Table(name = "company")
public class Company {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	private String local;
	private String image;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "company" , cascade = CascadeType.ALL)
	private List<Jobs> jobs;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<Jobs> getJobs() {
		return jobs;
	}
	public void setJobs(List<Jobs> jobs) {
		this.jobs = jobs;
	}
	public Company() {
		super();
	}
	
	

}
