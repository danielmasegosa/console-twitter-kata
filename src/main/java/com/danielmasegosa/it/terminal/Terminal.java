package com.danielmasegosa.it.terminal;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.time.Clock;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Terminal {

    private Clock clock;
    private Scanner scanner;

    public Terminal(final Clock clock, final Scanner scanner) {
        this.clock = clock;
        this.scanner = scanner;
    }

    public void write(final List<Post> posts) {
        posts.forEach(this::write);
    }

    public void write(final String text) {
        System.out.println(text);
    }

    public String readLine() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    private void write(final Post post) {
        System.out.println(formatPost(post));
    }

    private String formatPost(final Post post){
        return String.format("%s - %s (%s)", post.getUser().getUserName(), post.getMessage(), timeDifferenceAsString(post.getCreationDate()));
    }

    private String timeDifferenceAsString(final Instant creationDate){
        final PrettyTime prettyTime = new PrettyTime(clock.now());
        prettyTime.setLocale(Locale.UK);
        return prettyTime.format(creationDate);
    }
}
