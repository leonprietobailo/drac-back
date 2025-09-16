package com.leonbros.drac.component;

import com.leonbros.drac.port.EmailGateway;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Slf4j
@Component
@Getter
public class AppMailSender implements EmailGateway {

  private final String from;
  private final JavaMailSender mailSender;

  @Autowired
  public AppMailSender(@Value("${spring.mail.username}") String from, JavaMailSender mailSender) {
    this.from = from;
    this.mailSender = mailSender;
  }

  @Override
  @Async("mailExecutor")
  public void sendSimpleMail(String to, String subject, String body) {
    final SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);
    mailSender.send(message);
  }

  @Override
  @Async("mailExecutor")
  public void sendHtmlMail(String to, String subject, String htmlBody, Path... attachments) {
    try {
      final MimeMessage mimeMessage = mailSender.createMimeMessage();
      final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(htmlBody, true);
      for (Path attachment : attachments) {
        helper.addAttachment(attachment.getFileName().toString(), attachment.toFile());
      }
      mailSender.send(mimeMessage);
    } catch (MessagingException e) {
      log.error("Failed to send HTML email from={} to={} subject={}. Error: {}", from, to, subject,
          e.getMessage(), e);
    }
  }

}
