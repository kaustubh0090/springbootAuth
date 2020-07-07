package com.friendsgroup.mobileappws.service;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.friendsgroup.mobileappws.io.entity.UserEntity;
import com.friendsgroup.mobileappws.io.repositories.UserRepository;
import com.friendsgroup.mobileappws.shared.dto.UserDto;
import com.friendsgroup.mobileappws.shared.dto.Utils;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils util;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto user) {

		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new RuntimeException("user already exist");
		}

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		String publicUserId = util.generateUserId(30);

		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserid(publicUserId);

		UserEntity storedUserDetails = userRepository.save(userEntity);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);

		return returnValue;
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userentity = userRepository.findByEmail(email);

		if (userentity == null)
			throw new UsernameNotFoundException(email);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userentity, returnValue);

		return returnValue;

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userentity = userRepository.findByEmail(email);

		if (userentity == null)
			throw new UsernameNotFoundException(email);

		return new User(userentity.getEmail(), userentity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUserByUserId(String userid) {

		UserDto returnvalue = new UserDto();
		UserEntity userentity = userRepository.findByUserid(userid);

		if (userentity == null)
			throw new UsernameNotFoundException(userid);

		BeanUtils.copyProperties(userentity, returnvalue);

		return returnvalue;
	}

	@Override
	public UserDto updateUser(String userid, UserDto user) {
		
		UserDto returnvalue = new UserDto();
		UserEntity userentity = userRepository.findByUserid(userid);

		if (userentity == null)
			throw new UsernameNotFoundException(userid);

		userentity.setFirstname(user.getFirstname());
		userentity.setLastname(user.getLastname());

		UserEntity updateUserDetails = userRepository.save(userentity);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(updateUserDetails, returnValue);

		return returnValue;
		
		
		
	}

	@Override
	public void deleteUserByUserId(String userid) {
		UserDto returnvalue = new UserDto();
		UserEntity userentity = userRepository.findByUserid(userid);

		if (userentity == null)
			throw new UsernameNotFoundException(userid);

		userRepository.delete(userentity);
		
		System.out.println("delete user succesfully");
		
	}

}
