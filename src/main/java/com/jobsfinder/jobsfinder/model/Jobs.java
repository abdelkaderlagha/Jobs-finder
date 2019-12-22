package com.jobsfinder.jobsfinder.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="jobs")
public class Jobs {

	@Id
	@GeneratedValue
	private int id;
	
	private String title;
	private String created_at;
	private String description;
	private String deadline;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn
	private Category category;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn
	private Company company;
	
	
	
	@JsonManagedReference
	@OneToMany(mappedBy = "jobs" , cascade = CascadeType.ALL)
	private List<Enrolled> enrolled;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public Jobs() {
		super();
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public List<Enrolled> getEnrolled() {
		return enrolled;
	}
	public void setEnrolled(List<Enrolled> enrolled) {
		this.enrolled = enrolled;
	}
	
}
