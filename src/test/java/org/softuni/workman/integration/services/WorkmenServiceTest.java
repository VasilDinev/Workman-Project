package org.softuni.workman.integration.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.workman.domain.entities.Workman;
import org.softuni.workman.domain.models.service.WorkmanServiceModel;
import org.softuni.workman.repository.WorkmanRepository;
import org.softuni.workman.service.CloudinaryService;
import org.softuni.workman.service.WorkmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WorkmenServiceTest {
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastNmae";
    private static final String EMAIL = "email@abv.com";
    private static final String JOB_FUNCTION = "TEST JOB Function";
    private static final Integer PHONE_NUMBER = 2222;
    private static final String IMAGE_URL = "http://imageUrl";

    @Autowired
    private WorkmanService workmanService;

    @MockBean
    private WorkmanRepository workmanRepository;

    @MockBean
    private CloudinaryService cloudinaryService;

    private List<Workman> workmen;

    @Before
    public void setupTest() {
        this.workmen = new ArrayList<>();
        when(this.workmanRepository.findAll())
                .thenReturn(this.workmen);
    }

//    @Test
//    public void CreateWorkman_whenValidImage_productCreated() throws IOException {
//        WorkmanServiceModel workmanServiceModel = this.workmanService.createWorkman(new WorkmanServiceModel());
//        this.cloudinaryService.uploadImage(any());
//        workmanServiceModel.setImageUrl(IMAGE_URL);
//        workmanServiceModel.setFirstName();
//
//        verify(this.workmanRepository)
//                .save(any());
//    }
    @Test(expected = Exception.class)
    public void addWorkman_whenNull_throwException() throws IOException {
        this.workmanService.createWorkman(null);

        verify(this.workmanRepository)
                .save(any());
    }
    @Test
    public void findAllWorkman_when1Product_return1Product() {
        Workman workman = new Workman();
        workman.setFirstName(FIRST_NAME);
        workman.setLastName(LAST_NAME);
        workman.setEmail(EMAIL);
        workman.setPhoneNumber(PHONE_NUMBER);
        workman.setJobFunction(JOB_FUNCTION);
        workman.setImageUrl(IMAGE_URL);

        this.workmen.add(workman);

        List<WorkmanServiceModel> actualWorkman = this.workmanService.findAllWorkman();
        WorkmanServiceModel actWorkman = actualWorkman.get(0);

        assertEquals(workman.getFirstName(), actWorkman.getFirstName());
        assertEquals(workman.getLastName(), actWorkman.getLastName());
        assertEquals(workman.getEmail(), actWorkman.getEmail());
        assertEquals(workman.getPhoneNumber(), actWorkman.getPhoneNumber());
        assertEquals(workman.getJobFunction(), actWorkman.getJobFunction());
        assertEquals(workman.getImageUrl(), actWorkman.getImageUrl());
    }


    @Test
    public void findWorkmanById() {
        Workman workman = new Workman();

        when(this.workmanRepository.findById(any()))
                .thenReturn(java.util.Optional.of(workman));
    }




}
