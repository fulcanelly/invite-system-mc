package me.fulcanelly.inguard.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.bukkit.entity.Player;

import lombok.Data;
import me.fulcanelly.inguard.spigot.PlayerSender;
import me.fulcanelly.inguard.spigot.TitleSender;

@Data
class PlayerAssociatedTimer {
    
    Player player;
    long expireTime = System.currentTimeMillis();
    
    PlayerAssociatedTimer(Player player, long expireTime) {
        this.setPlayer(player);
        this.setExpireTime(expireTime);
    }

    boolean isExpired() {
        return System.currentTimeMillis() - expireTime >= 0;
    }

    public void setExpireTime(long delay) {
        this.expireTime = delay + System.currentTimeMillis();
    }
}

class PlayerActionDelayControl {

    Set<PlayerAssociatedTimer> timeControlList = 
        Collections.newSetFromMap(
            new WeakHashMap<>()
        );

    @Inject @Named("sender.delay")
    long delay = 4001;
    
    public void entrollDelayFor(Player player) {
        timeControlList.add(
            new PlayerAssociatedTimer(player, delay)
        );
    }

    public boolean checkIsRightTimeToDoActionFor(Player player) {
        var expireTime = timeControlList.stream()
            .dropWhile(playerNtime -> playerNtime.getPlayer() != player)
            .limit(1).findFirst();
        
        if (expireTime.isEmpty()) {
            entrollDelayFor(player);
            return true;
        }

        return expireTime.get().isExpired();
    }


}

class FoolConstantAnnouncmentSender implements PlayerSender {

    TitleSender titleSender = new TitleSender("§4You are not invited", "§6check out chat for details");

    @Inject
    PlayerActionDelayControl delayControl;

    @Override
    public void send(Player player) {
        if (delayControl.checkIsRightTimeToDoActionFor(player)) {
            titleSender.send(player);
            player.sendMessage("§6To join server you have to be invited, if you aren't try to ask for that in telegram chat §et.me/MinecraftSexClub\n");
        }
    }

}