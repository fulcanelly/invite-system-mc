package me.fulcanelly.inguard;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.fulcanelly.inguard.client.RequestPlanner;
import me.fulcanelly.inguard.client.protocol.InviteProtocol;
import me.fulcanelly.inguard.client.protocol.InviteProtocolService;
import me.fulcanelly.inguard.client.protocol.SafeInviteProtocolService;
import me.fulcanelly.inguard.logger.SocketIOLogger;
import me.fulcanelly.inguard.utils.ConnectionCreator;
import me.fulcanelly.inguard.utils.PlayersSpecificScheduledTaskExecutor;
import me.fulcanelly.inguard.utils.SocketTalker;

@AllArgsConstructor
public class MainModule extends AbstractModule {

    Plugin plugin;

    @Override @SneakyThrows
    public void configure() {
        bind(Plugin.class).toInstance(plugin);
        bind(Server.class).toInstance(plugin.getServer());

        bindConstant()
            .annotatedWith(Names.named("executor.interval"))
            .to(300);

        bindConstant()
            .annotatedWith(Names.named("max ping before die"))
            .to(3l);
            
        bindConstant()
            .annotatedWith(Names.named("sender.delay"))
            .to(4000l);

        bind(ConnectionCreator.class)
            .toInstance(
                new ConnectionCreator("localhost", 6997, 3000)
            );
    
        bind(SocketTalker.class).toConstructor(
            SocketTalker.class.getConstructor(ConnectionCreator.class, SocketIOLogger.class)
        );
    
        bind(InviteProtocol.class)
            .to(SafeInviteProtocolService.class)
            .in(Scopes.SINGLETON);

        bind(PlayersSpecificScheduledTaskExecutor.class).to(RequestPlanner.class);

    }
}