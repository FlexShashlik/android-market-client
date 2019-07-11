package com.flex.market;

import android.support.v4.app.FragmentTransaction;

class Singleton {
    private static final Singleton ourInstance = new Singleton();
    private static FragmentTransaction transaction;

    static Singleton getInstance() {
        return ourInstance;
    }

    FragmentTransaction getTransaction() {
        return transaction;
    }

    void setTransaction(FragmentTransaction var) {
        transaction = var;
    }
}
