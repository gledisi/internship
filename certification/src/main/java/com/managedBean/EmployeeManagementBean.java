package com.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.primefaces.event.SelectEvent;

import com.dto.PasswordDto;
import com.dto.UserDto;
import com.services.UserService;
import com.utility.Messages;
import com.utility.Validate;

@ManagedBean(name = "employeeManagementBean")
@ViewScoped
public class EmployeeManagementBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	@ManagedProperty(value = "#{userService}")
	private UserService userService;

	private PasswordDto passwordDto;
	private UserDto employee;
	private List<UserDto> employeesOfManager;
	private List<UserDto> selectedEmployees;

	private String inputSearch;

	@PostConstruct
	public void init() {

		refreshBean();
	}

	public void refreshBean() {
		this.passwordDto = new PasswordDto();
		this.employee = new UserDto();
		getEmployees();
	}

	public String getEmployees() {
		this.employeesOfManager = userService.getEmployeesOfManager(inputSearch, userBean.getUser().getId());

		return null;
	}

	public String addEmployee() {

		if (Validate.birthdayOfEmployee(employee.getBirthday())) {
			employee.setManagedBy(userBean.getUser().getId());

			if (!userService.existUserEmail(employee.getEmail())) {

				if (!userService.existUserCardId(employee.getIdCard())) {
					if (userService.add(employee)) {
						Messages.addFlushMessage(Messages.bundle.getString("EMPLOYEE_ADDED"), "info");
						return "employees.xhtml?faces-redirect=true";
					} else {
						Messages.addMessage(Messages.bundle.getString("EMPLOYEE_NOT_ADDED"), "error");
					}
				} else {
					Messages.addMessage(Messages.bundle.getString("EMPLOYEE_CARDID_EXIST"), "warn");
				}

			} else {
				Messages.addMessage(Messages.bundle.getString("EMPLOYEE_EMAIL_EXIST"), "warn");
			}
		}
		return null;
	}

	public String editEmployee() {
		UserDto userEdit = userService.getUserFromId(this.employee.getId());

		if (!this.employee.equals(userEdit)) {
			boolean userEmailExist = userService.existUserEmail(employee.getEmail());
			boolean userCardIdExist = userService.existUserCardId(employee.getIdCard());
			if (!userEmailExist || (userEmailExist && userEdit.getEmail().equals(this.employee.getEmail()))) {

				if (!userCardIdExist || (userCardIdExist && userEdit.getIdCard().equals(this.employee.getIdCard()))) {

					if (userService.edit(employee)) {

						Messages.addFlushMessage(Messages.bundle.getString("EMPLOYEE_EDITED"), "info");
						return "employees.xhtml?faces-redirect=true";
					} else {
						Messages.addMessage(Messages.bundle.getString("EMPLOYEE_NOT_EDITED"), "error");
					}
				} else {
					Messages.addMessage(Messages.bundle.getString("EMPLOYEE_CARDID_EXIST"), "warn");
				}
			} else {
				Messages.addMessage(Messages.bundle.getString("EMPLOYEE_EMAIL_EXIST"), "warn");
			}
		} else {
			return "employees.xhtml?faces-redirect=true";
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

	public String loadUser() {
		int id = employee.getId();
		if (id == 0) {
			Messages.addMessage("Jepni nje id!", "warn");
		} else {
			UserDto user = userService.getUserFromId(id);
			if (user == null) {
				Messages.addMessage("Nuk ka punonjes!", "warn");
			} else if (user.getRole().equals("manager") || user.getManagedBy() != userBean.getUser().getId()) {
				Messages.addMessage("Nuk mund te editoni kete punonjes", "warn");
			} else {
				this.employee = user;
			}
		}
		return null;
	}

	public void onDoubleClick(final SelectEvent event) {
		this.employee = (UserDto) event.getObject();

	}

	public String changePassword() {

		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

		String dbPassword = userBean.getUser().getPassword();

		if (passwordEncryptor.checkPassword(passwordDto.getOldPassword(), dbPassword)) {
			if (!passwordDto.getOldPassword().equals(passwordDto.getNewPassword())) {
				if (passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {

					String newPassword = passwordEncryptor.encryptPassword(passwordDto.getNewPassword());

					if (userService.changePassword(userBean.getUser().getId(), newPassword)) {
						userBean.getUser().setPassword(newPassword);
						Messages.addMessage(Messages.bundle.getString("PASSWORD_CHANGED"), "info");
						this.passwordDto = new PasswordDto();
					} else {
						Messages.addMessage(Messages.bundle.getString("PASSWORD_NOT_CHANGED"), "error");
					}
				} else {
					Messages.addMessage(Messages.bundle.getString("INCORRECT_CONFIRM_PASSWORD"), "warn");
				}
			} else {
				Messages.addMessage(Messages.bundle.getString("SAME_PASSWORD"), "info");

			}
		} else {
			Messages.addMessage(Messages.bundle.getString("INCORRECT_OLD_PASSWORD"), "warn");
		}

		return null;
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

	public UserDto getEmployee() {
		return employee;
	}

	public void setEmployee(UserDto employee) {
		this.employee = employee;
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

	public String getInputSearch() {
		return inputSearch;
	}

	public void setInputSearch(String inputSearch) {
		this.inputSearch = inputSearch;
	}

	public PasswordDto getPasswordDto() {
		return passwordDto;
	}

	public void setPasswordDto(PasswordDto passwordDto) {
		this.passwordDto = passwordDto;
	}

}