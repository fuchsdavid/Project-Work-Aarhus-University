package com.example.habittracker.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.net.PasswordAuthentication;
import java.util.Properties;

public class EmailController {
    public static void sendEmail(String emailAddress, String body, String subject){
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.wp.pl");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.wp.pl");

        // Session creation with authentication
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication("habittracker123@wp.pl", "Nimad4321");
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("habittracker123@wp.pl"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(emailAddress));
            message.setSubject(subject);

            // Email body content
            String msg = body;

            // Create MimeBodyPart
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            // Create Multipart and add MimeBodyPart to it
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            // Set the content of the message
            message.setContent(multipart);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();  // This provides a more detailed error message
        }
    }

    public static void sendWelcomeEmail(String emailAddress, String name){
        String subject = "Welcome " + name + "!";
        String body = "Welcome to Habit Tracker!\n\n" +
                "You have successfully created an account. Now you are ready to use all the " +
                "features our application offers. We wish you a lot of fun and good luck with completing your goals!\n\n" +
                "Habit Tracker Team";
        sendEmail(emailAddress, body, subject);
    }
}
