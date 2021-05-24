package com.danielmasegosa.infrastructure.terminal.commands;

import com.danielmasegosa.application.UserSubscriber;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.infrastructure.terminal.Terminal;
import com.danielmasegosa.infrastructure.terminal.commands.dto.SubscribtioDto;
import com.danielmasegosa.infrastructure.terminal.commands.dto.validation.Notification;

public class SubscriptionTerminalCommand implements TerminalCommand {

    private final SubscribtioDto subscriptionDto;
    private final UserSubscriber userSubscriber;
    private Terminal terminal;

    public SubscriptionTerminalCommand(final SubscribtioDto subscriptionDto, final UserSubscriber userSubscriber, Terminal terminal) {
        this.subscriptionDto = subscriptionDto;
        this.userSubscriber = userSubscriber;
        this.terminal = terminal;
    }

    @Override
    public void execute() {
        final Notification check = subscriptionDto.check();
        if (check.hasErrors()) {
            terminal.writeErrors(check.getErrors());
        } else {
            userSubscriber.execute(new User(subscriptionDto.getUserName().trim()), new User(subscriptionDto.getSubscribeTo().trim()));
        }
    }
}
