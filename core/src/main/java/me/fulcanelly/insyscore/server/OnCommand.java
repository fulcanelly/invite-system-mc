package me.fulcanelly.insyscore.server;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface OnCommand {
    String symbol();
}
