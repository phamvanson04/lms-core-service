package com.learnify.lms.util;

import com.learnify.lms.repository.JpaUserRepository;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserHelper {
  private static final SecureRandom RANDOM = new SecureRandom();
  private static final String PREFIX = "llx";
  private static final int RANDOM_LENGTH = 5;
  private static final int MAX_ATTEMPTS = 5;

  private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z][a-z0-9_.-]{5,30}$");

  public static String generateUsername(String fullName) {
    String base = normalize(fullName);

    if (base.isBlank()) {
      base = "user";
    }

    return PREFIX + "." + base + randomDigits(RANDOM_LENGTH);
  }

  public static String generateUniqueUsername(String fullName, JpaUserRepository repository) {
    String username = generateUsername(fullName);
    int attempts = 0;

    while (repository.existsByUsername(username) && attempts < MAX_ATTEMPTS) {
      username = generateUsername(fullName);
      attempts++;
    }

    if (repository.existsByUsername(username)) {
      username = username + "_" + UUID.randomUUID().toString().substring(0, 6);
    }

    return username;
  }

  private static String normalize(String input) {
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
    normalized = normalized.replaceAll("\\p{M}", "");
    normalized = normalized.toLowerCase();
    normalized = normalized.replaceAll("[^a-z]", "");
    return normalized;
  }

  private static String randomDigits(int length) {
    int bound = (int) Math.pow(10, length);
    return String.format("%0" + length + "d", RANDOM.nextInt(bound));
  }

  public static boolean isValid(String username) {
    return username != null && USERNAME_PATTERN.matcher(username).matches();
  }
}
