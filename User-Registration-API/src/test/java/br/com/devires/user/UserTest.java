package br.com.devires.user;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.devires.user.controller.UserController;
import br.com.devires.user.model.User;
import br.com.devires.user.repositories.UserRespository;




 
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

	
	private final String BASE_URL = "/v1/users";
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@SuppressWarnings("deprecation")
	User				RECORD_1			=	new 	User( 10, "John Smith" , "string", new Date("21/07-1988"), new Date(), new Date(), false);			
	@SuppressWarnings("deprecation")
	User				RECORD_2			=	new 	User( 2, "Jack Sparrow" , "Maior pirata", new Date("05/05/1979"), new Date(), new Date(), false);			

	@Autowired
	private MockMvc mockMvc;
	


	@Mock
	private UserRespository mockRepository;
	
	@InjectMocks
	private UserController restController;
	
	@Before
	public void setUp() {
		
		
		mockMvc = MockMvcBuilders
				//.
				.standaloneSetup(restController)
				.build();
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
	
		 when(mockRepository.save(any(User.class))).thenReturn(user);

		 when(mockRepository.findById(1)).thenReturn(Optional.of(user));
		
		
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
	
	

}
