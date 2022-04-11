package br.com.devires.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devires.user.repositories.UserRespository;
import lombok.Data;

@Service
@Data
public class UserService {
	
	private UserRespository userRespository;
	
	@Autowired
	public UserService(UserRespository userRespository) {
		this.userRespository = userRespository;
		
	}
	
	

}
