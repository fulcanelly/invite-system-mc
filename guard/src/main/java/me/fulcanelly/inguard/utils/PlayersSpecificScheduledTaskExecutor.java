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
import me.fulcanelly.inguard.client.PlayerFlowPipe;
import me.fulcanelly.inguard.client.protocol.InviteProtocol;
import me.fulcanelly.inguard.client.protocol.ProtocolRequestInsurer;
import me.fulcanelly.inguard.logger.PlayersLoopedPlayerExecutorLogger;

import java.util.concurrent.ScheduledFuture;

@Data
public abstract class PlayersSpecificScheduledTaskExecutor {

    protected ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    protected boolean isRuning = true;

    protected void shutDown() {
        isRuning = false;
    }

    protected boolean isRuning() {
        return isRuning;
    }

    protected void runAgaing() {
        isRuning = true;
    }

    @Inject @Named("executor.interval")
    Integer interval;    
    
    @Inject
    PlayersLoopedPlayerExecutorLogger logger;

    @Inject
    Server server;
    
    @Inject
    InviteProtocol protocol;
     
    @Inject
    ProtocolRequestInsurer insurer;

    @Inject
    PlayerFlowPipe pipe;

    protected abstract void handlePlayer(Player player);
    protected abstract void onEmptyServer();

    protected void onEachInterval() {
        logger.logProcessingInterval();
      
        var onlinePlayers = new LinkedList<>(pipe.getPlayersToHandle());

        if (onlinePlayers.size() > 0) {
            onlinePlayers.parallelStream()
                .forEach(this::handlePlayer);
        } else {
            onEmptyServer();
        }
    }  

    private final void safeInteraval() {
        if (!isRuning) {
            return;
        }
        try {
            insurer.exeucteSafelyProtocolRequest(this::onEachInterval);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        logger.logSettingUp();
        service.scheduleAtFixedRate(this::safeInteraval, 0, interval, TimeUnit.MILLISECONDS);
    }

}   
