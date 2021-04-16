package me.fulcanelly.insyscore.server;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.logging.Logger;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.net.*;
import java.io.*;
import lombok.SneakyThrows;
import me.fulcanelly.clsql.databse.SQLQueryHandler;
import me.fulcanelly.clsql.stop.Stopable;
import me.fulcanelly.insyscore.database.InvitationsDatabase;

class SocketTalker {
    PrintWriter out;
    BufferedReader in;

    @SneakyThrows
    SocketTalker(Socket socket) {
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(
            new InputStreamReader(socket.getInputStream())
        );
    }

}

public class InviteCheckerServer implements Stopable {

    public InviteCheckerServer(InvitationsDatabase idb, Logger logger) {
        this.idb = idb;
        this.logger = logger;
    }

    ServerSocket server;
    InvitationsDatabase idb;
    Logger logger;

    PrintWriter out;
    BufferedReader in;

    @SneakyThrows
    void setupIO(Socket socket) {
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(
            new InputStreamReader(socket.getInputStream())
        );
    }


    @SneakyThrows
    void handleRequests(Socket socket) {
        try {

            while (socket.isConnected()) {
                if (in.readLine().equals("?")) {
                    out.println(
                        idb.isInvited(in.readLine())
                    );
                    logger.info("[IniteCheckerServer] handled request ");
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    <T>boolean isNotNull(T it) {
        return it != null;
    }

    @SneakyThrows
    public void closeConnection(Closeable closeable) {
        closeable.close();
    }

    @SneakyThrows
    <T> void closeAll() {
        List.of(out, in, server).parallelStream()
            .filter(this::isNotNull)
            .forEach(this::closeConnection);
    }

    @SneakyThrows
    public void start() {
        server = new ServerSocket(6997);
        logger.info("[IniteCheckerServer] started server");

        while (true) {
            System.out.println("ok " + server);
            var clientSocket = server.accept();
            logger.info("[IniteCheckerServer] get connection");

            setupIO(clientSocket);
            handleRequests(clientSocket);
            closeAll();

            logger.info("[IniteCheckerServer] lost connection");
        }
    }

    @SneakyThrows
    @Override
    public void stopIt() {
        closeAll();
    }
}
