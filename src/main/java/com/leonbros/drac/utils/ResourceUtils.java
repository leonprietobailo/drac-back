package com.leonbros.drac.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
public class ResourceUtils {

  public static String loadResourceAsString(String resourcePath) throws IOException {
    try (InputStream input = ResourceUtils.class.getClassLoader()
        .getResourceAsStream(resourcePath)) {
      if (input == null) {
        throw new IllegalArgumentException("Resource not found: " + resourcePath);
      }
      return new String(input.readAllBytes(), StandardCharsets.UTF_8);
    }
  }

}
