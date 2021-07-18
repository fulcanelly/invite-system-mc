package me.fulcanelly.inguard.client.states;

import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class PlayerState {
    
    Visitor visitor;

    void setContext(Visitor visitor) {
        this.visitor = visitor;
    }

    abstract PlayerState nextState();
    abstract void update();
    
}
