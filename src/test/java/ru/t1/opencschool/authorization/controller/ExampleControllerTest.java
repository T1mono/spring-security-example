package ru.t1.opencschool.authorization.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тесты ExampleController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testExampleWithAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/example"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, world!"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testExampleAdminWithAdminUser() throws Exception {
        mockMvc.perform(get("/example/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, admin!"));
    }

    @Test
    @WithAnonymousUser
    public void testExampleWithAnonymousUser() throws Exception {
        mockMvc.perform(get("/example"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void testGetAdminWithAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/example/get-admin"))
                .andExpect(status().isForbidden());
    }
}