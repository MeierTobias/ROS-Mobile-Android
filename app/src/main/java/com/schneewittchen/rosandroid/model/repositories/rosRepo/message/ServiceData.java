package com.schneewittchen.rosandroid.model.repositories.rosRepo.message;

import java.lang.reflect.Method;

public class ServiceData {
    private final String command;
    private final Method commandMethodRef;

    public ServiceData(String cmd, Method mRef) {
        this.command = cmd;
        this.commandMethodRef = mRef;
    }

    public String getCommand() {
        return this.command;
    }

    public Method getCommandMethodRef() {
        return this.commandMethodRef;
    }
}
