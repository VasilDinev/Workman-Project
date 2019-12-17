package org.softuni.workman.integration.web.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.softuni.workman.domain.entities.Workman;
import org.softuni.workman.repository.UserRepository;
import org.softuni.workman.repository.WorkmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class WorkmanControllerTest {



    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private MockMvc mvc;

    @Autowired
    private WorkmanRepository workmanRepository;

    @Mock
    private Principal principal;


    @Test
    @WithMockUser("spring")
    public void add_saveEntityProperly() throws Exception {
        this.mvc
                .perform(
                        post("/add-workman")
                       .param("firstName", "Pesho")
                       .param("lastName", "Peshev")
                        .param("email", "Peshev@p.com")
                       .param("phoneNumber", "111")
                       .param("jobFunction", "Test")
                     .param("imageUrl", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSomDWcSQ3JNRouEtpd2Y0L1MiPTIpDOBqX3Qkx5p1w6FtfShfd&s")
                );

        Assert.assertEquals(1,this.workmanRepository.count());


    }
    @Test
    @WithMockUser("spring")
    public void add_redirectCorrectly() throws Exception {
        this.mvc
                .perform(
                        post("/workman/add")
                                .param("firstName", "Pesho")
                                .param("lastName", "Peshev")
                                .param("email", "Peshev@p.com")
                                .param("phoneNumber", "111")
                                .param("jobFunction", "Test")
                                .param("imageUrl", "http://res.cloudinary.com/vasil-cloud/image/upload/v1576191655/gdr2gywtzxd1byxq2yco.jpg")
                )

        .andExpect(view().name("redirect:list"));


    }

    @Test
    @WithMockUser("spring")
    public void edit_worksCorrectlt() throws Exception {
        Workman workman=new Workman();
        workman.setFirstName("Pesho");
        workman.setLastName("Peshov");
        workman.setEmail("Peshov@abv.com");
        workman.setPhoneNumber(232);
        workman.setJobFunction("test");
        workman.setImageUrl("http://res.cloudinary.com/vasil-cloud/image/upload/v1576191655/gdr2gywtzxd1byxq2yco.jpg");
       this.workmanRepository.saveAndFlush(workman);
        this.mvc
                .perform(
                        post("/workman/edit"+workman.getId())
                                .param("firstName", "Pesho")
                                .param("lastName", "Pppeshev")
                                .param("email", "Peshev@p.com")
                                .param("phoneNumber", "111")
                                .param("jobFunction", "Test")
                                .param("imageUrl", "http://res.cloudinary.com/vasil-cloud/image/upload/v1576191655/gdr2gywtzxd1byxq2yco.jpg")
                );

                Workman fistActual=this.workmanRepository.findById(workman.getId()).orElse(null);
                Assert.assertEquals("Pppeshev", fistActual.getLastName());


    }
}
