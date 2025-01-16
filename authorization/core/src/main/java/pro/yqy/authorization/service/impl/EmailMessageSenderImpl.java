package pro.yqy.authorization.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pro.yqy.authorization.model.bean.message.MessageDTO;
import pro.yqy.authorization.service.MessageSender;

@Slf4j
@Service("emailMessageSender")
@AllArgsConstructor
public class EmailMessageSenderImpl implements MessageSender {

    private final JavaMailSender javaMailSender;

    @Override
    public void send(MessageDTO message) {
        log.info("Sending email message to: {}", message.getTo());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(message.getTo());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getBody());

        javaMailSender.send(mailMessage);
        log.info("Email sent successfully to: {}", message.getTo());
    }
}
