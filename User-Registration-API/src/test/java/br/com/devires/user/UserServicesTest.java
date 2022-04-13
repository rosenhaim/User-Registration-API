package br.com.devires.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.devires.user.model.User;
import br.com.devires.user.repositories.UserRepository;
import br.com.devires.user.service.UserService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserServicesTest {
	
	User				RECORD_1			=	new 	User
			( 10, "John Smith" , "string", new Date("21/07-1988"), new Date(), new Date(), false);
	
	User				RECORD_2			=	new 	User
			( 2, "Jack Sparrow" , "Maior pirata", new Date("05/05/1979"), new Date(), new Date(), false);			


	@MockBean
	private 	UserRepository 		userRepository;
	
	@InjectMocks
	private 	UserService 		userService;
	
	@Test
	public void testUserSave() {
		
		Mockito.when(userRepository.save(RECORD_1)).thenReturn(RECORD_1);
		
		
		User result = userService.save(RECORD_1);
		
		assertTrue(result.equals(RECORD_1));
	}
	
	@Test
	public void testUserNotFoundById() throws UserNotFoundException {
	
		Mockito.when(userRepository.findById(10)).thenReturn(null);
		
		Optional<User> result = userService.findById(10);
		
		assertNull(result);
		
	}
	
	@Test
	public void testFindAll() {
		List<User> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2));
		
		Mockito.when(userRepository.findAll()).thenReturn(records);
		
		assertTrue(userService.findAll().equals(records));
	}
	
}
