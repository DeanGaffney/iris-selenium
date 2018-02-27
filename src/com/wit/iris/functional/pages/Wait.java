package com.wit.iris.functional.pages;

public enum Wait {

    SHORT(10000), MEDIUM(60000), LONG(900000);

    private final int waitTime;

    Wait(int waitTime){
        this.waitTime = waitTime;
    }

    public int getTime(){
        return this.waitTime;
    }
}
