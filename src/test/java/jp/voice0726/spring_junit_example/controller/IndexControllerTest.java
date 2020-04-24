package jp.voice0726.spring_junit_example.controller;

import jp.voice0726.spring_junit_example.helper.WithMockCustomUser;
import jp.voice0726.spring_junit_example.service.StudentService;
import jp.voice0726.spring_junit_example.service.impl.AdminUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = IndexController.class)
@MockBeans({
        @MockBean(AdminUserServiceImpl.class),
        @MockBean(StudentService.class)
})
class IndexControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockCustomUser
    void index() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}
