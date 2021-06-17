package me.fulcanelly.inguard.logger;

import lombok.NonNull;

public class RequestPlannerLogger extends TargetLogger {

    public RequestPlannerLogger() {
        super("RequestPlanner");
    }

    public void logHadnlingPlayer() {
        println("handling player");
    }

    public void logEmptyServer() {
        println("handling empty server");
    }

}
