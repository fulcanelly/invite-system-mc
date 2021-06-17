package me.fulcanelly.inguard.client.protocol;

import me.fulcanelly.inguard.utils.NeedRequestRepeatException;

public class SafeInviteProtocolService extends InviteProtocolService {

    public void pingKeepAlive() throws NeedRequestRepeatException {
        while (true) {
            try {
                super.pingKeepAlive();
                return;
            } catch(NeedRequestRepeatException e) {
                logger.getRequestProblem();
                io.reconnect();
                continue;
            }
        }

    }

    public boolean checkIsInvited(String player) throws NeedRequestRepeatException {
        while (true) {
            try {
                return super.checkIsInvited(player);
            } catch(NeedRequestRepeatException e) {
                logger.getRequestProblem();
                io.reconnect();
                continue;
            }
        }
    }

}