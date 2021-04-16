package me.fulcanelly.insyscore.database;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.net.*;
import java.io.*;
import lombok.SneakyThrows;
import me.fulcanelly.clsql.databse.SQLQueryHandler;
import me.fulcanelly.clsql.stop.Stopable;


public class InvitationsDatabase implements Stopable {

    SQLQueryHandler sql;

    public InvitationsDatabase(Connection connection) {
        sql = new SQLQueryHandler(connection, false);
        sql.syncExecuteUpdate("CREATE TABLE IF NOT EXISTS invitations(" +
            "beckoner STRING," +
            "invitee STRING," +
            "time INTEGER" +
        ")");
    }

    public boolean invite(String beckoner, String who) {
        if (isInvited(who)) {
            return false;
        } else {
            sql.execute("INSERT INTO invitations VALUES(?, ?, ?)", beckoner, who, System.currentTimeMillis());
            return true;
        }
    }

    public String whoInvited(String person) {
        return sql.executeQuery("SELECT * FROM invitations WHERE beckoner = ?", person)
            .andThen(this::getName)
            .waitForResult();
    }

    public boolean isInvited(String player) {
        return sql.executeQuery("SELECT * FROM invitations WHERE invitee = ?", player)
            .andThen(sql::safeParseOne)
            .waitForResult()
            .map(igored -> true)
            .orElse(false);
    }

    public boolean kickOut(String player) {
        if (isInvited(player)) {
            sql.execute("DELETE FROM invitations WHERE invitee = ?", player);
            return true;
        } else {
            return false;
        }
    }

    public boolean kickOutRecursive(String p) {
        return false;
    }


    @SneakyThrows
    private String getName(ResultSet rset) {
        return rset.getString(1);
    }   

    //todo
    public void showPage(int size, int page) {

    }

    @Override
    public void stopIt() {
        sql.stopIt();
    }
}
