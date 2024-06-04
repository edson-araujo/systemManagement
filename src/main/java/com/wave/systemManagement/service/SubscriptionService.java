package com.wave.systemManagement.service;

import com.wave.systemManagement.model.Plantype;
import com.wave.systemManagement.model.Subscription;
import com.wave.systemManagement.model.User;

public interface SubscriptionService {
    Subscription createSubscription(User user);
    Subscription getUsersSubscription(Long userId) throws Exception;
    Subscription upgradeSubscription(Long userId, Plantype planType);

    boolean isValid(Subscription subscription);
}