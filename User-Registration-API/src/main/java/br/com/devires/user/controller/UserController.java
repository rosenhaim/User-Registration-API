package br.com.devires.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devires.user.UserNotFoundException;
import br.com.devires.user.dto.MessageResponseDTO;
import br.com.devires.user.model.User;
import br.com.devires.user.repositories.UserRepository;
import br.com.devires.user.service.UserService;

@RestController
@RequestMapping(path="/v1/users")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	
	
	/**
	 * Versão 1.1 - Rosenhaim - 05/04/2022
	 * @return
	 */
	@PostMapping
	public MessageResponseDTO createUser(@RequestBody User user) {
		user.setUpdatedAt(new Date());
	    user.setCreatedAt(new Date());
		User savedUser = userService.save(user);
		
		
		return MessageResponseDTO
				.builder()
				.message("Created User with id " + savedUser.getId())
				.build();
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		List<User> users = new ArrayList<>();

		users = userService.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	
	@GetMapping(path="/{id}")
	public ResponseEntity<Optional<User>> getById(@PathVariable Integer id) throws UserNotFoundException{
		Optional<User> user;
		try {
			user = userService.findById(id);
			return new ResponseEntity<Optional<User>> (user, HttpStatus.OK);
		} catch (NoSuchElementException nse) {
			return new ResponseEntity<Optional<User>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Optional<User>> deleteById(@PathVariable Integer id){
		try {
			userService.deleteById(id);
			return new ResponseEntity<Optional<User>>(HttpStatus.OK);
		}catch (NoSuchElementException nse) {
			return new ResponseEntity<Optional<User>>(HttpStatus.NOT_FOUND);
		}
	}
		
	
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User newUser) throws UserNotFoundException{
		return userService.findById(id)
				.map(user -> {
					user.setName(newUser.getName());
					User userUpdated = userService.save(user);
					return ResponseEntity.ok().body(userUpdated);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	
	
	
	}
	
	


