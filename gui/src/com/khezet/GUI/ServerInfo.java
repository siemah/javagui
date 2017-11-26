package com.khezet.GUI;

/**
 * Created by acer on 24/11/2017.
 */
class ServerInfo {

    private int id;
    private String name;

    ServerInfo (int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
