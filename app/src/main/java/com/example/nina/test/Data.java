package com.example.nina.test;

import android.app.Application;

/**
 * Created by Nina on 17/1/7.
 */

public class Data extends Application {
    private int hero1;
    private int hero2;

    public int getHero1() {
        return hero1;
    }

    public void setHero1(int hero1) {
        this.hero1 = hero1;
    }

    public int getHero2() {
        return hero2;
    }

    public void setHero2(int hero2) {
        this.hero2 = hero2;
    }

    @Override
    public void onCreate(){
        hero1=0;
        hero2=0;
        super.onCreate();
    }
}
