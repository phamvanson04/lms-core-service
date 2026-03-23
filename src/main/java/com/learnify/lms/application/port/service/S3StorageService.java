package com.learnify.lms.application.port.service;

import com.learnify.lms.application.port.out.IStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("s3StorageService")
public class S3StorageService implements IStorageService {
  @Override
  public String upload(MultipartFile file, String folder) {
    return "";
  }

  @Override
  public void delete(String fileUrl) {}
}
