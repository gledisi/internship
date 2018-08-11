package com.managedBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.dto.UserDto;
import com.services.UserService;
import com.utility.Messages;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String password;

	@ManagedProperty(value = "#{userService}")
	private UserService userService;
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	public String logIn() {

		UserDto userDto = userService.getLoggedUser(email);
		if (userDto != null) {
			userBean.setUser(userDto);
			return "employee/home.xhtml?faces-redirect=true";
		} else {
			Messages.addMessage(Messages.bundle.getString("INCORRECT_DATA"), "error");
			return null;
		}

	}

	public String logOut() {

		userBean.logOut();
		return "/login.xhtml?faces-redirect=true";
	}

	// GETTERS AND SETTERS
	public String getEmail() {
		return email;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
