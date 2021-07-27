package me.fulcanelly;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.net.ServerSocket;

import com.google.inject.Guice;

import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;
import me.fulcanelly.inguard.MainModule;
import me.fulcanelly.inguard.client.RequestPlanner;

public class PluginTest {
    
    @SneakyThrows
    static void mockServer() {
        var socket = new ServerSocket(6997);
        socket.accept();
        socket.close();
    }

    @Test
    public void testIt() {
        new Thread(PluginTest::mockServer).start();

        var mainModule = new MainModule(
            mock(Plugin.class)
        );

        Guice.createInjector(mainModule)
            .getInstance(RequestPlanner.class)
            .start();

    }
}
