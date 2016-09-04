package com.example.santa.timeout;

/**
 * Created by santa on 16/8/26.
 */
public interface OnTimeoutListener {
    void onTimePoint(String hour, String minute, String second);
    void onTimeout();
}
