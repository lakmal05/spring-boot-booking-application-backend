package com.serviceApartment.serviceAparmtnet.controller;

import com.serviceApartment.serviceAparmtnet.model.Comment;
import com.serviceApartment.serviceAparmtnet.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<Comment> createCommentForPost(@PathVariable Long postId, @RequestBody String content) {
        Comment comment = commentService.createCommentForPost(postId, content);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/image/{imageId}")
    public ResponseEntity<Comment> createCommentForImage(@PathVariable Long imageId, @RequestBody String content) {
        Comment comment = commentService.createCommentForImage(imageId, content);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable Long commentId) {
        var data= commentService.getComment(commentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        System.out.println(data);
        return  data;
    }


}
