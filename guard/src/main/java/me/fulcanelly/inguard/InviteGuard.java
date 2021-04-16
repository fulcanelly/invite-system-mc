package me.fulcanelly.inguard;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;


import java.net.*;
import java.io.*;
import lombok.SneakyThrows;



public class InviteGuard extends JavaPlugin implements PluginMessageListener, Listener {

    Logger logger;
    List<String> badPlayers = new LinkedList<>();

    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;

    @SneakyThrows
    synchronized boolean isPlayerInvited(String name) {
        System.out.println(Color.RED + "???");
        logger.warning(Color.RED + "???");

        if (clientSocket.isClosed()) {
            tryConnect();
        }
        
        out.println("?"); // method
        out.println(name);
        out.flush();

        var value = in.readLine();
        System.out.println("got " + value);
        logger.warning(value);

        return Boolean.valueOf(value);
    }


    @SneakyThrows
    boolean safeIsPlayerInvited(String name) {
        while (true) {
            try {
                return isPlayerInvited(name);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(100);
            }
        }

    }

    @EventHandler
    void onMove(PlayerMoveEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
    }

    @EventHandler 
    void onCommand(PlayerCommandPreprocessEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    void onTab(TabCompleteEvent event) {
        event.setCancelled(true);
    }
    
    @EventHandler
    void onQuit(PlayerQuitEvent event) {
        badPlayers.remove(event.getPlayer().getName());
    }

    void dispatchPlayer(Player player) {
        
        if (safeIsPlayerInvited(player.getName())) { 
            logger.info("redirecting "+ player.getName() + " to main server");
        
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("main");
            try {
                player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
            } catch(Exception e) {
                e.printStackTrace();
            } 
        } else {
            badPlayers.add(player.getName());
        }
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {

        var player = event.getPlayer();

        Bukkit.getScheduler()
            .runTaskLater(this, () -> dispatchPlayer(player), 5);
    }
    
    @SneakyThrows
    void tryConnect() {
        while (clientSocket == null || clientSocket.isClosed()) {
            logger.info("trying to connect");
            try {
                clientSocket = new Socket("localhost", 6997);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream());
            } catch(Throwable t) {
                logger.warning(t.getMessage());                
            }
            Thread.sleep(2500);
        }
        logger.info("connected");
    }

    
    @SneakyThrows
    void titleSender() {
        while (InviteGuard.this.isEnabled()) {
            Bukkit.getOnlinePlayers().parallelStream()
                .filter(player -> badPlayers.contains(player.getName()))
                .forEach(player -> {
                    player.sendMessage("§6To join server you have to be invited, if you aren't try to ask for that in telegram chat §et.me/MinecraftSexClub\n");
                    player.sendTitle("§4You are not invited", "§6check out chat for details", 20, 30, 20);
                });
            Thread.sleep(4000);
        }
    }

    @Override
    public void onEnable() {
        logger = getLogger();


        this.tryConnect();

        try {
            getServer().getMessenger()
                .registerOutgoingPluginChannel(this, "BungeeCord");
        } catch(Exception e) {
            e.printStackTrace();
        } 
        /*
        could not connect to default of fallback server
        */

        getServer().getPluginManager().registerEvents(this, this);
        new Thread(this::titleSender).start();
        //getServer().getMessenger().registerIncomingPluginChannel(this, "bungeecord", this);
    }

    @Override
    @SneakyThrows
    public void onDisable() {
        clientSocket.close();
    }
    
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        System.out.println(channel);
        if (channel.equals("bungeecord:sometest")) {
            logger.info(("got message from sometest"));
            logger.info(
                Arrays.toString(message)
            );
        }
    }


}



