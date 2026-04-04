package com.learnify.lms.service;

import com.learnify.lms.exception.AppException;
import com.learnify.lms.exception.ErrorCode;
import com.learnify.lms.exception.FileUploadException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiBackgroundRemovalService {
  @Qualifier("aiServiceWebClient")
  private final WebClient aiServiceWebClient;

  public byte[] removeBackground(MultipartFile file) {
    validateImage(file);

    byte[] fileBytes;
    try {
      fileBytes = file.getBytes();
    } catch (IOException e) {
      throw new FileUploadException(ErrorCode.FILE_UPLOAD_ERROR, e);
    }

    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder
        .part(
            "file",
            new ByteArrayResource(fileBytes) {
              @Override
              public String getFilename() {
                return file.getOriginalFilename() != null ? file.getOriginalFilename() : "avatar.png";
              }
            })
        .contentType(resolveContentType(file.getContentType()));

    try {
      byte[] response =
          aiServiceWebClient
              .post()
              .uri("/internal/remove-bg")
              .contentType(MediaType.MULTIPART_FORM_DATA)
              .body(BodyInserters.fromMultipartData(builder.build()))
              .retrieve()
              .bodyToMono(byte[].class)
              .block();

      if (response == null || response.length == 0) {
        throw new AppException(ErrorCode.EXTERNAL_SERVICE_ERROR, "AI service returned empty data");
      }

      return response;
    } catch (WebClientResponseException ex) {
      if (ex.getStatusCode() == HttpStatus.PAYLOAD_TOO_LARGE) {
        throw new FileUploadException(
            ErrorCode.FILE_SIZE_EXCEEDED, "File too large. Max 50MB.");
      }
      String message = ex.getResponseBodyAsString();
      if (message == null || message.isBlank()) {
        message = "AI service error";
      }
      throw new AppException(ErrorCode.EXTERNAL_SERVICE_ERROR, message);
    } catch (Exception ex) {
      throw new AppException(ErrorCode.EXTERNAL_SERVICE_ERROR, ex);
    }
  }

  private void validateImage(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new FileUploadException(ErrorCode.INVALID_FILE_FORMAT);
    }
    String contentType = file.getContentType();
    if (contentType == null || !contentType.startsWith("image/")) {
      throw new FileUploadException(ErrorCode.INVALID_FILE_FORMAT);
    }
  }

  private MediaType resolveContentType(String contentType) {
    if (contentType == null || contentType.isBlank()) {
      return MediaType.APPLICATION_OCTET_STREAM;
    }
    return MediaType.parseMediaType(contentType);
  }
}
