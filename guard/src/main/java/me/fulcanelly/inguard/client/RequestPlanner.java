package me.fulcanelly.inguard.client;


import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import lombok.NonNull;
import lombok.SneakyThrows;
import me.fulcanelly.inguard.client.protocol.InviteProtocol;
import me.fulcanelly.inguard.logger.RequestPlannerLogger;
import me.fulcanelly.inguard.utils.PlayersSpecificScheduledTaskExecutor;

public class RequestPlanner extends PlayersSpecificScheduledTaskExecutor implements Listener {

    @Inject
    InviteProtocol invites;
    
    @Inject
    RequestPlannerLogger logger;

    @Inject
    PlayerDispatcher dispatcher;
    
    @Override
    protected void handlePlayer(Player player) {
        logger.logHadnlingPlayer();
        dispatcher.process(player);
    }

    long counterToSuspend = 0;
 
    @Inject @Named("max ping before die")
    final long maxPingCount = 3;

    @Override @SneakyThrows
    protected void onEmptyServer() {
        counterToSuspend++;
        
        logger.logEmptyServer();
        invites.pingKeepAlive();   

        if (counterToSuspend >= maxPingCount) {
            logger.logShuttingDown();
            counterToSuspend = 0;
            this.shutDown();
        }

    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        if (!isRuning()) {
            runAgaing();
        }
    
    }
    
} 

