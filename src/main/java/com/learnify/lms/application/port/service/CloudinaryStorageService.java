package com.learnify.lms.application.port.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.learnify.lms.application.port.out.IStorageService;
import com.learnify.lms.common.exception.ErrorCode;
import com.learnify.lms.common.exception.FileUploadException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service("cloudinaryStorageService")
@RequiredArgsConstructor
public class CloudinaryStorageService implements IStorageService {

  private static final long MAX_FILE_SIZE = 100 * 1024 * 1024; // 100MB

  private final Cloudinary cloudinary;

  @Override
  public String upload(MultipartFile file, String folder) {

    validateFile(file);

    try {

      String resourceType = detectResourceType(file.getContentType());

      String publicId = UUID.randomUUID().toString();

      Map<String, Object> uploadParams =
          ObjectUtils.asMap(
              "folder", folder,
              "public_id", publicId,
              "resource_type", resourceType,
              "overwrite", false);

      if ("video".equals(resourceType)) {
        uploadParams.put("chunk_size", 6000000);
        uploadParams.put("eager", "sp_full_hd");
        uploadParams.put("eager_async", true);
      }

      Map<String, Object> uploadResult =
          (Map<String, Object>) cloudinary.uploader().upload(file.getBytes(), uploadParams);

      String secureUrl = (String) uploadResult.get("secure_url");

      return secureUrl;

    } catch (IOException e) {

      log.error("Failed to upload file to Cloudinary", e);

      throw new FileUploadException(ErrorCode.FILE_UPLOAD_ERROR);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void delete(String fileUrl) {

    if (fileUrl == null || fileUrl.isBlank()) {
      return;
    }

    try {

      String publicId = extractPublicId(fileUrl);

      if (publicId == null) {
        return;
      }

      String resourceType = detectResourceTypeFromUrl(fileUrl);

      Map<String, Object> result =
          cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", resourceType));

      log.info("Delete result: {}", result);

    } catch (Exception e) {
      log.error("Failed to delete file from Cloudinary. url={}", fileUrl, e);
      throw new FileUploadException(ErrorCode.FILE_DELETE_ERROR, e);
    }
  }

  private void validateFile(MultipartFile file) {

    if (file == null || file.isEmpty()) {
      throw new FileUploadException(ErrorCode.INVALID_FILE_FORMAT);
    }

    if (file.getSize() > MAX_FILE_SIZE) {
      throw new FileUploadException(ErrorCode.FILE_SIZE_EXCEEDED);
    }
  }

  private String detectResourceType(String contentType) {

    if (contentType == null) {
      return "auto";
    }

    if (contentType.startsWith("video/")) {
      return "video";
    }

    if (contentType.startsWith("image/")) {
      return "image";
    }

    if (contentType.startsWith("application/")) {
      return "raw";
    }

    return "auto";
  }

  private String detectResourceTypeFromUrl(String url) {

    if (url.contains("/video/")) {
      return "video";
    }

    if (url.contains("/raw/")) {
      return "raw";
    }

    return "image";
  }

  private String extractPublicId(String fileUrl) {

    try {

      int uploadIndex = fileUrl.indexOf("/upload/");

      if (uploadIndex == -1) {
        return null;
      }

      String path = fileUrl.substring(uploadIndex + 8);

      int versionIndex = path.indexOf("/");

      if (versionIndex != -1) {
        path = path.substring(versionIndex + 1);
      }

      int extensionIndex = path.lastIndexOf(".");

      if (extensionIndex != -1) {
        return path.substring(0, extensionIndex);
      }

      return path;

    } catch (Exception e) {
      log.warn("Cannot extract Cloudinary publicId from url={}", fileUrl, e);
      return null;
    }
  }
}
