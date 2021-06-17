package me.fulcanelly.inguard.logger;

import java.io.PrintStream;

public class InviteProtocolLogger extends TargetLogger {
    
    public InviteProtocolLogger() {
        super("InviteProtocolService");
    }

    public void logPing() {
        println("pinging server");
    }

    public void logIniteCheckFor(String player) {
        println("checkign is " + player + "invited");
    }

    public void getRequestProblem() {
        println("get problem while executing request, trying again");
    }
    
}
