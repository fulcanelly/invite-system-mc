package me.fulcanelly.insyscore.server;

import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
class CommandArgumentsParser {

    @NonNull Method method;
    List<Method> expected;
    
    @SneakyThrows
    String readLine(BufferedReader reader) {
        return reader.readLine();
    }
    
    @SneakyThrows
    Object convertToExpectedType(Method mapper, String line) {
        return mapper.invoke(null, line); 
    }

    Object[] parseParams(BufferedReader in) {
        System.out.println("expected to get " + expected);
        return expected.stream()
            .map(
                mapper -> {
                    var line = readLine(in);
                    System.out.println("read line: " + line);
                    return convertToExpectedType(mapper, line);
            })
            .collect(Collectors.toList())
            .toArray();
    }

    @SneakyThrows
    Method getMatchingValueOfMethod(Class<?> type) {
        if (type.equals(String.class)) {
            return type.getMethod("valueOf", Object.class);
        } else {
            return type.getMethod("valueOf", String.class);
        }
    }

    @SneakyThrows
    void parseMethodArgumetns() {
        LinkedList<Method> parsers = new LinkedList<>();
        for (var param : method.getParameterTypes()) {
            parsers.add(getMatchingValueOfMethod(param));
        }
        expected = parsers;
    }   

}