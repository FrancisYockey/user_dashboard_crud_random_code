package com.youtube.random_code.user_dashboard_crud.controller;

import com.youtube.random_code.user_dashboard_crud.persistence.UserEntity;
import com.youtube.random_code.user_dashboard_crud.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user")
	public UserEntity getUserById(@RequestParam Integer id) {
		return userService.getUserById(id);
	}

	@GetMapping("/users")
	public List<UserEntity> getUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("new-user")
	public ResponseEntity<Integer> addUser(HttpEntity<String> userEntity) {
		Optional<UserEntity> newUser = userService.addNewUser(userEntity);

		Integer userId = null;
		HttpStatus status = HttpStatus.CONFLICT;

		if(newUser.isPresent()) {
			userId = newUser.get().getId();
			status = HttpStatus.CREATED;
		}

		return new ResponseEntity<>(userId, status);
	}

	@PutMapping("updated-user")
	public ResponseEntity<Integer> updateUser(
		@RequestParam Integer id,
		HttpEntity<String> userEntity
	) {
		Optional<UserEntity> updatedUser = userService.updateUser(id, userEntity);

		Integer userId = null;
		HttpStatus status = HttpStatus.CONFLICT;

		if(updatedUser.isPresent()) {
			userId = updatedUser.get().getId();
			status = HttpStatus.OK;
		}

		return new ResponseEntity<>(userId, status);
	}
}
