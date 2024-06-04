package com.wave.systemManagement.service;

import com.wave.systemManagement.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

    public void sendInvitation(String email, Long projectId) throws MessagingException;

    public Invitation accepInvitation(String token, Long userId) throws Exception;

    public String getTokenByUserMail(String userMail);

    void deleteToken(String token);
}