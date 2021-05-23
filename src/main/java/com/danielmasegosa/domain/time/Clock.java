package com.danielmasegosa.domain.time;

import java.time.Instant;

public interface Clock {

    Instant now();
}
