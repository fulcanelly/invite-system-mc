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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lombok.NonNull;
import lombok.SneakyThrows;
import me.fulcanelly.inguard.client.protocol.InviteProtocol;
import me.fulcanelly.inguard.logger.RequestPlannerLogger;
import me.fulcanelly.inguard.utils.PlayersSpecificScheduledTaskExecutor;

public class RequestPlanner extends PlayersSpecificScheduledTaskExecutor {

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

    @Override @SneakyThrows
    protected void onEmptyServer() {
        logger.logEmptyServer();
        invites.pingKeepAlive();        
    }
    
} 

