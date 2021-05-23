package com.danielmasegosa.infrastructure.time;

import com.danielmasegosa.domain.time.Clock;

import java.time.Instant;

public final class InternalClock implements Clock {

    @Override
    public Instant now() {
        return Instant.now();
    }
}
