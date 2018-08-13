package com.converter;

import java.util.ArrayList;
import java.util.List;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.dto.UserDto;
import com.entity.Role;
import com.entity.User;

public class UserConverter {

	public static User toUser(UserDto userDto) {

		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

		User user = new User();
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		user.setBirthday(userDto.getBirthday());
		user.setIdCard(userDto.getIdCard());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncryptor.encryptPassword(userDto.getPassword()));
		user.setManagedBy(userDto.getManagedBy());
		user.setValidity(true);
		Role role = new Role();
		role.setId(2);
		user.setRole(role);

		return user;
	}

	public static User toEditUser(UserDto userDto) {
		User user = new User();

		user.setId(userDto.getId());
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		user.setBirthday(userDto.getBirthday());
		user.setIdCard(userDto.getIdCard());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setManagedBy(userDto.getManagedBy());
		user.setValidity(true);
		Role role = new Role();
		role.setId(userDto.getIdRole());
		user.setRole(role);

		return user;
	}

	public static UserDto toUserDto(User user) {

		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setFirstname(user.getFirstname());
		userDto.setLastname(user.getLastname());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setIdRole(user.getRole().getId());
		userDto.setRole(user.getRole().getRole());
		userDto.setManagedBy(user.getManagedBy());
		userDto.setIdCard(user.getIdCard());
		userDto.setBirthday(user.getBirthday());

		return userDto;
	}

	public static List<UserDto> toUserListDto(List<User> users) {
		List<UserDto> usersListDto = new ArrayList<>();
		for (User user : users) {
			usersListDto.add(toUserDto(user));
		}
		return usersListDto;
	}

}