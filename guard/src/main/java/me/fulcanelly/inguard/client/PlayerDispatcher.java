package me.fulcanelly.inguard.client;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.fulcanelly.inguard.client.protocol.InviteProtocol;
import me.fulcanelly.inguard.logger.PlayerDispatcherLogger;

@Data
class PlayerDispatcher {

    @Inject
    InviteProtocol invites;    
    
    @Inject
    Plugin plugin;
    
    @Inject
    FoolConstantAnnouncmentSender sender;
    
    @Inject 
    PlayerDispatcherLogger logger;
    
    @SneakyThrows
    void passToMainServer(Player player) {
        logger.logPassingPlayerToServer();
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("main");
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    void onPlayerUnknown(Player player) {
        sender.send(player);
    }

    @SneakyThrows
    void process(Player player) {
        if (invites.checkIsInvited(player.getName())) {
            passToMainServer(player);
        } else {
            onPlayerUnknown(player);
        }
    }

}