package com.friendsgroup.mobileappws.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.friendsgroup.mobileappws.service.UserService;
import com.friendsgroup.mobileappws.shared.dto.UserDto;
import com.friendsgroup.mobileappws.ui.model.request.UserDetailsRequest;
import com.friendsgroup.mobileappws.ui.model.response.OperationStatus;
import com.friendsgroup.mobileappws.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(path = "/{id}")
	public UserRest getUser(@PathVariable String id) {

		UserRest returnvalue = new UserRest();
		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnvalue);

		return returnvalue;
	}

	@PostMapping
	public UserRest createUser(@RequestBody UserDetailsRequest userDetails) {

		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userDetails, userDto);

		UserDto createUser = userService.createUser(userDto);

		BeanUtils.copyProperties(createUser, returnValue);

		return returnValue;
	}

	@PutMapping(path = "/{id}")
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequest userDetails) {

		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userDetails, userDto);

		UserDto updateUser = userService.updateUser(id, userDto);

		BeanUtils.copyProperties(updateUser, returnValue);

		return returnValue;

	}

	@DeleteMapping(path = "/{id}")
	public OperationStatus deleteUser(@PathVariable String id) {

		userService.deleteUserByUserId(id);

		OperationStatus returnvalue = new OperationStatus();
		returnvalue.setMessage("delete success");
		return returnvalue;

	}

}
