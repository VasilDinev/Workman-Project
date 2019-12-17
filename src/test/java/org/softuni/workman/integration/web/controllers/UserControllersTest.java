package org.softuni.workman.integration.web.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.workman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllersTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void login_returnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/users/login"))
                .andExpect(view().name("user/login"));

    }
    @Test
    public void register_returnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/users/register"))
                .andExpect(view().name("user/register"));

    }

    @Test
    public void register_registersUserUserCorrectly() throws Exception {
        this.mvc
                .perform(
                        post("/users/register")
                                .param("username", "Pesho")
                                .param("password", "1")
                                .param("confirmPassword", "1")
                                .param("email", "Pesho@abv.bg"));

        Assert.assertEquals(1,this.userRepository.count());

    }
    @Test
    public void register_registersRedirectCorrectly() throws Exception {
        this.mvc
                .perform(
                        post("/users/register")
                                .param("username", "Pesho")
                                .param("password", "1")
                                .param("confirmPassword", "1")
                                .param("email", "Pesho@abv.bg"))

                              .andExpect(view().name("redirect:/login"));

    }
}
