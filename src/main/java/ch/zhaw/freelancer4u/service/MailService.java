package ch.zhaw.freelancer4u.service;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


import ch.zhaw.freelancer4u.model.Mail;

@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    private final static String FROM_MAIL = "se2.zhaw@outlook.com";

    /**
     * Setups the mail configuraiton.
     * @return configured mail sender.
     */
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.office365.com");
        mailSender.setPort(587);
        
        mailSender.setUsername(FROM_MAIL);
        mailSender.setPassword("KNR7ZHsFThk5eav");
        
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        
        return mailSender;
    }

    public boolean sendMail(Mail mail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage(); 
            message.setFrom(FROM_MAIL);
            message.setTo(mail.getTo()); 
            message.setSubject(mail.getSubject()); 
            message.setText(mail.getMessage());

            var emailSender = getJavaMailSender();
            emailSender.send(message);
            return true;

        } catch (Exception e) {
            logger.error("Error sending the mail", e);
        }  
        return false;
    }
}