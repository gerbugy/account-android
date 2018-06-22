package com.gerbugy.account.secure;

public interface SecureTimerContext {

    int SECONDS_DEFAULT_FINISHED = 30;
    int SECONDS_DEFAULT_WARNING = 10;

    int MSG_START = 1;
    int MSG_STOP = 2;
    int MSG_COUNTING = 3;
    int MSG_WARNING = 4;
    int MSG_FINISHED = 5;
}