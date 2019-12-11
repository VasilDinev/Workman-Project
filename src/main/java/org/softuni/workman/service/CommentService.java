package org.softuni.workman.service;

import org.softuni.workman.domain.models.service.CommentServiceModel;
import org.softuni.workman.domain.models.view.CommentViewModel;

import java.util.List;

public interface CommentService {

    CommentServiceModel createComment(CommentServiceModel commentServiceModel);
    CommentServiceModel getCommentByUsername(String userName);
    CommentServiceModel findCommentByTitle(String title);
    List<CommentServiceModel> findCommentsByAuthor(String author);

    List<CommentServiceModel> findAllComments();

    CommentServiceModel findCommentById(String id);

    void deleteComment(String id);

    CommentServiceModel editComment(String id, CommentServiceModel commentViewModel);
    List<CommentServiceModel> findAllByUser(String username);
    CommentServiceModel findCommentByIdAndUser(String commentId, String name);
    List<CommentServiceModel> findAllByWorkman(String workmanId);



}
