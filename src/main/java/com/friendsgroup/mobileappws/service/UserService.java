package com.friendsgroup.mobileappws.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import com.friendsgroup.mobileappws.shared.dto.UserDto;


public interface UserService extends UserDetailsService {

	UserDto createUser(UserDto user);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userid);
	UserDto updateUser(String userid, UserDto user);
	void deleteUserByUserId(String userid);
	
}
