package com.serviceApartment.serviceAparmtnet.model;

import com.serviceApartment.serviceAparmtnet.repository.ImageRepository;
import com.serviceApartment.serviceAparmtnet.repository.PostRepository;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "commented_id")
    private Long commentedId;


    @Column(name = "commented_type")
    private String commentedType;

    @Transient
    private Object commentedObject;

    @Autowired
    private transient PostRepository postRepository;

    @Autowired
    private transient ImageRepository imageRepository;


    @PostLoad
    public void loadCommentedObject() {
        if ("Post".equals(commentedType)) {
            this.commentedObject = postRepository.findById(commentedId).orElse(null);
        } else if ("Image".equals(commentedType)) {
            this.commentedObject = imageRepository.findById(commentedId).orElse(null);
        }
    }

    // Getters and Setters
}
