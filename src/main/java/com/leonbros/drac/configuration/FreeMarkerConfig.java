package com.leonbros.drac.configuration;

import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
@Configuration
public class FreeMarkerConfig {

  @Bean
  public freemarker.template.Configuration provideConfig() throws IOException {
    final freemarker.template.Configuration cfg =
        new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_34);
    cfg.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates");
    cfg.setDefaultEncoding("UTF-8");
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    return cfg;
  }

}
