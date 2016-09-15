package ru.sbt.bit.ood.solid.homework.mail;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailService {

    // now when the report is built we need to send it to the recipients list
    private final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    public void sendMail(String recipients, String report) {
        // we're going to use google mail to send this message
        mailSender.setHost("mail.google.com");
        // construct the message
        MimeMessage message = constructMessage(recipients, report);
        // send the message
        mailSender.send(message);
    }

    private MimeMessage constructMessage(String recipients, String report) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipients);
            // setting message text, last parameter 'true' says that it is HTML format
            helper.setText(report, true);
            helper.setSubject("Monthly department salary report");
            return message;
        } catch (MessagingException e) {
            throw new RuntimeException("Mail service exception: " + e.getMessage(), e);
        }
    }
}
