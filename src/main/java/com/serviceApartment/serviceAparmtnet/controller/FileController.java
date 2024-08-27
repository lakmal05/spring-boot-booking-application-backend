package com.serviceApartment.serviceAparmtnet.controller;

import com.serviceApartment.serviceAparmtnet.model.FileMetadata;
import com.serviceApartment.serviceAparmtnet.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileService fileService;


    @PostMapping("/upload")
    public ResponseEntity<List<FileMetadata>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            List<FileMetadata> metadataList = fileService.upload(files);
            return ResponseEntity.ok(metadataList);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{size}/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String size, @PathVariable String filename) {
        try {
            Resource resource = fileService.loadFile(size, filename);
            String contentType = Files.probeContentType(Paths.get(resource.getURI())); // Use the file's actual content type
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(contentType)) // Set content type dynamically
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}