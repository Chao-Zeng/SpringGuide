package com.charles.SpringBootGuide.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController{

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public void getAdd(@RequestParam(value = "id", defaultValue = "0") Integer id,
                    @RequestParam(value = "content", defaultValue = "") String content){
        Message message = new Message(id, content);
        messageService.saveMessage(message);
    }

    @PostMapping
    public void postAdd(@RequestBody Message message){
        messageService.saveMessage(message);
    }

    @GetMapping("/{id}")
    public Message getMessage(@PathVariable Integer id){
        return messageService.getMessage(id);
    }

}
