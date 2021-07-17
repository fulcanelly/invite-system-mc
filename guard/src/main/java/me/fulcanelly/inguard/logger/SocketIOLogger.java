package me.fulcanelly.inguard.logger;

import java.io.IOException;

public class SocketIOLogger extends TargetLogger {
    
    public SocketIOLogger() {
        super("SocketTalker");
    }

    public void logReadingLine() {
        println("reading line ");
    }

    public void logPrintlingLine(String line) {
        println("printing line: "+ line);
    }

    public void logReconnecting() {
        println("connecting to socket");
    }

    public void logGotIOException(IOException e) {
        println("got io exc: " + e);
        e.printStackTrace();
    }
}
