package me.fulcanelly.inguard.client.state;

public interface PlayerContext {
    
    boolean isInvited();

    void sendWarning();


    void moveToServer();
    
    long getCurrentTime();

    long getSendInterval();

}
