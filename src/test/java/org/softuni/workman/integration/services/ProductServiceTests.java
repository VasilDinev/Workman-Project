package org.softuni.workman.integration.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.workman.domain.entities.Comment;
import org.softuni.workman.domain.entities.Workman;
import org.softuni.workman.domain.models.service.CommentServiceModel;
import org.softuni.workman.domain.models.service.WorkmanServiceModel;
import org.softuni.workman.repository.WorkmanRepository;
import org.softuni.workman.service.WorkmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {
    @Autowired
    private WorkmanService service;

    @MockBean
    private WorkmanRepository mockWorkmanRepository;

    @Test
    public void createWorkman_whenValid_productCreated() {
        WorkmanServiceModel workmanServiceModel = new WorkmanServiceModel();
        workmanServiceModel.setComments((List<Comment>) new CommentServiceModel());
        when(mockWorkmanRepository.save(any()))
                .thenReturn(new Workman());


        service.createWorkman(workmanServiceModel);
        verify(mockWorkmanRepository)
              .save(any());
    }
}
