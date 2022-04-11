package br.com.devires.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.devires.user.controller.UserController;
import br.com.devires.user.model.User;
import br.com.devires.user.repositories.UserRespository;
import br.com.devires.user.service.UserService;



/**
 * Please 
* to run my test  run with configuration : JUnit 4 
 * @author rosenhaim
 *
 */
//@RunWith(MockitoJUnitRunner.class)
//@WebMvcTest(controllers = UserController.class)
//@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@Autowired
	private 	MockMvc 			mockMvc;
	
	@Mock
	private		UserRespository		userRepository;
	
	@InjectMocks
	private 	UserController		userController;
	

	
	ObjectMapper 		objectMapper		= 	new 	ObjectMapper();	
	ObjectWriter		objectWriter		=	(ObjectWriter) objectMapper.writer(); 
	
	
			
	User				RECORD_1			=	new 	User( 10, "John Smith" , "string", new Date("21/07-1988"), new Date(), new Date(), false);			
	User				RECORD_2			=	new 	User( 2, "Jack Sparrow" , "Maior pirata", new Date("05/05/1979"), new Date(), new Date(), false);			

	@Before
	public void setUp() {
		
		this.mockMvc	=	MockMvcBuilders.standaloneSetup(userController).build();
		
		
	}

	/**
	 * 11abril 
	 * Rosenhaim teste de pesquisa com todos os usuários pré cadastrados
	 */
	@Test
	public void getAllRecords_success() throws Exception{
		
		List<User> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2));
		
		Mockito.when(userRepository.findAll()).thenReturn(records);
		
		mockMvc.perform( MockMvcRequestBuilders
				.get("/v1/users")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	/**
	 * 11abril 
	 * Rosenhaim 
	 */
	@Test
	public void UserTest() {
		User 	user 	=	new User( 10, "John Smith" , "string", new Date("21/07-1988"), new Date(), new Date(), false);
		assertEquals("John Smith", user.getName());
	
	}
	
	
	
	/**
	 * 11abril 
	 * Rosenhaim teste de erro ao pesquisar um usuário não cadastrado
	 */
	@Test
	public void erroFindUserById() {
		
		User 	user 	=	new User( 10, "John Smith" , "string", new Date("21/07-1988"), new Date(), new Date(), false);
		userRepository.save(user);
		
		User    	userSearch = userRepository.getById(10);
		
		assertFalse( userRepository.findById(1).isPresent());
		}
	
	
	
	
	
	
}
