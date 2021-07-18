package me.fulcanelly.inguard.client.states;

import com.google.inject.Inject;

import org.bukkit.entity.Player;

import me.fulcanelly.inguard.client.FoolConstantAnnouncmentSender;
import me.fulcanelly.inguard.client.bungee.PlayerTransitor;
import me.fulcanelly.inguard.client.protocol.ProtocolRequestInsurer;

public class ControlingContext {

    @Inject
    ProtocolRequestInsurer insurer;

    @Inject 
    PlayerTransitor transitor;

    @Inject
    FoolConstantAnnouncmentSender sender;
  
    void transitToTargetServer(Player player) {
        transitor.transitToMainServer(player);
    }

    void sendWarning(Player player) {
        sender.send(player);
    }

    void removeFromFlowPipe() {
        
    }
}
