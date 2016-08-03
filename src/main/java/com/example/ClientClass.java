package com.example;

/**
 * Created by szymon on 02.08.16.
 */
public class ClientClass {

    private String name;

    private boolean enable;

    public String getName() {
        return name;
    }

    public ClientClass setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public ClientClass setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

}
