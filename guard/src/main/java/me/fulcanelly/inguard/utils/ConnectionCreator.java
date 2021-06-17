package me.fulcanelly.inguard.utils;

import java.io.IOException;
import java.net.Socket;

import lombok.RequiredArgsConstructor ;
import lombok.Builder;
import lombok.SneakyThrows;
import me.fulcanelly.inguard.logger.ConnectionCreatorLogger;
import lombok.Data;
import lombok.NonNull;

@Data @RequiredArgsConstructor 
public class ConnectionCreator {

    @NonNull String host;
    @NonNull int port;
    @NonNull int timeout;

    ConnectionCreatorLogger logger = new ConnectionCreatorLogger();

    @SneakyThrows
    protected Socket obtainConnection() {
        try {
            var socket = new Socket(host, port);
            socket.setSoTimeout(timeout);
            return socket;
        } catch(IOException e) {
            return null;
        }
    }
      /*

    void showStackTrace() {
        try {
            throw new RuntimeException();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
*/
    @SneakyThrows
    public Socket connect() {
        Socket conn;
        int attemt = 0;
     ///   showStackTrace();
        do {
            logger.logAttemtToConnect(attemt);
            conn = obtainConnection();
            if (attemt > 0) {
                Thread.sleep(1000);
            }
            attemt++;
        } while (conn == null);
        logger.logSuccessfulConnection();
        return conn;
    }
}