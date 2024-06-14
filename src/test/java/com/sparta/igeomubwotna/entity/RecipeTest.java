package com.sparta.igeomubwotna.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.igeomubwotna.dto.RecipeRequestDto;

public class RecipeTest {

	String TEST_RECIPE_TITLE = "햄버거";
	String TEST_RECIPE_CONTENT = "참깨빵 위에 ~~ 양파까지";
	Long TEST_RECIPE_LIKES = 10L;
	String TEST2_RECIPE_TITLE = "컵라면";
	String TEST2_RECIPE_CONTENT = "줄까지 물긋고 3분";

	@Test
	public void recipeAddLikeSuccess() {
		//given
		final Recipe recipe = new Recipe();
		setRecipe(recipe, TEST_RECIPE_TITLE, TEST_RECIPE_CONTENT, TEST_RECIPE_LIKES);

		//when
		recipe.addLike();

		//then
		assertEquals(TEST_RECIPE_LIKES + 1L, recipe.getRecipeLikes());
	}

	@Test
	public void recipeMinusLike() {
		//given
		final Recipe recipe = new Recipe();
		setRecipe(recipe, TEST_RECIPE_TITLE, TEST_RECIPE_CONTENT, TEST_RECIPE_LIKES);

		//when
		recipe.minusLike();

		//then
		assertEquals(TEST_RECIPE_LIKES - 1L, recipe.getRecipeLikes());
	}

	@Test
	public void recipeUpdateSuccess() {
		//given
		final Recipe recipe = new Recipe();
		setRecipe(recipe, TEST_RECIPE_TITLE, TEST_RECIPE_CONTENT, TEST_RECIPE_LIKES);
		RecipeRequestDto requestDto = new RecipeRequestDto();
		setRecipeDto(requestDto, TEST2_RECIPE_TITLE, TEST2_RECIPE_CONTENT);

		//when
		recipe.update(requestDto);

		//then
		assertEquals(TEST2_RECIPE_TITLE, recipe.getTitle());
		assertEquals(TEST2_RECIPE_CONTENT, recipe.getContent());
	}

	private <T> void setRecipe (T entity, String title, String content, Long recipeLikes){
		ReflectionTestUtils.setField(entity, "title", title);
		ReflectionTestUtils.setField(entity, "content", content);
		ReflectionTestUtils.setField(entity, "recipeLikes", recipeLikes);
	}
	private <T> void setRecipeDto (T entity, String title, String content){
		ReflectionTestUtils.setField(entity, "title", title);
		ReflectionTestUtils.setField(entity, "content", content);
	}

}
