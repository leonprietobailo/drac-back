package com.leonbros.drac.port;

import java.nio.file.Path;
public interface EmailGateway {
  void sendSimpleMail(String to, String subject, String body);

  void sendHtmlMail(String to, String subject, String htmlBody, Path... attachments);
}
