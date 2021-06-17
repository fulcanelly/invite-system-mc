package me.fulcanelly.inguard.logger;

import lombok.NonNull;

public class LoopedPlayerExecutorLogger extends TargetLogger {

    public LoopedPlayerExecutorLogger() {
        super("LoopedPlayerExecutor");
    }

    public void logSettingUp() {
        println("setting up");
    }

    public void logProcessingInterval() {
        println("interval time");
    }

}
