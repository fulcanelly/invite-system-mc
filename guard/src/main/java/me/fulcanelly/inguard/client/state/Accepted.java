package me.fulcanelly.inguard.client.state;



public class Accepted implements BaseState {

    @Override
    public BaseState update(PlayerContext ctx) {
        ctx.moveToServer();
        return null;
    }
    
}
