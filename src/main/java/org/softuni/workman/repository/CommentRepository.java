package org.softuni.workman.repository;

import org.softuni.workman.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    Optional<Comment> findCommentByTitle(String title);

    List<Comment> findAllByUser_Id(String creatorId);

    List<Comment> findAllByWorkman_Id(String workmanId);

    Optional<Comment> findByUser_Username(String username);
}
