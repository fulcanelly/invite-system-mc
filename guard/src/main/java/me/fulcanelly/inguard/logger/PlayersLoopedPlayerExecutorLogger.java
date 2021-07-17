package me.fulcanelly.inguard.logger;

import lombok.NonNull;

public class PlayersLoopedPlayerExecutorLogger extends TargetLogger {

    public PlayersLoopedPlayerExecutorLogger() {
        super("PlayersLoopedPlayerExecutorLogger");
    }

    public void logSettingUp() {
        println("setting up");
    }

    public void logProcessingInterval() {
        println("interval time");
    }

}
