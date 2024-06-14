package com.sparta.igeomubwotna.DTO;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.igeomubwotna.dto.RecipeRequestDto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class RecipeRequestDtoTest {
	String TITLE = "레시피 제목입니다.";
	String CONTENT = "레시피 내용입니다.";

	@Test
	public void RecipeRequestSuccess() {
		//given
		final RecipeRequestDto requestDto = new RecipeRequestDto();
		RecipeRequestDto(requestDto, TITLE, CONTENT);

		//when
		Set<ConstraintViolation<RecipeRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isEmpty();
	}
	@Test
	public void RecipeRequestNoTitle() {
		//given
		final RecipeRequestDto requestDto = new RecipeRequestDto();
		RecipeRequestDto(requestDto, "", CONTENT);

		//when
		Set<ConstraintViolation<RecipeRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isNotEmpty();
	}
	@Test
	public void RecipeRequestNoContent() {
		//given
		final RecipeRequestDto requestDto = new RecipeRequestDto();
		RecipeRequestDto(requestDto, TITLE, "");

		//when
		Set<ConstraintViolation<RecipeRequestDto>> violations = validate(requestDto);

		//then
		Assertions.assertThat(violations).isNotEmpty();
	}

	private Set<ConstraintViolation<RecipeRequestDto>> validate(RecipeRequestDto requestDto) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(requestDto);
	}

	private <T> void RecipeRequestDto (T entity, String title, String content){
		ReflectionTestUtils.setField(entity, "title", title);
		ReflectionTestUtils.setField(entity, "content", content);
	}
}
