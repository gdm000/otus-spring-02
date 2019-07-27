package edu.otus.spring02.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class HealthCheck implements HealthIndicator {
    private final String message_key = "Library service";
    @Override
    public Health health() {
        if (!isRunning()) {
            return Health.down().withDetail(message_key, "Not Available").build();
        }
        return Health.up().withDetail(message_key, "Available").build();
    }
    private Boolean isRunning() {
        return new Random().nextBoolean();
    }
}