package com.pokez.security.service;

import com.pokez.security.dto.EmailValuesDto;
import com.pokez.security.models.UserModel;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

  @Autowired
  JavaMailSender javaMailSender;
  @Autowired
  TemplateEngine templateEngine;
  @Autowired
  UserService userService;
  @Value("${mail.urlFront}")
  String urlFront;
  @Value("${spring.mail.username}")
  String mailFrom;
  private static final String SUBJECT = "Recover Password";

  public void sendEmail(EmailValuesDto emailDto) {
    MimeMessage message = javaMailSender.createMimeMessage();
    try {
      Map<String, Object> model = new HashMap<>();
      model.put("userName", emailDto.getUserName());
      model.put("url", urlFront + emailDto.getTokenPassword());
      Context context = new Context();
      context.setVariables(model);
      String htmlText = templateEngine.process("email-template", context);
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(emailDto.getMailFrom());
      helper.setTo(emailDto.getMailTo());
      helper.setSubject(emailDto.getSubject());
      helper.setText(htmlText, true);
      javaMailSender.send(message);
    }catch (MessagingException e){
      e.printStackTrace();
    }
  }

  public EmailValuesDto setMailAndToken(EmailValuesDto emailDto, UserModel user){
    String tokenPassword = UUID.randomUUID().toString();
    emailDto.setMailFrom(mailFrom);
    emailDto.setMailTo(user.getEmail());
    emailDto.setSubject(SUBJECT);
    emailDto.setUserName(user.getUserName());
    emailDto.setTokenPassword(tokenPassword);
    user.setTokenPassword(tokenPassword);
    userService.save(user);
    return emailDto;
  }


}