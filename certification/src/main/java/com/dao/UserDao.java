package com.dao;

import java.util.List;

import com.entity.User;

public interface UserDao {

	public boolean add(User user);

	public boolean edit(User user);

	public boolean delete(int userId);

	public boolean changePassword(int userId, String newPassword);

	public User getUserFromId(int userId);

	public User getLoggedUser(String email);

	public User getUserFromCardId(String idCard);

	public List<User> getEmployeesOfManager(String inputSearch, int idManager);
}
