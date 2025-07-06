package com.airtribe.news;

import com.airtribe.news.config.JwtUtil;
import com.airtribe.news.controller.AuthController;
import com.airtribe.news.dto.LoginRequest;
import com.airtribe.news.dto.RegisterRequest;
import com.airtribe.news.entity.User;
import com.airtribe.news.model.JwtResponse;
import com.airtribe.news.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private AuthenticationManager authManager;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testRegister() throws Exception {
        RegisterRequest request = new RegisterRequest("john", "pass123");

        User mockUser = new User();
        mockUser.setUsername("john");

        when(authService.register(any(RegisterRequest.class))).thenReturn(mockUser);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    void testLogin() throws Exception {
        LoginRequest request = new LoginRequest("john", "pass123");

        when(jwtUtil.generateToken("john")).thenReturn("mock-token");

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-token"));
    }
}
