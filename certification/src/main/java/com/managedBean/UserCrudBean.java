package com.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.dto.UserDto;
import com.services.UserService;
import com.utility.Messages;

@ManagedBean(name = "userCrudBean")
@ViewScoped
public class UserCrudBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	@ManagedProperty(value = "#{userService}")
	private UserService userService;

	private UserDto user;
	private List<UserDto> employeesOfManager;
	private List<UserDto> selectedEmployees;

	@PostConstruct
	public void init() {

		refreshBean();
	}

	public void refreshBean() {
		this.user = new UserDto();
		this.employeesOfManager = userService.getEmployeesOfManager(1);
	}

	public String addEmployee() {

		user.setManagedBy(1);

		if (!userExists()) {
			System.out.println(user.toString());
			if (userService.add(user)) {
				System.out.println("user u shtua");
				refreshBean();
			} else {
				System.out.println("user nuk u shtua");
				return null;
			}
		} else {
			System.out.println("useri ekziston!");
			return null;
		}

		return "employees.xhtml?faces-redirect=true";
	}

	public String editEmployee() {
		UserDto userEdit = userService.getUserFromId(this.user.getId());
		System.out.print(userEdit.getEmail());
		boolean userExist = userExists();
		if (!userExist || (userExist && userEdit.getEmail().equals(this.user.getEmail()))) {

			if (userService.edit(user)) {
				refreshBean();
				Messages.addMessage(Messages.bundle.getString("EMPLOYEE_EDITED"), "info");
			} else {
				Messages.addMessage(Messages.bundle.getString("EMPLOYEE_NOT_EDITED"), "error");
			}
		} else {
			Messages.addMessage(Messages.bundle.getString("EMAIL_EXIST"), "warn");
			refreshBean();
		}
		return null;
	}

	public String deleteEmployee(int employeeId) {

		if (userService.delete(employeeId)) {
			Messages.addMessage(Messages.bundle.getString("EMPLOYEE_DELETED"), "info");
			refreshBean();
		} else {
			Messages.addMessage(Messages.bundle.getString("EMPLOYEE_NOT_DELETED"), "error");
		}
		return null;
	}

	public String deleteSelectedEmployees() {

		if (userService.deleteList(getSelectedEmployees())) {

			if (getSelectedEmployees().size() == 1) {
				Messages.addMessage(Messages.bundle.getString("EMPLOYEE_DELETED"), "info");

			} else {
				Messages.addMessage(Messages.bundle.getString("EMPLOYEES_DELETED"), "info");

			}

			refreshBean();

		} else {

			if (getSelectedEmployees().size() == 0) {
				Messages.addMessage(Messages.bundle.getString("EMPLOYEES_NOT_SELECTED"), "warn");
			} else {
				Messages.addMessage(Messages.bundle.getString("EMPLOYEE_NOT_DELETED"), "warn");
			}

		}
		return null;
	}

	private boolean userExists() {
		if (userService.getLoggedUser(this.user.getEmail()) != null)
			return true;
		return false;
	}

	// GETTERS AND SETTERS

	public List<UserDto> getSelectedEmployees() {
		return selectedEmployees;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setSelectedEmployees(List<UserDto> selectedEmployees) {
		this.selectedEmployees = selectedEmployees;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public List<UserDto> getEmployeesOfManager() {
		return employeesOfManager;
	}

	public void setEmployeesOfManager(List<UserDto> employeesOfManager) {
		this.employeesOfManager = employeesOfManager;
	}

}