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
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public UserEntity getUser(@PathVariable Integer id) {
		return userService.getUserById(id);
	}

	@GetMapping
	public List<UserEntity> getUsers() {
		return userService.getAllUsers();
	}

	@PostMapping
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

	@PutMapping("{id}")
	public ResponseEntity<Integer> updateUser(
		@PathVariable Integer id,
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

	@DeleteMapping("{id}")
	public ResponseEntity<Integer> deleteUser(@PathVariable Integer id) {
		Optional<UserEntity> removedUser = userService.removeUser(id);

		Integer userId = null;
		HttpStatus status = HttpStatus.CONFLICT;

		if(removedUser.isPresent()) {
			userId = removedUser.get().getId();
			status = HttpStatus.OK;
		}

		return new ResponseEntity<>(userId, status);
	}
}
