package me.fulcanelly.inguard.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.bukkit.entity.Player;

import lombok.Data;
import lombok.experimental.UtilityClass;
import me.fulcanelly.inguard.spigot.PlayerSender;
import me.fulcanelly.inguard.spigot.TitleSender;

@UtilityClass
class TimerHelper {

    
    boolean isExpired(long expireTime) {
        return System.currentTimeMillis() - expireTime >= 0;
    }

    public long getExpireTime(long delay) {
        return delay + System.currentTimeMillis();
    }
}

class PlayerActionDelayControl {

    Map<Player, Long> expireMap = new WeakHashMap<>();

    @Inject @Named("sender.delay")
    long delay = 4001;
    
    public void entrollDelayFor(Player player) {
        expireMap.put(player, TimerHelper.getExpireTime(delay));
    }

    public boolean checkIsRightTimeToDoActionFor(Player player) {
        return TimerHelper.isExpired(expireMap.getOrDefault(player, 0l));
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