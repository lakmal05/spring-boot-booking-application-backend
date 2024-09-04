package com.serviceApartment.serviceAparmtnet.repository;

import com.serviceApartment.serviceAparmtnet.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentRepository extends JpaRepository<Comment, Long> {
}