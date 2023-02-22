package com.youtube.random_code.user_dashboard_crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.random_code.user_dashboard_crud.model.User;
import com.youtube.random_code.user_dashboard_crud.persistence.UserEntity;
import com.youtube.random_code.user_dashboard_crud.persistence.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserEntity getUserById(Integer id) {
		return userRepository.getUserEntityById(id);
	}

	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<UserEntity> addNewUser(HttpEntity<String> userEntity) {
		Optional<UserEntity> newUser = Optional.empty();

		Optional<User> httpBodyUser = turnJsonIntoModel(userEntity.getBody());

		if(httpBodyUser.isPresent()) {
			UserEntity mappedUserEntity = mapUserEntity(httpBodyUser.get());

			UserEntity newlySavedUserInDb = userRepository.save(mappedUserEntity);

			newUser = Optional.of(newlySavedUserInDb);
		}

		return newUser;
	}

	public Optional<UserEntity> updateUser(
		Integer id,
		HttpEntity<String> userEntity
	) {
		Optional<UserEntity> updatedUser = Optional.empty();

		Optional<UserEntity> currentUser = Optional.ofNullable(userRepository.getUserEntityById(id));

		if(currentUser.isEmpty()) {
			return updatedUser;
		}

		Optional<User> httpBodyUser = turnJsonIntoModel(userEntity.getBody());

		if(httpBodyUser.isPresent()) {
			UserEntity potentiallyUpdatedUser = modifyUser(httpBodyUser.get(), currentUser.get());

			UserEntity newlyUpdatedUserInDb = userRepository.save(potentiallyUpdatedUser);

			updatedUser = Optional.of(newlyUpdatedUserInDb);
		}

		return updatedUser;
	}

	private UserEntity mapUserEntity(User user) {
		return new UserEntity(
			user.getName(),
			user.getEmail(),
			user.getStatus()
		);
	}

	private Optional<User> turnJsonIntoModel(String jsonUser) {
		ObjectMapper mapper = new ObjectMapper();

		Optional<User> userModel = Optional.empty();

		try {
			User mappedUser = mapper.readValue(jsonUser, User.class);

			userModel = Optional.of(mappedUser);
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		}

		return userModel;
	}

	private UserEntity modifyUser(
		User newlyUpdatedUser,
		UserEntity userEntity
	) {
		if(newlyUpdatedUser.getName() != null) {
			userEntity.setName(newlyUpdatedUser.getName());
		}

		if(newlyUpdatedUser.getEmail() != null) {
			userEntity.setEmail(newlyUpdatedUser.getEmail());
		}

		if(newlyUpdatedUser.getStatus() != null) {
			userEntity.setStatus(newlyUpdatedUser.getStatus());
		}

		return userEntity;
	}

	public Optional<UserEntity> removeUser(Integer id) {
		Optional<UserEntity> optionalUser = userRepository.findById(id);

		if(optionalUser.isPresent()) {
			userRepository.deleteById(id);
		}

		return optionalUser;
	}
}
