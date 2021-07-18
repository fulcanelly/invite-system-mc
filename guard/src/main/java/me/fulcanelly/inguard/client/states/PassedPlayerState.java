package me.fulcanelly.inguard.client.states;

public class PassedPlayerState extends PlayerState {

    public PassedPlayerState(Visitor visitor) {
        super(visitor);
    }

    @Override
    PlayerState nextState() {
        return this;
    }

    @Override
    void update() {
        visitor.removeFromFlowPipe();
    }
    
}
