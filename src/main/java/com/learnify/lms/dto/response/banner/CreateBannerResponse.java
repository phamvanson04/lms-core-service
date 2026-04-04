package com.learnify.lms.dto.response.banner;

import java.util.UUID;
import lombok.Getter;

@Getter
public class CreateBannerResponse {
  private UUID bannerId;
  private String title;
  private String description;
  private String imgBanner;
  private String startDate;
  private String endDate;
  private String urlBanner;
}
