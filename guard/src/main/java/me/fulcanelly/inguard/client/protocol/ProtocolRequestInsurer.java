package me.fulcanelly.inguard.client.protocol;

import java.net.SocketTimeoutException;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.fulcanelly.inguard.logger.InviteProtocolLogger;
import me.fulcanelly.inguard.utils.ExceptionProducingRunnable;
import me.fulcanelly.inguard.utils.NeedRequestRepeatException;
import me.fulcanelly.inguard.utils.SocketTalker;

public class ProtocolRequestInsurer {

    @Inject 
    SocketTalker io;

    @Inject @Named("insurer.max_attempts")
    int maxAttempts = 5;

    @Inject 
    InviteProtocolLogger logger;
    
    public <T extends ExceptionProducingRunnable<SocketTimeoutException>> void exeucteSafelyProtocolRequest(T runableWithRequest) {
        int count = 0;
        while (count++ < maxAttempts) {
            try { 
                runableWithRequest.run();
                return;
            } catch (NeedRequestRepeatException | SocketTimeoutException e) {
               // e.printStackTrace();
                logger.logGotRequestProblem();
                io.reconnect();
            }
        }   
        logger.logPoorAttempt();
    }
}
