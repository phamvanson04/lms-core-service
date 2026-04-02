package com.learnify.lms.service;

import com.learnify.lms.service.interfaces.IStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileUploadService {
  private final IStorageService storageService;

  public String uploadAvatar(MultipartFile file, String userId) {
    return storageService.upload(file, "avatars/" + userId);
  }

  public String uploadVideo(MultipartFile file) {
    return storageService.upload(file, "videos");
  }

  public void deleteFile(String fileUrl) {
    storageService.delete(fileUrl);
  }
}
