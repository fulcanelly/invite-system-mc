package me.fulcanelly.inguard.spigot.event;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;


import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PlayerMotionPreventor implements Listener {
    
    @EventHandler
    void onMove(PlayerMoveEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
    }

    @EventHandler 
    void onCommand(PlayerCommandPreprocessEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    void onTab(TabCompleteEvent event) {
        event.setCancelled(true);
    }
    
}
