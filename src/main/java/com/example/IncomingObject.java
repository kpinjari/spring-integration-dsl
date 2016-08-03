package com.example;

import java.io.Serializable;

/**
 * Created by szymon on 02.08.16.
 */
public class IncomingObject implements Serializable {

    private String argument;
    private ClientClass clientClass;

    public String getArgument() {
        return argument;
    }

    public IncomingObject setArgument(String argument) {
        this.argument = argument;
        return this;
    }

    public ClientClass getClientClass() {
        return clientClass;
    }

    public IncomingObject setClientClass(ClientClass clientClass) {
        this.clientClass = clientClass;
        return this;
    }
}
