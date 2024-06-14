package com.sparta.igeomubwotna.DTO;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.igeomubwotna.dto.SignupRequestDto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class SignupRequestDtoTest {

	String USERID_SUCCESS = "qwertyuiop1";
	String USERID_FAILED_MIN = "qwer1";
	String USERID_FAILED_MAX = "qwertyuiop1234567890asdfghjkl";
	String USERID_FAILED_PATTERN = "!!!!!!!!!!!!";
	String PASSWORD_SUCCESS = "Qwertyuiop1!";
	String PASSWORD_FAILED_SIZE = "Qwer1!";
	String PASSWORD_FAILED_PATTERN = "비밀번호1234567890";
	String NAME = "유저1";
	String EMAIL_SUCCESS = "11@11.com";
	String EMAIL_FAILED_EMAIL ="11.com";
	String DESCRIPTION = "설명입니다.";

	@Test
	public void userSignupRequestSuccess() {
		//given
		final SignupRequestDto requestDto = new SignupRequestDto();
		setSignupRequestDto(requestDto, USERID_SUCCESS, PASSWORD_SUCCESS, NAME, EMAIL_SUCCESS, DESCRIPTION);

		//when
		Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isEmpty();
	}

	@Test
	public void userSignupRequestNoUserId() {
		//given
		final SignupRequestDto requestDto = new SignupRequestDto();
		setSignupRequestDto(requestDto, "", PASSWORD_SUCCESS, NAME, EMAIL_SUCCESS, DESCRIPTION);

		//when
		Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isNotEmpty();
	}

	@Test
	public void userSignupRequestNoPassword() {
		//given
		final SignupRequestDto requestDto = new SignupRequestDto();
		setSignupRequestDto(requestDto, USERID_SUCCESS, "", NAME, EMAIL_SUCCESS, DESCRIPTION);

		//when
		Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isNotEmpty();
	}
	@Test
	public void userSignupRequestNoName() {
		//given
		final SignupRequestDto requestDto = new SignupRequestDto();
		setSignupRequestDto(requestDto, USERID_SUCCESS, PASSWORD_SUCCESS, "", EMAIL_SUCCESS, DESCRIPTION);

		//when
		Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isNotEmpty();
	}
	@Test
	public void userSignupRequestNoEmail() {
		//given
		final SignupRequestDto requestDto = new SignupRequestDto();
		setSignupRequestDto(requestDto, USERID_SUCCESS, PASSWORD_SUCCESS, NAME, "", DESCRIPTION);

		//when
		Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isNotEmpty();
	}
	@Test
	public void userSignupRequestNoDescription() {
		//given
		final SignupRequestDto requestDto = new SignupRequestDto();
		setSignupRequestDto(requestDto, USERID_SUCCESS, PASSWORD_SUCCESS, NAME, EMAIL_SUCCESS, "");

		//when
		Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isNotEmpty();
	}
	@Test
	public void userSignupRequestUserIdFailedMin() {
		//given
		final SignupRequestDto requestDto = new SignupRequestDto();
		setSignupRequestDto(requestDto, USERID_FAILED_MIN, PASSWORD_SUCCESS, NAME, EMAIL_SUCCESS, DESCRIPTION);

		//when
		Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).extracting("message").contains("사용자 ID는 최소 10글자 이상, 20글자 이하이어야 합니다.");
	}
	@Test
	public void userSignupRequestUserIdFailedMax() {
		//given
		final SignupRequestDto requestDto = new SignupRequestDto();
		setSignupRequestDto(requestDto, USERID_FAILED_MAX, PASSWORD_SUCCESS, NAME, EMAIL_SUCCESS, DESCRIPTION);

		//when
		Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).extracting("message").contains("사용자 ID는 최소 10글자 이상, 20글자 이하이어야 합니다.");
	}
	@Test
	public void userSignupRequestUserIdFailedPattern() {
		//given
		final SignupRequestDto requestDto = new SignupRequestDto();
		setSignupRequestDto(requestDto, USERID_FAILED_PATTERN, PASSWORD_SUCCESS, NAME, EMAIL_SUCCESS, DESCRIPTION);

		//when
		Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).extracting("message").contains("사용자 ID는 알파벳 대소문자, 숫자로만 구성되어야 합니다.");
	}
	@Test
	public void userSignupRequestPasswordFailedSize() {
		//given
		final SignupRequestDto requestDto = new SignupRequestDto();
		setSignupRequestDto(requestDto, USERID_SUCCESS, PASSWORD_FAILED_SIZE, NAME, EMAIL_SUCCESS, DESCRIPTION);

		//when
		Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).extracting("message").contains("password는 최소 10글자 이상이어야 합니다.");
	}
	@Test
	public void userSignupRequestPasswordFailedPattern() {
		//given
		final SignupRequestDto requestDto = new SignupRequestDto();
		setSignupRequestDto(requestDto, USERID_SUCCESS, PASSWORD_FAILED_PATTERN, NAME, EMAIL_SUCCESS, DESCRIPTION);

		//when
		Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).extracting("message").contains("password는 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로만 구성되어야 합니다.");
	}	@Test
	public void userSignupRequestEmailFailedEmail() {
		//given
		final SignupRequestDto requestDto = new SignupRequestDto();
		setSignupRequestDto(requestDto, USERID_SUCCESS, PASSWORD_SUCCESS, NAME, EMAIL_FAILED_EMAIL, DESCRIPTION);

		//when
		Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isNotEmpty();
	}

	private Set<ConstraintViolation<SignupRequestDto>> validate(SignupRequestDto requestDto) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(requestDto);
	}

	private <T> void setSignupRequestDto (T entity, String userId, String password, String name, String email, String description){
		ReflectionTestUtils.setField(entity, "userId", userId);
		ReflectionTestUtils.setField(entity, "password", password);
		ReflectionTestUtils.setField(entity, "name", name);
		ReflectionTestUtils.setField(entity, "email", email);
		ReflectionTestUtils.setField(entity, "description", description);
	}

}
