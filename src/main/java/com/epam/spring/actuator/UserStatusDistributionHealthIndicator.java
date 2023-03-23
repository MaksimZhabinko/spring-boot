package com.epam.spring.actuator;

import com.epam.spring.dao.UserRepository;
import com.epam.spring.entity.StatusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserStatusDistributionHealthIndicator implements HealthIndicator {
    private static final String CURRENT_ACTIVE_METRIC = "currentActive";
    private static final String CURRENT_DELETED_METRIC = "currentDeleted";
    private static final String DETAIL_NAME = "userStatusDistribution";

    @Value("${app.health.min-active-users-count}")
    private int minimumActiveUsersRequired;
    @Value("${app.health.max-deleted-users-count}")
    private int maximumDeletedUsersAllowed;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Health health() {
        Integer activeUsers = userRepository.countUserByStatus(StatusEntity.ACTIVE);
        Integer deletedUsers = userRepository.countUserByStatus(StatusEntity.DELETED);

        Map<String, Integer> userStatusDistribution = new HashMap<>();
        userStatusDistribution.put(CURRENT_ACTIVE_METRIC, activeUsers);
        userStatusDistribution.put(CURRENT_DELETED_METRIC, deletedUsers);

        if (activeUsers < minimumActiveUsersRequired || deletedUsers > maximumDeletedUsersAllowed) {
            return new Health.Builder().down().withDetail(DETAIL_NAME, userStatusDistribution).build();
        }

        return new Health.Builder().up().withDetail(DETAIL_NAME, userStatusDistribution).build();
    }
}
