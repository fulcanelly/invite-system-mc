package me.fulcanelly.inguard.logger;

import java.io.PrintStream;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


public abstract class TargetLogger {
    
    PrintStream out = System.out;
    final String self;

    TargetLogger(String self) {
        this.self = self;
        println("created");
    }
    
    void println(String line) {
        var current = Thread.currentThread();
        var wasName = current.getName();
        current.setName(self);
        out.println("[" + self + "]: " + line);
        current.setName(wasName);
    }
}
