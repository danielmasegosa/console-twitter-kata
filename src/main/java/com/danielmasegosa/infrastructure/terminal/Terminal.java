package com.danielmasegosa.infrastructure.terminal;

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

    public String readLine() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    public void write(final List<Post> posts) {
        posts.forEach(this::write);
    }

    public void write(final String text) {
        System.out.println(text);
    }

    public void writeErrors(final List<String> errors) {
        errors.forEach(this::writeError);
    }

    public void writeError(final String errorText) {
        System.err.println(errorText);
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
