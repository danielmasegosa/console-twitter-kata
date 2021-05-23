package com.danielmasegosa.infrastructure.terminal.commands;

import com.danielmasegosa.application.UserSubscriber;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.infrastructure.terminal.commands.dto.SubscribtioDto;

public class SubscriptionTerminalCommand implements TerminalCommand {

    private final SubscribtioDto subscriptionDto;
    private final UserSubscriber userSubscriber;

    public SubscriptionTerminalCommand(final SubscribtioDto subscriptionDto, final UserSubscriber userSubscriber) {
        this.subscriptionDto = subscriptionDto;
        this.userSubscriber = userSubscriber;
    }

    @Override
    public void execute() {
        userSubscriber.execute(new User(subscriptionDto.getUserName()), new User(subscriptionDto.getSubscribeTo()));
    }
}
