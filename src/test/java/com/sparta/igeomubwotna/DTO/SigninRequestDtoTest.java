package com.sparta.igeomubwotna.DTO;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.igeomubwotna.dto.SigninRequestDto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class SigninRequestDtoTest {
	String USERID_SUCCESS = "qwertyuiop1";
	String PASSWORD_SUCCESS = "Qwertyuiop1!";

	@Test
	public void SigninRequestSuccess() {
		//given
		final SigninRequestDto requestDto = new SigninRequestDto();
		setSigninRequestDto(requestDto, USERID_SUCCESS, PASSWORD_SUCCESS);

		//when
		Set<ConstraintViolation<SigninRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isEmpty();
	}
	@Test
	public void SigninRequestNoUserId() {
		//given
		final SigninRequestDto requestDto = new SigninRequestDto();
		setSigninRequestDto(requestDto, "", PASSWORD_SUCCESS);

		//when
		Set<ConstraintViolation<SigninRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).extracting("message").contains("아이디는 공백일 수 없습니다.");
	}
	@Test
	public void SigninRequestNoPassword() {
		//given
		final SigninRequestDto requestDto = new SigninRequestDto();
		setSigninRequestDto(requestDto, USERID_SUCCESS, "");

		//when
		Set<ConstraintViolation<SigninRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).extracting("message").contains("비밀번호는 공백일 수 없습니다.");
	}

	private Set<ConstraintViolation<SigninRequestDto>> validate(SigninRequestDto requestDto) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(requestDto);
	}

	private <T> void setSigninRequestDto (T entity, String userId, String password){
		ReflectionTestUtils.setField(entity, "userId", userId);
		ReflectionTestUtils.setField(entity, "password", password);
	}
}
