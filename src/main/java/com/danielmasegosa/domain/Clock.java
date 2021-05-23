package com.danielmasegosa.domain;

import java.time.Instant;

public interface Clock {

    Instant now();
}
