package com.example.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * Класс отправления сообщения по почте при оформлении корзины.
 */
@Service
@Transactional
public class EmailService {

    /**
     * Метод производит вход в почту через логин, пароль, после чего формирует сообщение при помощи параметра message
     * (тема сообщения указана в проекте "Создание заказа") и отправляет сообщение на почту, указанную в профиле
     * пользователя
     * @param message сообщение, содержащее список товаров в корзине и их количество.
     * @param email почта покупателя.
     * @throws AddressException - Класс исключений, возникающих при обнаружении неправильно отформатированного адреса.
     * @throws MessagingException - Базовый класс для всех исключений, создаваемых классами обмена сообщениями.
     * @throws IOException - Общий класс исключений, создаваемых неудачными или прерванными операциями ввода-вывода.
     */
    @Async
    public void sendmail(String message, String email) throws AddressException, MessagingException, IOException {
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

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        msg.setSubject("Создание заказа");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Ваш заказ {" + message + '}' + " успешно создан", "text/html; charset=UTF-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}