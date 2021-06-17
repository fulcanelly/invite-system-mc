package me.fulcanelly.inguard;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import java.net.ServerSocket;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import lombok.SneakyThrows;
import me.fulcanelly.inguard.client.RequestPlanner;
import me.fulcanelly.inguard.spigot.event.PlayerMotionPreventor;

public class InviteGuardPlugin extends JavaPlugin {

    @Override @SneakyThrows
    public void onEnable() {
        var planner = Guice.createInjector(new MainModule(this))
            .getInstance(RequestPlanner.class);
        planner.start();
            
        getServer().getPluginManager()
            .registerEvents(new PlayerMotionPreventor(), this);

        getServer().getPluginManager()
            .registerEvents(planner, this);
            
        getServer().getMessenger()
            .registerOutgoingPluginChannel(this, "BungeeCord"); 
    }

}


