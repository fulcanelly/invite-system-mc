package me.fulcanelly.insyscore.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;
import me.fulcanelly.insyscore.database.InvitationsDatabase;

public class InviteServerEventsReactor extends ProtocolHandler {

    public InviteServerEventsReactor(Socket socket, InvitationsDatabase idb, Logger logger) {
        super(socket);
        this.logger = logger;
        this.idb = idb;
    }

    @NonNull InvitationsDatabase idb;
    
    @NonNull Logger logger;

    @OnCommand(symbol = "?")
    boolean checkIsPlayerInvited(String playerName) {
        logger.config("check invite method triggered");

        return idb.isInvited(playerName);
    }

    @OnCommand(symbol = "w")
    String getWhoInvited(String playerName) {
        logger.config("check who invited method triggered");
        return idb.whoInvited(playerName);
    }

    @OnCommand(symbol = "p")
    String keepAliveSignal() {
        logger.config("get signal to keep connection");
        return "ok";
    }

    @Override
    void onDisconect() {
        logger.info("lost connection");
    }

}

