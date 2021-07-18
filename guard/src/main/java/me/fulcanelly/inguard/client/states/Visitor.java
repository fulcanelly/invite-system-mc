package me.fulcanelly.inguard.client.states;

import org.bukkit.entity.Player;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class Visitor {

    final Player player;
    final ControlingContext context;

    PlayerState state = new JustJoinedPlayerState(this);

    {
        new Visitor(null, null);
    }

    public void update() {
        state.update();
    }

    public void nextState() {
        setState(state.nextState());
    }

    protected void transitToTargetServer() {
        context.transitToTargetServer(player);
    }

    protected void sendWarning() {
        context.sendWarning(player);
    }

    protected void removeFromFlowPipe() {

    }

    protected boolean isNoOnlineAnyMore() {
        return player.isOnline();
    }

    protected boolean isInvited() {
        return false;
    }
}
