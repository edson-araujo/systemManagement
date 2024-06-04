package com.wave.systemManagement.repository;

import com.wave.systemManagement.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
