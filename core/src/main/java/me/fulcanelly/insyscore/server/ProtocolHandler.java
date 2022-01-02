package me.fulcanelly.insyscore.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.Value;
import lombok.With;
import lombok.experimental.SuperBuilder;

@RequiredArgsConstructor
public abstract class ProtocolHandler extends Thread {

    @NonNull Socket socket;

    PrintWriter out;
    BufferedReader in;

    //Todo
    //remove thios maps to separate classes
    final Map<String, Method> reactors = getCommandsAndReactingMethods();
    final Map<String, Function<BufferedReader, Object[]>> suppliers = genSuppliers();

    boolean run = true;

    @SneakyThrows
    void setupIO() {
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(
            new InputStreamReader(socket.getInputStream())
        );
    }

    @SneakyThrows
    void setTimeout(int num) {
        socket.setSoTimeout(num);
    }

    Void stopSelf() {
        run = false;
        return null;
    }


    Object[] parserArgumentsForMethod(String symbol) {
        return suppliers.get(symbol).apply(in);
    }

    @SneakyThrows
    void processMethodForData(String symbol, Object... data) {
        var method = reactors.get(symbol);
        var result = method.invoke(this, data);
        out.println(result);
    }

    @SneakyThrows
    void loop() throws SocketTimeoutException {
        var symbol = in.readLine();
        if (symbol != null) {
            processMethodForData(symbol, parserArgumentsForMethod(symbol));
        } else {
            stopSelf();
        }
    }

    @SneakyThrows
    public void startProcessing() {

        while (run) {
            try {
                loop();
            } catch(SocketTimeoutException e) {
                shutDown();
            }
        }
        onDisconect();
    }

    void shutDown() {
        run = false;
    }

    Map<String, Method> getCommandsAndReactingMethods() {
        var map = new HashMap<String, Method>();
        for (var meth : this.getClass().getDeclaredMethods()) {
            if (meth.isAnnotationPresent(OnCommand.class)) {
                map.put(
                    meth.getAnnotation(OnCommand.class).symbol(), meth
                );
            }
        }
        return map;
    }

    Map<String, Function<BufferedReader, Object[]>> genSuppliers() {
        var map = new HashMap<String, Function<BufferedReader, Object[]>>();

        for (var name : reactors.keySet()) {
            var parser = new CommandArgumentsParser(reactors.get(name));
            parser.parseMethodArgumetns();
            map.put(name, parser::parseParams);
        }

        return map;
    }

    @Override
    public void run() {
        startProcessing();
    }

    abstract void onDisconect();
}




