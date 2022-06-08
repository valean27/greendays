package com.greendays.greendays.service.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    private String to;
    private String from;
    private String username;
    private String password;
    private String host;
    private String subject;
    private String text;

    private MailSender(Builder builder) {
        to = builder.to;
        from = builder.from;
        username = builder.username;
        password = builder.password;
        host = builder.host;
        subject = builder.subject;
        text = builder.text;
    }

    public static class Builder {
        private String to;
        private String from;
        private String username;
        private String password;
        private String host;
        private String subject;
        private String text;

        public Builder to(String value) {
            to = value;
            return this;
        }

        public Builder from(String value) {
            from = value;
            return this;
        }

        public Builder username(String value) {
            username = value;
            return this;
        }

        public Builder password(String value) {
            password = value;
            return this;
        }

        public Builder host(String value) {
            host = value;
            return this;
        }

        public Builder subject(String value) {
            subject = value;
            return this;
        }

        public Builder text(String value) {
            text = value;
            return this;
        }

        public MailSender build() {
            return new MailSender(this);
        }
    }

    public void send() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(text);

            // Send message
            Transport.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

