package pro.yqy.authorization.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.yqy.authorization.model.bean.message.MessageDTO;
import pro.yqy.authorization.service.MessageSender;

@Slf4j
@Service("smsMessageSender")
public class SmsMessageSenderImpl implements MessageSender {

    @Override
    public void send(MessageDTO message) {
        log.info("send sms message");
    }
}
