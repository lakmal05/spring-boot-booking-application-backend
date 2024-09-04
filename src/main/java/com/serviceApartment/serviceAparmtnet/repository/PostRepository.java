package com.serviceApartment.serviceAparmtnet.repository;

import com.serviceApartment.serviceAparmtnet.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

