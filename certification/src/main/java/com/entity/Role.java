package com.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String role;
	@Column(columnDefinition = "TINYINT", length = 1)
	private boolean validity;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isValidity() {
		return validity;
	}
	public void setValidity(boolean validity) {
		this.validity = validity;
	}	
	
}
