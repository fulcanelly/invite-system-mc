package me.fulcanelly.inguard.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.fulcanelly.inguard.logger.SocketIOLogger;

public class SocketTalker {

    ConnectionCreator connector;
    SocketIOLogger logger;

    PrintWriter out;
    BufferedReader in;


    public SocketTalker(ConnectionCreator connector, SocketIOLogger logger) {
        this.connector = connector;
        this.logger = logger;
        reconnect();
    }


    @SneakyThrows
    public void setupIO(Socket socket) {
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(
            new InputStreamReader(socket.getInputStream())
        );
    }

    public void reconnect() {
        logger.logReconnecting();
        setupIO(connector.connect());
    }

  
    @SneakyThrows
    protected String ioExceptionFreeReadLine() {
        try {
            return in.readLine();
        } catch(IOException e) {
            logger.logGotIOException();
            return null;
        }
    }

    @SneakyThrows
    public String readLine() {
        logger.logReadingLine();
        var result = ioExceptionFreeReadLine();     
        if (result == null) {
            throw new NeedRequestRepeatException();
        } else {
            return result;
        }
    }

    public void println(Object object) {
        logger.logPrintlingLine(object.toString());
        out.println(object);
    }
}