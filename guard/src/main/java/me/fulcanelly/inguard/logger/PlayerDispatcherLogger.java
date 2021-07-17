package me.fulcanelly.inguard.logger;

public class PlayerDispatcherLogger extends TargetLogger {

    PlayerDispatcherLogger() {
        super("PlayerDispatcher");
    }

    public void logPassingPlayerToServer() {
        println("passing player to server");
    }

}
