package com.shinkle.SpringRestTutorial.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.String.valueOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void shouldCreateUser() throws Exception {
        String expectedName = "Lucas";
        User expectedUser = new User(2, expectedName);
        when(userService.create(any())).thenReturn(expectedUser);

        mockMvc.perform(post("/api/new_user")
                .param("name", expectedName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetUser() throws Exception {
        int expectedId = 1;
        String expectedName = "Christian";
        when(userService.findById(anyInt())).thenReturn(new User(expectedId, expectedName));

        mockMvc.perform(get("/api/user")
                .param("id", valueOf(expectedId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("name").value(expectedName))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("id").value(expectedId));
    }

    @Test
    void shouldGet404WhenNoUserForId() throws Exception {
        int unexpectedId = 2;
        when(userService.findById(anyInt()))
                .thenThrow(new UserNotFound("Could not find User with id: " + unexpectedId));
        mockMvc.perform(get("/api/user")
                .param("id", valueOf(unexpectedId)))
                .andExpect(status().isNotFound());
    }
}
