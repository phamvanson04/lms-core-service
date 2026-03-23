package com.learnify.lms.application.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
  String upload(MultipartFile file, String folder);

  void delete(String fileUrl);
}

