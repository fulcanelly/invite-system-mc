package me.fulcanelly.inguard.client.states;

public class UnknownPlayerState extends PlayerState {

    public UnknownPlayerState(Visitor visitor) {
        super(visitor);
    }

    @Override
    public PlayerState nextState() {
        return this;
    }

    @Override
    public void update() {
        visitor.sendWarning();
    }
    
}
