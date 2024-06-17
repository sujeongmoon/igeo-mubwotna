package com.sparta.igeomubwotna.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.igeomubwotna.controller.CommentController;
import com.sparta.igeomubwotna.controller.RecipeController;
import com.sparta.igeomubwotna.service.CommentService;
import com.sparta.igeomubwotna.service.RecipeService;

@WebMvcTest(
	controllers = {CommentController.class, RecipeController.class}
)
public class CommentControllerTest {
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	CommentService commentService;

	@MockBean
	RecipeService recipeService;

}
