/*
 * package com.board.controller;
 * 
 * import static
 * org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 * 
 * import org.junit.jupiter.api.Test; import org.mockito.Mockito; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
 * import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
 * import org.springframework.boot.test.mock.mockito.MockBean; import
 * org.springframework.http.MediaType; import
 * org.springframework.security.test.context.support.WithMockUser; import
 * org.springframework.test.web.servlet.MockMvc;
 * 
 * import com.board.service.CommentService;
 * 
 * @WebMvcTest(CommentController.class)
 * 
 * @AutoConfigureMockMvc(addFilters = false) // Security 필터 비활성화 public class
 * CommentControllerTest {
 * 
 * @Autowired private MockMvc mockMvc;
 * 
 * @MockBean private CommentService commentService;
 * 
 * @Test
 * 
 * @WithMockUser(username = "testUser") // 인증된 사용자 추가 void testRegisterComment()
 * throws Exception { String content = "This is a test comment";
 * 
 * mockMvc.perform(post("/comments") .param("postId", "1") .param("userId",
 * "testUser") .contentType(MediaType.TEXT_PLAIN) .content(content))
 * .andExpect(status().isOk())
 * .andExpect(content().string("Comment created successfully"));
 * 
 * Mockito.verify(commentService, Mockito.times(1)) .registerComment(1L,
 * "testUser", content); } }
 */