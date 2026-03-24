package com.learnify.lms.domain.repository;

import com.learnify.lms.domain.model.Banner;
import java.util.UUID;

public interface BannerRepository {
  Banner save(Banner banner);

  void deleteById(UUID id);
}
