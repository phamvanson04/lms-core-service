package com.learnify.lms.storage;

import com.learnify.lms.service.interfaces.IStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("s3StorageService")
public class S3IStorageService implements IStorageService {
  @Override
  public String upload(MultipartFile file, String folder) {
    return "";
  }

  @Override
  public void delete(String fileUrl) {}
}
