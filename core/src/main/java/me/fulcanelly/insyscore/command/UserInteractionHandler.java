package me.fulcanelly.insyscore.command;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.management.RuntimeErrorException;

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
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.net.*;
import java.io.*;
import lombok.SneakyThrows;
import me.fulcanelly.clsql.databse.SQLQueryHandler;
import me.fulcanelly.clsql.stop.Stopable;
import me.fulcanelly.insyscore.database.InvitationsDatabase;
import me.fulcanelly.insyscore.server.InviteCheckerServer;

public class UserInteractionHandler implements CommandExecutor {
    
    InvitationsDatabase idb;

    public UserInteractionHandler(InvitationsDatabase idb) {
        this.idb = idb;
    }

    /**
     * tests
     */
    public static void main(String[] args) {

        var uihandler = new UserInteractionHandler(null);    
      //  uihandler.onCommand(null, null, null, null);//expect error
        uihandler.onCommand(null, null, "invite", new String[] {
            "person", "lohg"
        });//ok

        uihandler.onCommand(null, null, "invite", new String[] {
            "person",
        });//error: not enough

        uihandler.onCommand(null, null, "invite", new String[] {
            "person", "lohg", "sdfg"
        });//error: too much arguments


    }
    /**
     * /invite show option<page> 
     * /invite about (#self | name) 
     * /invite person name
     * /invite kick-out name optin<recursive>
     * 
     * /invite help -/
     */



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        var deque = new ArrayDeque<>(Arrays.asList(args));

        switch (deque.pollFirst()) {
            case "help": 
                sender.sendMessage("i'll do it soon... I promise...");
            break;
            case "person":
                if (deque.size() == 0) {
                    sender.sendMessage("Not enogh arguments, specify person's name");
                } else if (deque.size() != 1) {
                    sender.sendMessage("Too much arguments");
                } else {
                    var invitee = deque.pollFirst();
                    if (idb.invite(sender.getName(), invitee)) {
                        sender.sendMessage(invitee + " have invited");
                    } else {
                        sender.sendMessage(invitee + " is already invited");
                    }
                }
            break;
            case "kick-out":
                if (deque.size() == 0) {
                    sender.sendMessage("Not enogh arguments, specify person's name");
                } else if (deque.size() != 1) {
                    sender.sendMessage("Too much arguments");
                } else {
                    final var player_name = deque.pollFirst();

                    if (sender.isOp()) {
                        if (idb.kickOut(player_name)) {
                            var player = Bukkit.getPlayerExact(player_name);
                            if (player != null) {
                                player.kickPlayer("^)");
                            };
                            sender.sendMessage("Player kicked out");
                        } else {
                            sender.sendMessage("Unknown player");

                        };
                    } else {
                        sender.sendMessage("You have no permissions to execute this command");
                    }
                }
            break;
            case "about":
                if (deque.size() == 0) {
                    sender.sendMessage("Not enogh arguments, specify person's name");
                } else if (deque.size() != 1) {
                    sender.sendMessage("Too much arguments");
                } else {
                    
                }
            break;
            default:
                sender.sendMessage("Wrong option try /invite help");
        }

        return true;
    }


}
