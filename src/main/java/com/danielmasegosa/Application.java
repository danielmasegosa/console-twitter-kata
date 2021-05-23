package com.danielmasegosa;

import com.danielmasegosa.application.MessagesRetriever;
import com.danielmasegosa.application.PostCreator;
import com.danielmasegosa.application.UserSubscriber;
import com.danielmasegosa.application.UserWallRetriever;
import com.danielmasegosa.domain.repository.UserRepository;
import com.danielmasegosa.domain.time.Clock;
import com.danielmasegosa.infrastructure.persistence.InMemoryRepository;
import com.danielmasegosa.infrastructure.persistence.InMemoryUserRepository;
import com.danielmasegosa.infrastructure.terminal.commands.CommandExecutor;
import com.danielmasegosa.infrastructure.terminal.commands.CommandGenerator;
import com.danielmasegosa.infrastructure.time.InternalClock;
import com.danielmasegosa.it.terminal.Terminal;

import java.util.Scanner;

public class Application {


    public static void main(String[] args) {
        final Clock clock = new InternalClock();
        final Scanner scanner = new Scanner(System.in);
        final Terminal terminal = new Terminal(clock, scanner);
        final InMemoryRepository inMemoryRepository = new InMemoryRepository();
        final UserRepository userRepository = new InMemoryUserRepository(inMemoryRepository);
        final MessagesRetriever messagesRetriever = new MessagesRetriever(userRepository);
        final PostCreator postCreator = new PostCreator(userRepository, clock);
        final UserSubscriber userSubscriber = new UserSubscriber(userRepository);
        final UserWallRetriever userWallRetriever = new UserWallRetriever(userRepository);
        final CommandGenerator commandGenerator = new CommandGenerator(postCreator, messagesRetriever, userSubscriber, userWallRetriever, terminal);
        final CommandExecutor commandExecutor = new CommandExecutor(commandGenerator);

        String terminalCommand = terminal.readLine();
        while (!"quit".equals(terminalCommand)){
            commandExecutor.execute(terminalCommand);
            terminalCommand = terminal.readLine();
        }
        terminal.write("Out");

    }
}
