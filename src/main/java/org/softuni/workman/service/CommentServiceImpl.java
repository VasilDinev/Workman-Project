package org.softuni.workman.service;


import org.modelmapper.ModelMapper;
import org.softuni.workman.domain.entities.Comment;
import org.softuni.workman.domain.entities.User;
import org.softuni.workman.domain.entities.Workman;
import org.softuni.workman.domain.models.service.CommentServiceModel;
import org.softuni.workman.error.CommentNotFoundException;
import org.softuni.workman.error.IllegalOperationException;
import org.softuni.workman.repository.CommentRepository;
import org.softuni.workman.repository.UserRepository;
import org.softuni.workman.repository.WorkmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final WorkmanRepository workmanRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, UserRepository userRepository, WorkmanRepository workmanRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.workmanRepository = workmanRepository;
    }


    @Override
    public CommentServiceModel createComment(CommentServiceModel commentServiceModel) {

        Comment comment=this.modelMapper.map(commentServiceModel, Comment.class);


        comment=this.commentRepository.save(comment);

        return this.modelMapper.map(comment, CommentServiceModel.class);
    }

    @Override
    public CommentServiceModel getCommentByUsername(String userName) {
        Comment comment=this.commentRepository.findByUser_Username(userName).orElseThrow(() -> new CommentNotFoundException("Comment not found!"));

        return this.modelMapper.map(comment, CommentServiceModel.class);
    }

    @Override
    public CommentServiceModel findCommentByTitle(String title) {
        return this.commentRepository.findCommentByTitle(title)
                .map(q -> this.modelMapper.map(q, CommentServiceModel.class))
                .orElseThrow(() -> new CommentNotFoundException("Comment not found!"));
    }

    @Override
    public List<CommentServiceModel> findCommentsByAuthor(String author) {
        return this.commentRepository.findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, CommentServiceModel.class))
                .filter(q -> q.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentServiceModel> findAllComments() {
        return this.commentRepository.findAll()
                .stream()
                .map(q -> this.modelMapper.map(q, CommentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentServiceModel findCommentById(String id) {
        return this.commentRepository.findById(id)
                .map(q -> this.modelMapper.map(q, CommentServiceModel.class))
                .orElseThrow(() -> new CommentNotFoundException("Comment not found!"));
    }

    @Override
    public void deleteComment(String id) {
        Comment comment = this.commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found!"));

        this.commentRepository.delete(comment);
    }

    @Override
    public CommentServiceModel editComment(String id, CommentServiceModel commentServiceModel) {
        Comment comment = this.commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found!"));
        comment.setTitle(commentServiceModel.getTitle());
        comment.setAuthor(commentServiceModel.getAuthor());
        comment.setDescription(commentServiceModel.getDescription());
        comment.setImageUrl(commentServiceModel.getImageUrl());
        return this.modelMapper.map(this.commentRepository.saveAndFlush(comment), CommentServiceModel.class);

    }

    @Override
    public List<CommentServiceModel> findAllByUser(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Comment> comments = this.commentRepository
                .findAllByUser_Id(user.getId());

        return comments.stream()
                .map(this::getCommentServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public CommentServiceModel findCommentByIdAndUser(String commentId, String name) {
        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("User not found"));

        User user = getUser(name);

        if(!comment.getUser().getId().equals(user.getId())){
            throw new IllegalOperationException("Operation not allowed.");
        }

        return this.getCommentServiceModel(comment);
    }

    @Override
    public List<CommentServiceModel> findAllByWorkman(String workmanId) {
        List<Comment> events = this.commentRepository.findAllByWorkman_Id(workmanId);

        return this.getProcessedComment(events);
    }

    private User getUser(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    private CommentServiceModel getCommentServiceModel(Comment e) {
        CommentServiceModel csm = this.modelMapper.map(e, CommentServiceModel.class);

        return csm;
    }
    private List<CommentServiceModel> getProcessedComment(List<Comment> events) {
        return events
                .stream()

                .map(this::getCommentServiceModel)
                .collect(Collectors.toList());
    }

}
