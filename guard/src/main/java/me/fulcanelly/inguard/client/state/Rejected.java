package me.fulcanelly.inguard.client.state;

import lombok.AllArgsConstructor;
import lombok.With;

@AllArgsConstructor
public class Rejected implements BaseState {
    
    @With final long lastSendTime;

    boolean isTimeToSend(PlayerContext ctx) {
        return ctx.getCurrentTime() - lastSendTime >= ctx.getSendInterval();
    }

    @Override
    public BaseState update(PlayerContext ctx) {
        if (this.isTimeToSend(ctx)) {
            ctx.sendWarning();
            return new JustJoin();
        }
        return this;
    }
    
}
