package pro.yqy.authorization.service;

import pro.yqy.authorization.model.bean.message.MessageDTO;

public interface MessageSender {

    void send(MessageDTO message);

}
