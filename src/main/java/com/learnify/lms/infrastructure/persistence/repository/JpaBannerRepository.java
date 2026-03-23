package com.learnify.lms.infrastructure.persistence.repository;

import com.learnify.lms.domain.model.Banner;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBannerRepository extends JpaRepository<Banner, UUID> {}

