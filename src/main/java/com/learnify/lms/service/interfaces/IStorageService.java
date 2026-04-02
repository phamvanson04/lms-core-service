package com.learnify.lms.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
  String upload(MultipartFile file, String folder);

  void delete(String fileUrl);
}
