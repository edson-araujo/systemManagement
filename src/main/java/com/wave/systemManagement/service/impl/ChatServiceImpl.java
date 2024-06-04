package com.wave.systemManagement.service.impl;

import com.wave.systemManagement.model.Chat;
import com.wave.systemManagement.repository.ChatRepository;
import com.wave.systemManagement.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;
    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
