package com.danielmasegosa.infrastructure.console.input;

public final class CommandExecutor {

    private final CommandGenerator commandGenerator;

    public CommandExecutor(final CommandGenerator commandGenerator) {
        this.commandGenerator = commandGenerator;
    }

    public void execute(final String command) {
        commandGenerator.execute(command.trim()).execute();
    }
}
