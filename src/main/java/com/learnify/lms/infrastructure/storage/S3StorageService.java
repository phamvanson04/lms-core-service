package com.learnify.lms.infrastructure.storage;

import com.learnify.lms.application.port.output.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("s3StorageService")
public class S3StorageService implements StorageService {
  @Override
  public String upload(MultipartFile file, String folder) {
    return "";
  }

  @Override
  public void delete(String fileUrl) {}
}
