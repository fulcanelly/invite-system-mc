package me.fulcanelly.inguard.client;

import com.google.common.io.*;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;

import org.bukkit.Bukkit;
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
    PlayerFlowPipe pipe;

    /*
    implements PluginMessageListener 
    @Override
    public void onEnable() {
      this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
     this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
     }


    @SneakyThrows
    boolean isPlayerOnServer(Player player, String targetServer) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServer");
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        String servername = in.readUTF();
        return servername.equals(targetServer);
    }

    
    void passToServer(Player player, String server) {
        while (!isPlayerOnServer(player, server)) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        }
    }

    class AssociatedMessageExecutor {
        Consumer<ByteArrayDataInput> consumer;
        String channel;
        Player player;
        
        boolean isItRightExecutor(String c, Player p) {
            return channel.equals(c) && player.getName().equals(p.getName());
        }
        
    }
    
    @Inject
    BlockingList<AccosiatedMessageExecutor> associated; 
    
    //better create separate class for bungeecord purpose 
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        associated.stream()
            .filter(e -> e.isItRightExecutor(in.readUTF(), player)
            .forEach();
        String subchannel = ;
         if (!channel.equals("BungeeCord")) {
             ByteArrayDataInput in = ByteStreams.newDataInput(message);
             String servername = in.readUTF();
         }
    }
    

    */
    
    @Inject 
    PlayerDispatcherLogger logger;
    
    @SneakyThrows
    void passToMainServer(Player player) {
        pipe.remove(player);

        //temporary attempt to fix unloading chunks 
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            logger.logPassingPlayerToServer();
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("main");
            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        }, 5);
       
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
