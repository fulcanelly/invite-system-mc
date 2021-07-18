package me.fulcanelly.inguard.client.states;

import org.bukkit.entity.Player;
import lombok.AllArgsConstructor;


public class JustJoinedPlayerState extends PlayerState {

    public JustJoinedPlayerState(Visitor visitor) {
        super(visitor);
    }

    @Override
    public PlayerState nextState() {
        if (visitor.isInvited()) {
            return new InvitedPlayerState(visitor);
        } else {
            return new UnknownPlayerState(visitor);
        }
    }

    @Override
    public void update() {
    }
    
}
