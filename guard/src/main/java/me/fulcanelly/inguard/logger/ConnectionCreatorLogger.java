package me.fulcanelly.inguard.logger;

public class ConnectionCreatorLogger extends TargetLogger {

    public ConnectionCreatorLogger() {
        super("ConnectionCreator");
    }

    public void logAttemtToConnect(int number) {
        println("attemt " + number + " to connect");
    }

    public void logSuccessfulConnection() {
        println("connected");
    }

}
