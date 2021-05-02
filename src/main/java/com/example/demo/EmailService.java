package com.example.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
@Transactional
public class EmailService {

    @Async
    public void sendmail(Object s) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bigredbear37@gmail.com", "mxtpxqwudijmrcjr");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("bigredbear37@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("bigredbear37@gmail.com"));
        msg.setSubject("DemoApplication. Создание объекта");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Object " + s.toString() + " was successfully added", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}