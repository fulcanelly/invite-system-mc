package me.fulcanelly.inguard.logger;

import java.io.PrintStream;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


public abstract class TargetLogger {
    
    PrintStream out = System.out;
    final String self;

    @Inject @Named("disable logger")
    boolean disable = false;

    TargetLogger(String self) {
        this.self = self;
        println("created");
    }
    
    void println(String line) {
        if (disable) {
            return;
        }
        var current = Thread.currentThread();
        var wasName = current.getName();
        current.setName(self);
        out.println("[" + self + "]: " + line);
        current.setName(wasName);
    }
}
