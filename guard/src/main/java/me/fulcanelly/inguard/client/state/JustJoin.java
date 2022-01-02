package me.fulcanelly.inguard.client.state;

public class JustJoin implements BaseState {

   
    @Override
    public BaseState update(PlayerContext ctx) {
        if (ctx.isInvited()) {
            return new Accepted();
        }

        return new Rejected(ctx.getCurrentTime() - ctx.getSendInterval());
    }
}