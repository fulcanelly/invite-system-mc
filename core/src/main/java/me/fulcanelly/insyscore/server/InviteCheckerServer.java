package me.fulcanelly.insyscore.server;

import java.util.logging.Logger;
import java.util.stream.Stream;

import java.net.*;
import java.io.*;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.SneakyThrows;

import me.fulcanelly.clsql.stop.Stopable;
import me.fulcanelly.insyscore.database.InvitationsDatabase;

@AllArgsConstructor
public class InviteCheckerServer extends Thread implements Stopable {

    ServerSocket server;
    InvitationsDatabase idb;
    Logger logger;

    @SneakyThrows
    Socket unsafeAccept() {
        return server.accept();
    }

    public void run() {
        Stream.generate(this::unsafeAccept)
            .map(sock -> new InviteServerEventsReactor(sock, idb, logger))
            .peek(ProtocolHandler::setupIO)
            .peek(it -> it.setTimeout(10000))
            .forEach(Thread::start);
    }

    @SneakyThrows
    @Override
    public void stopIt() {
        server.close();
    }
}
