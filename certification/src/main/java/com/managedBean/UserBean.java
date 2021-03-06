package com.managedBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.dto.UserDto;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserDto user;

	public void logOut() {
		user = null;
	}

	public boolean isManager() {
		return user.getRole().equalsIgnoreCase("manager");
	}

	public boolean isEmployee() {
		return user.getRole().equalsIgnoreCase("employee");
	}

	// GETTERS AND SETTERS
	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

}
