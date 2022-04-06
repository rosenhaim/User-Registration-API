package br.com.devires.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
//	private Long 			id;
	private Integer 		id;
	
	@NonNull
	private String 		name;
	
	@Column(nullable = true)
	private String		description;
	
	@Column(nullable = true)
	private Date		birthDate;
	
	@Column(nullable = true)
	private Date		createdAt;
	
	@Column(nullable = true)
	private Date 		updatedAt;
	
	@NonNull
	private Boolean		active = false;
	
	

	

}
