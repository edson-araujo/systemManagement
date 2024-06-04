package com.wave.systemManagement.service.impl;

import com.wave.systemManagement.model.Plantype;
import com.wave.systemManagement.model.Subscription;
import com.wave.systemManagement.model.User;
import com.wave.systemManagement.repository.SubscriptionRepository;
import com.wave.systemManagement.service.SubscriptionService;
import com.wave.systemManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private UserService userService;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusWeeks(2));
        subscription.setValid(true);
        subscription.setPlantype(Plantype.FREE);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUsersSubscription(Long userId) throws Exception {
        Subscription subscription =  subscriptionRepository.findByUserId(userId);
        if(!isValid(subscription)){
            subscription.setPlantype(Plantype.FREE);
            subscription.setSubscriptionStartDate(LocalDate.now().plusWeeks(2));
            subscription.setSubscriptionStartDate(LocalDate.now());
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription upgradeSubscription(Long userId, Plantype planType) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        subscription.setPlantype(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());
        if(planType.equals(Plantype.ANNUALLY)){
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        } else if (planType.equals(Plantype.MONTHLY)) {
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }else {
            subscription.setSubscriptionEndDate(LocalDate.now().plusWeeks(2));
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if(subscription.getPlantype().equals(Plantype.FREE)){
            return true;
        }
        LocalDate endDate = subscription.getSubscriptionEndDate();
        LocalDate currentDate = LocalDate.now();

        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}