package com.serviceApartment.serviceAparmtnet.service;

import com.serviceApartment.serviceAparmtnet.model.FileMetadata;
import com.serviceApartment.serviceAparmtnet.repository.FileRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {


    @Value("${server.base-url}")
    private String baseUrl;

    private static final String UPLOAD_DIR = "files/";

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<FileMetadata> upload(MultipartFile[] files) throws IOException {
        List<FileMetadata> fileMetadataList = new ArrayList<>();

        for (MultipartFile file : files) {
            // Check if the file is valid
            String originalFilename = file.getOriginalFilename();
            if (!isValidFile(originalFilename)) {
                throw new IllegalArgumentException("Invalid file type");
            }

            // Generate unique filename
            String uniqueFilename = UUID.randomUUID().toString() + getExtension(originalFilename);

            // Define paths
            Path originalPath = Paths.get(UPLOAD_DIR, "original", uniqueFilename);
            Path smallPath = Paths.get(UPLOAD_DIR, "small", uniqueFilename);
            Path mediumPath = Paths.get(UPLOAD_DIR, "medium", uniqueFilename);
            Path largePath = Paths.get(UPLOAD_DIR, "large", uniqueFilename);

            // Create directories if they don't exist
            Files.createDirectories(originalPath.getParent());
            Files.createDirectories(smallPath.getParent());
            Files.createDirectories(mediumPath.getParent());
            Files.createDirectories(largePath.getParent());

            // Save the original file
            Files.copy(file.getInputStream(), originalPath);

            // Compress and save different sizes
            compressAndSave(file, smallPath, 200, 200);
            compressAndSave(file, mediumPath, 400, 400);
            compressAndSave(file, largePath, 800, 800);

            // Save metadata in database
            FileMetadata metadata = new FileMetadata();
            metadata.setOriginalName(originalFilename);
            metadata.setOriginalPath(baseUrl + "/original/" + uniqueFilename);
            metadata.setSmallPath(baseUrl + "/small/" + uniqueFilename);
            metadata.setMediumPath(baseUrl + "/medium/" + uniqueFilename);
            metadata.setLargePath(baseUrl + "/large/" + uniqueFilename);

            fileRepository.save(metadata);
            fileMetadataList.add(metadata);
        }

        return fileMetadataList;
    }

    private boolean isValidFile(String filename) {
        String extension = getExtension(filename);
        return extension.equalsIgnoreCase(".jpg") ||
                extension.equalsIgnoreCase(".png") ||
                extension.equalsIgnoreCase(".mp4");
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }

    private void compressAndSave(MultipartFile file, Path path, int width, int height) throws IOException {
        if (file.getContentType().startsWith("image")) {
            Thumbnails.of(file.getInputStream())
                    .size(width, height)
                    .toFile(path.toFile());
        } else {
            // For non-image files, simply copy without resizing
            Files.copy(file.getInputStream(), path);
        }
    }



    public Resource loadFile(String size, String filename) throws Exception {
        Path filePath = Paths.get(UPLOAD_DIR, size, filename);
        System.out.println("Loading file from: " + filePath.toString());
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new Exception("File not found");
        }
    }
}
