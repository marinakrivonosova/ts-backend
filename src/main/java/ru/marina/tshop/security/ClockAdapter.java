package ru.marina.tshop.security;

import io.jsonwebtoken.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class ClockAdapter implements Clock {
    private final java.time.Clock clock;

    @Autowired
    public ClockAdapter(final java.time.Clock clock) {
        this.clock = clock;
    }

    @Override
    public Date now() {
        final ZonedDateTime zoneDateTime = ZonedDateTime.now(clock);
        return Date.from(zoneDateTime.toInstant());
    }
}
