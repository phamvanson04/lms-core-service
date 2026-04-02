package com.learnify.lms.repository;

import com.learnify.lms.model.Banner;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBannerRepository extends JpaRepository<Banner, UUID> {}
