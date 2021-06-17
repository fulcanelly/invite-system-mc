package me.fulcanelly.inguard.client.protocol;

import me.fulcanelly.inguard.utils.NeedRequestRepeatException;

public interface InviteProtocol {
    void pingKeepAlive();
    boolean checkIsInvited(String player);
}