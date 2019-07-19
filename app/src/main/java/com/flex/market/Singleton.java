package com.flex.market;

import android.support.v4.app.FragmentManager;

class Singleton {
    private static final Singleton ourInstance = new Singleton();
    private static FragmentManager manager;

    static Singleton getInstance() {
        return ourInstance;
    }

    FragmentManager getManager() {
        return manager;
    }

    void setManager(FragmentManager var) {
        manager = var;
    }
}
