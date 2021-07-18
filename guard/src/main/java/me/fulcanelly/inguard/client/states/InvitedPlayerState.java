package me.fulcanelly.inguard.client.states;

public class InvitedPlayerState extends PlayerState {

    public InvitedPlayerState(Visitor visitor) {
        super(visitor);
    }

    @Override
    PlayerState nextState() {
        return new PassedPlayerState(visitor);
    }

    @Override
    void update() {
        visitor.transitToTargetServer();
    }
    
}
