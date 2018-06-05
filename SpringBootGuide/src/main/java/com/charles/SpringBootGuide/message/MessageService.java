package com.charles.SpringBootGuide.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
