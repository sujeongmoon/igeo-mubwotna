package com.sparta.igeomubwotna.DTO;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.igeomubwotna.dto.UserUpdateRequestDto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserUpdateRequestDtoTest {

	String PASSWORD_SUCCESS = "Qwertyuiop1234!";
	String PASSWORD_FAILED_SIZE = "Qwerty1!";
	String PASSWORD_FAILED_PATTERN = "비밀번호";
	String NAME = "유저1";
	String DESCRIPTION = "유저1입니다.";

	@Test
	public void userUpdateRequestSuccess() {
		//given
		final UserUpdateRequestDto requestDto = new UserUpdateRequestDto();
		setUserUpdateRequestDto(requestDto, NAME, DESCRIPTION, PASSWORD_SUCCESS);

		//when
		Set<ConstraintViolation<UserUpdateRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isEmpty();
	}

	@Test
	public void userUpdateRequestNoName() {
		//given
		final UserUpdateRequestDto requestDto = new UserUpdateRequestDto();
		setUserUpdateRequestDto(requestDto, "",DESCRIPTION, PASSWORD_SUCCESS);

		//when
		Set<ConstraintViolation<UserUpdateRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isNotEmpty();
	}

	@Test
	public void userUpdateNewPasswordNoDescription() {
		//given
		final UserUpdateRequestDto requestDto = new UserUpdateRequestDto();
		setUserUpdateRequestDto(requestDto, NAME,DESCRIPTION, PASSWORD_SUCCESS);

		//when
		Set<ConstraintViolation<UserUpdateRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isNotEmpty();
	}

	@Test
	public void userUpdateRequestPasswordFailedPattern() {
		//given
		final UserUpdateRequestDto requestDto = new UserUpdateRequestDto();
		setUserUpdateRequestDto(requestDto, NAME, DESCRIPTION, PASSWORD_FAILED_PATTERN);

		//when
		Set<ConstraintViolation<UserUpdateRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).extracting("message").contains("password는 최소 10글자 이상이어야 합니다.");
	}

	@Test
	public void userUpdateRequestPasswordFailedSize() {
		//given
		final UserUpdateRequestDto requestDto = new UserUpdateRequestDto();
		setUserUpdateRequestDto(requestDto, NAME,"", PASSWORD_FAILED_SIZE);

		//when
		Set<ConstraintViolation<UserUpdateRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).extracting("message").contains("password는 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로만 구성되어야 합니다.");
	}

	private Set<ConstraintViolation<UserUpdateRequestDto>> validate(UserUpdateRequestDto requestDto) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(requestDto);
	}

	private <T> void setUserUpdateRequestDto (T entity, String name, String description, String newPassword){
		ReflectionTestUtils.setField(entity, "name", name);
		ReflectionTestUtils.setField(entity, "description", description);
		ReflectionTestUtils.setField(entity, "newPassword", newPassword);
	}
}
