package com.networkmonitor.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotificationService {
    private String smtpHost;
    private String smtpPort;
    private String smtpEmail;
    private String smtpPassword;
    private String securityProtocol;

    public NotificationService(String smtpHost, String smtpPort, String smtpEmail, String smtpPassword, String securityProtocol) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.smtpEmail = smtpEmail;
        this.smtpPassword = smtpPassword;
        this.securityProtocol = securityProtocol;
    }

    public void sendEmail(String recipient, String subject, String body) {
        // Configure SMTP properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", "true");

        // Set security protocol
        if ("SSL".equalsIgnoreCase(securityProtocol)) {
            properties.put("mail.smtp.socketFactory.port", smtpPort);
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        } else if ("TLS".equalsIgnoreCase(securityProtocol)) {
            properties.put("mail.smtp.starttls.enable", "true");
        }

        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpEmail, smtpPassword);
            }
        });

        try {
            // Create a MimeMessage
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(smtpEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully to: " + recipient);
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}