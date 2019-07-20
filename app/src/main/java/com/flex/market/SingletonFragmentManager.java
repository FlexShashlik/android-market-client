package com.flex.market;

import android.support.v4.app.FragmentManager;

class SingletonFragmentManager {
    private static final SingletonFragmentManager ourInstance = new SingletonFragmentManager();
    private static FragmentManager manager;

    static SingletonFragmentManager getInstance() {
        return ourInstance;
    }

    FragmentManager getManager() {
        return manager;
    }

    void setManager(FragmentManager var) {
        manager = var;
    }
}
