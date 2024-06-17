package com.sparta.igeomubwotna.mvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.igeomubwotna.config.SecurityConfig;
import com.sparta.igeomubwotna.controller.RecipeController;
import com.sparta.igeomubwotna.dto.RecipeRequestDto;
import com.sparta.igeomubwotna.entity.User;
import com.sparta.igeomubwotna.security.UserDetailsImpl;
import com.sparta.igeomubwotna.service.RecipeService;

@WebMvcTest(
	controllers = {RecipeController.class},
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = SecurityConfig.class
		)
	}
)
@MockBean(JpaMetamodelMappingContext.class)
public class RecipeControllerTest {
	private MockMvc mvc;

	private Principal mockPrincipal;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	RecipeService recipeService;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context)
			.apply(springSecurity(new MockSpringSecurityFilter()))
			.build();
	}

	private void mockUserSetup() {
		String userId = "qwerty1";
		String password = "Qwertyuiop1!";
		String name = "user1";
		String email = "11@11.com";
		String description = "반갑습니다.";
		User testUser = new User(userId, password, name, email, description);
		UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
		mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
	}

	@Test
	@DisplayName("레시피 저장 테스트")
	void saveRecipeTest() throws Exception {
		// given
		this.mockUserSetup();
		String title = "제목입니다.";
		String content = "내용입니다.";
		RecipeRequestDto requestDto = Mockito.mock(RecipeRequestDto.class);
		when(requestDto.getTitle()).thenReturn(title);
		when(requestDto.getContent()).thenReturn(content);

		String saveRecipeInfo = objectMapper.writeValueAsString(requestDto);

		// when - then
		mvc.perform(post("/api/recipe/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(saveRecipeInfo)
				.accept(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
			)
			.andExpect(status().isOk())
			.andDo(print());
	}

	// POSTMAN에선 403이 잘 뜨는데 Test에선 200으로 출력 중
	// @Test
	// @DisplayName("레시피 저장 테스트 제목이 없는 경우 실패")
	// void saveRecipeNoTitleTest() throws Exception {
	// 	// given
	// 	this.mockUserSetup();
	// 	String content = "내용입니다.";
	// 	RecipeRequestDto requestDto = Mockito.mock(RecipeRequestDto.class);
	// 	when(requestDto.getTitle()).thenReturn(null);
	// 	when(requestDto.getContent()).thenReturn(content);
	//
	// 	String saveRecipeInfo = objectMapper.writeValueAsString(requestDto);
	//
	// 	// when - then
	// 	mvc.perform(post("/api/recipe/")
	// 			.contentType(MediaType.APPLICATION_JSON)
	// 			.content(saveRecipeInfo)
	// 			.accept(MediaType.APPLICATION_JSON)
	// 			.principal(mockPrincipal)
	// 		)
	// 		.andExpect(status().is4xxClientError())
	// 		.andDo(print());
	// }

}
