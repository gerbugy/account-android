package com.gerbugy.account.secure;

public interface SecureTimerListener {

    void onSecureCounting(int seconds);

    void onSecureWarning(int seconds);

    void onSecureFinished();
}