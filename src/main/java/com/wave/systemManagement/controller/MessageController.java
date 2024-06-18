package com.wave.systemManagement.controller;

import com.wave.systemManagement.model.Chat;
import com.wave.systemManagement.model.Message;
import com.wave.systemManagement.model.User;
import com.wave.systemManagement.request.CreateMessageRequest;
import com.wave.systemManagement.service.MessageService;
import com.wave.systemManagement.service.ProjectService;
import com.wave.systemManagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request) throws Exception {
        User user = userService.findUserById(request.getSenderId());
        if(user == null){
            throw new Exception("User not found with id " + request.getSenderId());
        }
        Chat chat = projectService.getProjectById(request.getProjectId()).getChat();
        if(chat == null){
            throw new Exception("Chat not found");
        }
        Message sendMessage = messageService.sendMessage(request.getSenderId(),
                request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sendMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessageByChatId(@PathVariable Long projectId) throws Exception {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}