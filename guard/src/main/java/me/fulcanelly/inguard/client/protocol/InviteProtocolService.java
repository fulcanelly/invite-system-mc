package me.fulcanelly.inguard.client.protocol;

import java.util.function.Function;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

import me.fulcanelly.inguard.logger.InviteProtocolLogger;
import me.fulcanelly.inguard.utils.SocketTalker;
import me.fulcanelly.inguard.utils.NeedRequestRepeatException;

public class InviteProtocolService implements InviteProtocol {

    @Inject
    SocketTalker io;

    @Inject
    InviteProtocolLogger logger;

    public synchronized void pingKeepAlive() {
        logger.logPing();
        io.println("p");
        io.readLine();
    }

    public synchronized boolean checkIsInvited(String player) {
        logger.logIniteCheckFor(player);
        io.println("?");
        io.println(player);
        return Boolean.valueOf(io.readLine());
    }

}