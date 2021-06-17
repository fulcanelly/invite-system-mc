package me.fulcanelly.inguard.logger;

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

    public void logGotTimout() {
        println("got timeout");

    }
}
