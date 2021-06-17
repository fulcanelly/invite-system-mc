package me.fulcanelly.inguard.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.fulcanelly.inguard.logger.LoopedPlayerExecutorLogger;

@interface test {}

@Data
public abstract class PlayersSpecificScheduledTaskExecutor {

    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    @Inject @Named("executor.interval")
    Integer interval;    
    
    @Inject
    LoopedPlayerExecutorLogger logger;

    @Inject
    Server server;

    protected abstract void handlePlayer(Player player);
    protected abstract void onEmptyServer();

    protected void onEachInterval() {
        logger.logProcessingInterval();
      
        var onlinePlayers = server.getOnlinePlayers();

        if (onlinePlayers.size() > 0) {
            onlinePlayers.parallelStream()
                .forEach(this::handlePlayer);
        } else {
            onEmptyServer();
        }
    }  

    private final void safeInteraval() {
        try {
            onEachInterval();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void start() {
        logger.logSettingUp();
        service.scheduleAtFixedRate(this::safeInteraval, 0, interval, TimeUnit.MILLISECONDS);
    }

}   
