package me.fulcanelly.insyscore.database;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;
import me.fulcanelly.clsql.databse.SQLQueryHandler;

@Data @AllArgsConstructor 
class NickNTime {

    static NickNTime of(Player player) {
        return new NickNTime(
            player.getName(), player.getLastPlayed()
        );
    }

    final String name;
    final long lastLogin;
}

@RequiredArgsConstructor @With @Builder
public class InviteCleaner {
    
    final SQLQueryHandler sql;
    final Server server;
    final long timeout;
    
    final InvitationsDatabase invites;
    final Logger logger;
    final Set<NickNTime> players;
  

    public InviteCleaner setup() {
        return this.withPlayers(getAllPlayedNicks());
    }

    Set<NickNTime> getAllPlayedNicks() {
        logger.info("Getting all players");
        return Stream.of(server.getOfflinePlayers())
            .map(it -> new NickNTime(it.getName(), it.getLastPlayed()))
            .filter(it -> (it.getName() != null))
            .collect(Collectors.toSet());
    }

    boolean isNotPlayed(Map<String, Object> tuple) {
        var name = tuple.get("invitee").toString();
        return players.stream()
            .filter(it -> it.getName().equals(name))
            .findFirst().isEmpty();
    }

    boolean isTimeOut(Map<String, Object> tuple) {
        var lastTime = (long)tuple.get("time");
        return System.currentTimeMillis() - lastTime >= timeout;
    }

    void removePlayer(String player) {
        invites.kickOut(player);
    }

    public void removeUnplayed() {
        logger.info("Loading all invites");
        sql.executeQuery("SELECT invitee, time FROM invitations")
            .andThen(sql::parseListOf)
            .waitForResult().stream()
            .filter(tuple -> isNotPlayed(tuple) && isTimeOut(tuple))
            .map(it -> it.get("invitee").toString())
            .forEach(player -> {
                logger.info("Removing player: " + player);
                removePlayer(player);
            });
            
    }
}
