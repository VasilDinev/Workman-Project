package org.softuni.workman.integration.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.softuni.workman.domain.entities.Comment;
import org.softuni.workman.domain.entities.User;
import org.softuni.workman.domain.entities.Workman;
import org.softuni.workman.domain.models.service.CommentServiceModel;
import org.softuni.workman.repository.CommentRepository;
import org.softuni.workman.service.CloudinaryService;
import org.softuni.workman.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentServiceTest {

    private static final String TITLE="Tets";
    private static final String AUTHOR="Test";
    private static final String DESCRIPTION="Test";
    private static final String IMAGE_URL="http://imageUrl";


    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository mockCommentRepository;
    @MockBean
    private CloudinaryService mockCloudinaryService;

    private List<Comment> comments;

    @Before
    public void setupTest() {
        this.comments = new ArrayList<>();
        when(this.mockCommentRepository.findAll())
                .thenReturn(this.comments);
    }
//    @Test
//    public void addComment_whenValidImage_productCreated() throws IOException {
//        CommentServiceModel commentServiceModel = this.commentService.createComment(new CommentServiceModel());
//        this.mockCloudinaryService.uploadImage(any());
//        commentServiceModel.setImageUrl(IMAGE_URL);
//
//        verify(this.mockCommentRepository)
//                .save(any());
//    }

    @Test(expected = Exception.class)
    public void addComment_whenNull_throwException() throws IOException {
        this.commentService.createComment(null);

        verify(this.mockCommentRepository)
                .save(any());
    }

    @Test
    public void findAllComments() {
        Comment comment = new Comment();
        comment.setTitle(TITLE);
        comment.setAuthor(AUTHOR);
        comment.setDescription(DESCRIPTION);
        comment.setImageUrl(IMAGE_URL);


        this.comments.add(comment);

        List<CommentServiceModel> actualComments = this.commentService.findAllComments();
        CommentServiceModel actualComment= actualComments.get(0);
        assertEquals(comment.getTitle(), actualComment.getTitle());
        assertEquals(comment.getAuthor(), actualComment.getAuthor());
        assertEquals(comment.getImageUrl(), actualComment.getImageUrl());
        assertEquals(comment.getDescription(), actualComment.getDescription());


    }


    @Test
    public void findCommentById_whenValidProduct_returnProduct() {
        Comment comment = new Comment();

        when(this.mockCommentRepository.findById(any()))
                .thenReturn(java.util.Optional.of(comment));
    }

    @Test
    public void findCommentById_whenCommentsIsNull_throwException() {
        when(this.mockCommentRepository.findById(null))
                .thenThrow(NullPointerException.class);
    }


}
