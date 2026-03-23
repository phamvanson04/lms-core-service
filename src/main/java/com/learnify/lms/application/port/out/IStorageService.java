package com.learnify.lms.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
  String upload(MultipartFile file, String folder);

  void delete(String fileUrl);
}
