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

import br.com.devires.user.dto.MessageResponseDTO;
import br.com.devires.user.model.User;
import br.com.devires.user.repositories.UserRespository;

@RestController
@RequestMapping(path="/v1/users")
public class UserController {
	
	private UserRespository userRepository;
	
	@Autowired
	public UserController(UserRespository userRespository) {
		super();
		this.userRepository = userRespository;
	}
	
	
	/**
	 * Versão 1.0  - Rosenhaim - 05/04/2022
	 * @param user
	 * @return
	 */
//	@PostMapping
//	public ResponseEntity<User> save(@RequestBody User user)
//	{
//		userRepository.save(user);
//		return new ResponseEntity<>(user, HttpStatus.OK);
//	}
	
	/**
	 * Versão 1.1 - Rosenhaim - 05/04/2022
	 * @return
	 */
	@PostMapping
	public MessageResponseDTO createUser(@RequestBody User user) {
		user.setUpdatedAt(new Date());
	    user.setCreatedAt(new Date());
		User savedUser = userRepository.save(user);
		
		
		return MessageResponseDTO
				.builder()
				.message("Created User with id " + savedUser.getId())
				.build();
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		List<User> users = new ArrayList<>();
		users = userRepository.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	
	@GetMapping(path="/{id}")
	public ResponseEntity<Optional<User>> getById(@PathVariable Integer id){
		Optional<User> user;
		try {
			user = userRepository.findById(id);
			return new ResponseEntity<Optional<User>> (user, HttpStatus.OK);
		} catch (NoSuchElementException nse) {
			return new ResponseEntity<Optional<User>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Optional<User>> deleteById(@PathVariable Integer id){
		try {
			userRepository.deleteById(id);
			return new ResponseEntity<Optional<User>>(HttpStatus.OK);
		}catch (NoSuchElementException nse) {
			return new ResponseEntity<Optional<User>>(HttpStatus.NOT_FOUND);
		}
	}
		
	
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User newUser){
		return userRepository.findById(id)
				.map(user -> {
					user.setName(newUser.getName());
					User userUpdated = userRepository.save(user);
					return ResponseEntity.ok().body(userUpdated);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	
	
	
	}
	
	


