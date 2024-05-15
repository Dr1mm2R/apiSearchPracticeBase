package com.example.apisearchpracticebase.Services;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Properties;

public class EmailSenderAppender extends AppenderBase<ILoggingEvent> {
    public JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("gusdimus1@gmail.com");
        mailSender.setPassword("rxcf lnws pscw dzvb");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        String temp = eventObject.getLevel().toString();
        if(temp.equals("WARN")){
            try {
                JavaMailSender javaMailSender = javaMailSender();
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setTo("gusdimus1@gmail.com");
                helper.setSubject("Критическая ошибка Api");
                helper.setText(eventObject.getFormattedMessage());
                javaMailSender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}