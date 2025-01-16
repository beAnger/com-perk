package pro.yqy.authorization.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pro.yqy.authorization.constant.AuthorizationError;
import pro.yqy.authorization.model.bean.message.MessageDTO;
import pro.yqy.authorization.service.MessageSender;
import pro.yqy.component.web.exception.RestException;

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

        try {
            javaMailSender.send(mailMessage);
            log.info("Email sent successfully to: {}", message.getTo());
        } catch (Exception e) {
            log.error("Failed to send email to: {}", message.getTo(), e);
            throw new RestException(AuthorizationError.verify_code_send_failed);
        }
    }
}
