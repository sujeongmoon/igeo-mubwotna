package com.sparta.igeomubwotna.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class CommentTest {

	String TEST_COMMENT_CONTENT = "정말 맛있어보여요";
	Long TEST_LIKE_COUNT = 0L;
	String TEST2_COMMENT_CONTENT = "해봤는데 별로임";

	@Test
	public void commentAddLikeSuccess() {
		//given
		final Comment comment = new Comment();
		setComment(comment, TEST_COMMENT_CONTENT);

		//when
		comment.addLike();

		//then
		assertEquals(TEST_LIKE_COUNT + 1L, comment.getLikeCount());
	}

	@Test
	public void commentMinusLikeSuccess() {
		//given
		final Comment comment = new Comment();
		setComment(comment, TEST_COMMENT_CONTENT);

		//when
		comment.minusLike();

		//then
		assertEquals(TEST_LIKE_COUNT - 1L, comment.getLikeCount());
	}

	@Test
	public void recipeUpdateSuccess() {
		//given
		final Comment comment = new Comment();
		setComment(comment, TEST2_COMMENT_CONTENT);

		//when
		comment.update(TEST2_COMMENT_CONTENT);

		//then
		assertEquals(TEST2_COMMENT_CONTENT, comment.getContent());
	}

	private <T> void setComment (T entity, String content){
		ReflectionTestUtils.setField(entity, "content", content);
	}

}
