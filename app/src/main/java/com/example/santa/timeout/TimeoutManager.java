package com.example.santa.timeout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by santa on 16/8/25.
 */
public class TimeoutManager {
    private long mLastTime;
    private Timeout mTimeout;
    private OnTimeRunListener mListener;
    private int MSG_GO = 1;
    private Thread mThread;

    public TimeoutManager(int hour, int minute, int second, OnTimeRunListener listener){
        mListener = listener;
        mTimeout = new Timeout(hour, minute, second);
        mLastTime = System.currentTimeMillis();

        new Thread(new TimeRun()).start();
    }

    public void resetTime(int hour, int minute, int second) {
        mTimeout.resetTime(hour, minute, second);
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_GO && null != mListener) {
                Bundle bundle = msg.getData();
                mListener.onTimeRun(bundle.getInt("hour"), bundle.getInt("minute"), bundle.getInt("second"));
            }
        }
    };


    private void timeRun() {
        long curTime = System.currentTimeMillis();
        if (curTime - mLastTime > 1000) {
            mTimeout.goOneSecond();
            mLastTime += 1000;
        }
    }

    private class TimeRun implements Runnable{

        @Override
        public void run() {
            while (!mTimeout.isFinish()) {
                try {
                    Thread.sleep(100);
                    timeRun();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class Timeout{
        private int hour;
        private int minute;
        private int second;

        public Timeout(int hour, int minute, int second) {
            resetTime(hour, minute, second);
        }

        public void resetTime(int hour, int minute, int second) {
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        }

        public void goOneSecond() {
            if (isFinish() || null == mListener) {
                return;
            }
            second --;

            if (second < 0) {
                second += 60;
                minute -= 1;
            }

            if (minute <0) {
                minute += 60;
                hour -= 1;
            }
            Message message = new Message();
            message.what = MSG_GO;
            Bundle bundle = new Bundle();
            bundle.putInt("hour", hour);
            bundle.putInt("minute", minute);
            bundle.putInt("second", second);
            message.setData(bundle);
            mHandler.sendMessage(message);
//            Log.d("DEBUG", hour+" : "+minute+" : "+second);
        }

        public boolean isFinish() {
            return hour ==  0 && minute == 0 && second == 0;
        }
    }

    public interface OnTimeRunListener{
        void onTimeRun(int hour, int minute, int second);
    }

}
