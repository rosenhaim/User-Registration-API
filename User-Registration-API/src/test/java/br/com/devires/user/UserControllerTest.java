package br.com.devires.user;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.devires.user.controller.UserController;
import br.com.devires.user.model.User;
import br.com.devires.user.repositories.UserRespository;
import ch.qos.logback.core.net.ObjectWriter;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	
	private 	MockMvc 			mockMvc;
	
	@Mock
	private		UserRespository		userRespository;
	
	@InjectMocks
	private 	UserController		userController;
	
	ObjectMapper 		objectMapper		= 	new 	ObjectMapper();	
	ObjectWriter		objectWriter		=	(ObjectWriter) objectMapper.writer(); 
	
	
			
	User				RECORD_1			=	new User( 1, "John William" , "Descrição de John", new Date("05/05/1979"), new Date(), new Date(), false);			
	User				RECORD_2			=	new User( 2, "Jack Sparrow" , "Maior pirata", new Date("05/05/1979"), new Date(), new Date(), false);			

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc	=	MockMvcBuilders.standaloneSetup(userController).build();
		
		
	}

	
	@Test
	public void getAllRecords_success() throws Exception{
		
		List<User> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2));
		
		Mockito.when(userRespository.findAll()).thenReturn(records);
		
		mockMvc.perform((RequestBuilder) ((ResultActions) MockMvcRequestBuilders
				.get("/users")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				
				
				);
	}
	
	
}
