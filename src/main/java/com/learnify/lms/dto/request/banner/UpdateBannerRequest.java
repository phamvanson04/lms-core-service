package com.learnify.lms.dto.request.banner;

import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;

public class UpdateBannerRequest {
  private String title;
  private String description;
  private MultipartFile imgBanner;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
