package com.shinkle.SpringRestTutorial.greeting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GreetingController.class)
class GreetingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldGetUserWhenCalled() throws Exception {
        mockMvc.perform(get("/greeting")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("content").exists());
    }

    @Test
    void shouldAddNameToGreeting() throws Exception {
        mockMvc.perform(get("/greeting")
                .param("name", "Lucas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("content").value("Hello, Lucas!"));
    }

    @Test
    void shouldIncrementCounterForEachCall() throws Exception {
        ResultActions firstCall = mockMvc.perform(get("/greeting").contentType(MediaType.APPLICATION_JSON));
        ResultActions secondCall = mockMvc.perform(get("/greeting").contentType(MediaType.APPLICATION_JSON));

        firstCall
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("id").value("1"));

        secondCall
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("id").value("2"));
    }
}