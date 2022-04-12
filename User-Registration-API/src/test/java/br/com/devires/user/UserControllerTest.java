package br.com.devires.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.devires.user.controller.UserController;
import br.com.devires.user.model.User;
import br.com.devires.user.repositories.UserRespository;



/**
 * 
 * @author rosenhaim
 *
 */

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
	
	private final String BASE_URL = "/v1/users";
	

	
	ObjectMapper 		objectMapper		= 	new 	ObjectMapper();	
	ObjectWriter		objectWriter		=	(ObjectWriter) objectMapper.writer(); 
	
	
			
	User				RECORD_1			=	new 	User( 10, "John Smith" , "string", new Date("21/07-1988"), new Date(), new Date(), false);			
	User				RECORD_2			=	new 	User( 2, "Jack Sparrow" , "Maior pirata", new Date("05/05/1979"), new Date(), new Date(), false);			

	@Before
	public void setUp() {
		
		this.mockMvc	=	MockMvcBuilders.standaloneSetup(userController).build();
		 RestAssuredMockMvc.standaloneSetup(mockMvc);
		
		
		
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
	
	
	@Test
	public void getById() throws Exception{
		
		 
		@SuppressWarnings("deprecation")
		User user = new User( 1,
					"John Smith" ,
					"string",
					new Date("21/07-1988"),
					new Date(),
					new Date(), false);	
	
		 when(userRepository.save(any(User.class))).thenReturn(user);

		 when(userRepository.findById(1)).thenReturn(Optional.of(user));
		
		
		mockMvc.perform(post("/v1/users")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(user)))
	            .andExpect(status().isOk());
	
        			mockMvc.perform( MockMvcRequestBuilders
        					.get(BASE_URL + "/1")
        					.contentType(MediaType.APPLICATION_JSON))
        					.andExpect(status().isOk())
        					.andExpect(content().contentType("application/json"))
         					.andExpect(jsonPath("$.id", is(1)))
                			.andExpect(jsonPath("$.name", is("John Smith")));
        	
		
	}

	@Test
public void notFindUser() {
		
		 //RestAssuredMockMvc.mockMvc(mockMvc);
	when(this.userRepository.findById(5)).thenReturn(null);
	
//	given()
////    .standaloneSetup(new UserController(userRepository))
//	//.accept(ContentType.JSON)
//.when()
//	.get("/v1/users/{codigo}", 5)
//.then()
//	.statusCode(HttpStatus.NOT_FOUND.value());
	
	
int statusCode = 	given()
		.when()
	.get("/v1/users/{codigo}", 5)
		.andReturn().statusCode();

assertEquals(404, statusCode);

}




@Test
public void returnBadRequest_searchUser() {
	
	int statusCode = given()
		.accept(ContentType.JSON)
	.when()
		.get("/v1/users/{codigo}", -10)
//	.then()
//		.statusCode(HttpStatus.BAD_REQUEST.value());
		.andReturn().statusCode();
	
	//verify(this.userRepository, never()).getById(-1);
	
	assertEquals(404, statusCode);
}
}
