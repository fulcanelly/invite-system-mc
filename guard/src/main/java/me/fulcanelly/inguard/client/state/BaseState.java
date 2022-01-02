package me.fulcanelly.inguard.client.state;

import java.util.Optional;

public interface BaseState {
    BaseState update(PlayerContext ctx);
}
