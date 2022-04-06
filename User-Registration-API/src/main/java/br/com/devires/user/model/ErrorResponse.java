package br.com.devires.user.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ControllerAdvice
public class ErrorResponse {
	
	
	private String 			message;
	private HttpStatus  	status;
	private List<String> 	errors;
	


	
	
}
