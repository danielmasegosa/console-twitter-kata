package com.danielmasegosa.infrastructure.terminal.commands.dto.validation;

import java.util.ArrayList;
import java.util.List;

public final class Notification {
    private List<String> errors = new ArrayList<>();

    public void addError(String message) {
        errors.add(message);
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
