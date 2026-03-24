package com.learnify.lms.application.dto.request.banner;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class CreateBannerRequest {
  private String title;
  private String description;
  private MultipartFile imgBanner;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
