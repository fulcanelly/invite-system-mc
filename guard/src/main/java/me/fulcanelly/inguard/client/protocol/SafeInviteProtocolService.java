package me.fulcanelly.inguard.client.protocol;

import me.fulcanelly.inguard.utils.NeedRequestRepeatException;

public class SafeInviteProtocolService extends InviteProtocolService {

    public void exeucteSafelyProtocolRequest(Runnable runableWithRequest) {
        while (true) {
            try { 
                runableWithRequest.run();
                return;
            } catch(NeedRequestRepeatException e) {
                logger.getRequestProblem();
                io.reconnect();
            }
        }   
    }

}