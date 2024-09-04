package com.serviceApartment.serviceAparmtnet.service;

import com.serviceApartment.serviceAparmtnet.model.Comment;
import com.serviceApartment.serviceAparmtnet.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.serviceApartment.serviceAparmtnet.repository.ImageRepository;
import com.serviceApartment.serviceAparmtnet.repository.PostRepository;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageRepository imageRepository;

    // Create a comment for a post
    public Comment createCommentForPost(Long postId, String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCommentedId(postId);
        comment.setCommentedType("Post");
        return commentRepository.save(comment);
    }

    // Create a comment for an image
    public Comment createCommentForImage(Long imageId, String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCommentedId(imageId);
        comment.setCommentedType("Image");
        return commentRepository.save(comment);
    }

    // Get a comment and resolve its associated object
    public Optional<Comment> getComment(Long commentId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);

        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            if ("Post".equals(comment.getCommentedType())) {
                comment.setCommentedObject(postRepository.findById(comment.getCommentedId()).orElse(null));
            } else if ("Image".equals(comment.getCommentedType())) {
                comment.setCommentedObject(imageRepository.findById(comment.getCommentedId()).orElse(null));
            }
            return Optional.of(comment);
        }

        return Optional.empty();
    }
}
