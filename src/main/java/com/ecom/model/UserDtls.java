package com.ecom.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UserDtls {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Name cannot be empty")
	@Size(min = 2, message = "Name should have at least 2 characters")
	private String name;

	@NotBlank(message = "Mobile number cannot be empty")
	private String mobileNumber;

	@Email(message = "Email should be valid")
	@NotBlank(message = "Email cannot be empty")
	private String email;

	@NotBlank(message = "Address cannot be empty")
	private String address;

	@NotBlank(message = "City cannot be empty")
	private String city;

	@NotBlank(message = "State cannot be empty")
	private String state;

	@NotBlank(message = "Pincode cannot be empty")
	private String pincode;

	private String password;

	private String profileImage;

	private String role;

	private Boolean isEnable;

	private Boolean accountNonLocked;

	private Integer failedAttempt;

	private Date lockTime;
	
	private String resetToken;

}
