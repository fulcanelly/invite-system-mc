package me.fulcanelly.inguard.utils;

public interface ExceptionProducingRunnable<T extends Exception> {
    void run() throws T;
}