package com.charles.SpringBootGuide.message;

import org.springframework.beans.factory.annotation.Autowired;

public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    void saveMessage(Message message){
        messageRepository.saveMessage(message);
    }

    Message getMessage(Integer messageId){
        return messageRepository.getMessage(messageId);
    }
}
