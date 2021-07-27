package me.fulcanelly;
import static org.junit.Assert.assertEquals;

import java.net.ServerSocket;

import com.google.inject.Guice;

import org.bukkit.Bukkit;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;
import me.fulcanelly.inguard.MainModule;
import me.fulcanelly.inguard.client.RequestPlanner;
import me.fulcanelly.mocks.MockPlugin;

public class PluginTest {
    
    @SneakyThrows
    static void mockServer() {
        var socket = new ServerSocket(6997);
        socket.accept();
        socket.close();
    }

    public static void main(String[] args) {
        System.out.println(Bukkit.getServer());
        new Thread(PluginTest::mockServer).start();
        var mainModule = new MainModule(
            new MockPlugin()
        );
        Guice.createInjector(mainModule)
            .getInstance(RequestPlanner.class)
            .start();
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
