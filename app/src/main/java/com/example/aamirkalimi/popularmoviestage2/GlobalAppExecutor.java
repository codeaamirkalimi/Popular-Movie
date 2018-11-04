package com.example.aamirkalimi.popularmoviestage2;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GlobalAppExecutor {
    private static final Object LOCK = new Object();
    private static GlobalAppExecutor sInstance;
    private final Executor diskIO, mainThread, networkIO;
    private GlobalAppExecutor(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }
    public static GlobalAppExecutor getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new GlobalAppExecutor(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), new MainThreadExecutor());
            }
        }
        return sInstance;
    }
    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
    public Executor diskIO() {
        return diskIO;
    }
    public Executor mainThread() {
        return mainThread;
    }
    public Executor networkIO() {
        return networkIO;
    }
}
