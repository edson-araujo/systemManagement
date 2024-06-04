package com.wave.systemManagement.service.impl;

import com.wave.systemManagement.model.Chat;
import com.wave.systemManagement.model.Message;
import com.wave.systemManagement.model.User;
import com.wave.systemManagement.repository.MessageRepository;
import com.wave.systemManagement.repository.UserRepository;
import com.wave.systemManagement.service.MessageService;
import com.wave.systemManagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new Exception("User not found with Id: " + senderId));
        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedAt(LocalDate.from(LocalDateTime.now()));
        message.setChat(chat);
        Message savedMessage = messageRepository.save(message);

        chat.getMessage().add(savedMessage);
        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}