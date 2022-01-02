package me.fulcanelly.insyscore;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
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

import lombok.Getter;
import lombok.SneakyThrows;
import me.fulcanelly.clsql.databse.SQLQueryHandler;
import me.fulcanelly.clsql.stop.Stopable;
import me.fulcanelly.insyscore.command.UserInteractionHandler;
import me.fulcanelly.insyscore.database.InvitationsDatabase;
import me.fulcanelly.insyscore.database.InviteCleaner;
import me.fulcanelly.insyscore.server.InviteCheckerServer;
import java.sql.DriverManager;

@Getter
public class InviteSysCore extends JavaPlugin  {


    Stopable[] toStop = new Stopable[] {};
    
    InvitationsDatabase database;

    @SneakyThrows
    InvitationsDatabase setupInviteDatabase(SQLQueryHandler sql) {
        return new InvitationsDatabase(sql);
    }

    @SneakyThrows    
    SQLQueryHandler setupSQL() {
        return new SQLQueryHandler(
            DriverManager.getConnection("jdbc:sqlite:" + this.getDataFolder() + "/db.sqlite3"), false
        );
    }
    
    void tryCleanInvites(SQLQueryHandler sql) {
        InviteCleaner.builder()
                .sql(sql)
                .server(getServer())
                .invites(database)
                .timeout(TimeUnit.HOURS.toMillis(24))
                .logger(this.getLogger()).build()
            .setup()
            .removeUnplayed();
    }

    @Override
    @SneakyThrows
    public void onEnable() {
        this.getDataFolder().mkdir(); //creating plugin folder if not exists

        this.getLogger().warning("creatinmg database");

        var sql = setupSQL();
        this.database = this.setupInviteDatabase(sql);
        
        this.getLogger().info("seting up server");
        
        //starting tcp server, currently on 6997 port
        var server = new InviteCheckerServer(
            new ServerSocket(6997), database, this.getLogger()
        );

        tryCleanInvites(sql);

        toStop = new Stopable [] { database, server };
        // is it needs comments ?

        var commands = new UserInteractionHandler(database);
        //sort of minecraft cli
        server.start();
        this.getCommand("invite").setExecutor(commands);;
   
    }

    @Override
    public void onDisable() {
        for (var one : toStop) {
            one.stopIt();
        }
    }
    

}



