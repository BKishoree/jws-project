package com.kishore.jws.project.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequestDto {

	private String userName;
	private String email;
	private String password;
}
