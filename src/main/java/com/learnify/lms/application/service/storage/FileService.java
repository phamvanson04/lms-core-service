package com.learnify.lms.application.service.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {
  private final IStorageService storageService;

  public String uploadAvatar(MultipartFile file) {
    return storageService.upload(file, "avatars");
  }

  public String uploadVideo(MultipartFile file) {
    return storageService.upload(file, "videos");
  }

  public void deleteFile(String fileUrl) {
    storageService.delete(fileUrl);
  }
}

