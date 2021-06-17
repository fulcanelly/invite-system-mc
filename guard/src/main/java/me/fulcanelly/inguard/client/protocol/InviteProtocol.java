package me.fulcanelly.inguard.client.protocol;

import me.fulcanelly.inguard.utils.NeedRequestRepeatException;

public interface InviteProtocol {
    void pingKeepAlive() throws NeedRequestRepeatException;
    boolean checkIsInvited(String player) throws NeedRequestRepeatException;
}