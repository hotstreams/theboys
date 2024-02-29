package com.theboys.data.repos;

import com.theboys.data.entities.Subscription;
import com.theboys.data.entities.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionsRepo extends JpaRepository<Subscription, SubscriptionId> {
}
