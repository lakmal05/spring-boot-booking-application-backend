package com.serviceApartment.serviceAparmtnet.repository;

import com.serviceApartment.serviceAparmtnet.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileMetadata, Long> {}