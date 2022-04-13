package br.com.devires.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.devires.user.UserNotFoundException;
import br.com.devires.user.model.User;
import br.com.devires.user.repositories.UserRepository;
import lombok.Data;

@Service
@Data
public class UserService {
	
	@Autowired
	private UserRepository userRespository;
	
	
	
	public List<User> findAll(){
		return userRespository.findAll(Sort.by("name").ascending());
	}
	
	public User save(User user) {
		return userRespository.save(user);
	}
	
	public  Optional<User> findById(Integer id) throws UserNotFoundException{
		return userRespository.findById(id);
	}
	
	public void deleteById(Integer id) {
		userRespository.deleteById(id);
	}

	
	
}
