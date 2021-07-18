package me.fulcanelly.inguard.client;

import java.util.LinkedList;
import java.util.List;

import javax.swing.UIDefaults.LazyValue;

import com.google.inject.Singleton;

import org.bukkit.entity.Player;

public class PlayerFlowPipe {
    
    List<Player> pipe = new LinkedList<>();

    public void addToPipe(Player player) {
        pipe.add(player);
    }

    public void remove(Player player) {
        pipe.remove(player);
    }

    public List<Player> getPlayersToHandle() {
        return pipe;
    }
}   
