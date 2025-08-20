package com.leonbros.drac.port;

public interface EmailGateway {
  void sendSimpleMail(String to, String subject, String body);

  void sendHtmlMail(String to, String subject, String htmlBody);
}
