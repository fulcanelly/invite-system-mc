package me.fulcanelly.inguard.client.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import lombok.SneakyThrows;
import me.fulcanelly.inguard.logger.PlayerDispatcherLogger;

public class PlayerTransitor {
    
    @Inject 
    PlayerDispatcherLogger logger;

    @Inject 
    Plugin plugin;

    @SneakyThrows
    public void transitToMainServer(Player player) {
        transitToServer(player, "main");
    }

    @SneakyThrows
    public void transitToServer(Player player, String serverName) {
        logger.logPassingPlayerToServer();
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName);
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }


}
