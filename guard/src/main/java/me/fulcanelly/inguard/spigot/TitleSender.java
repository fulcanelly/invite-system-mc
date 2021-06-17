package me.fulcanelly.inguard.spigot;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class TitleSender implements PlayerSender {
    
    @NonNull String title, subtitle;
    int fadeIn = 20, stay = 30, fadeOut = 20;

    public void send(Player player) {
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

}
