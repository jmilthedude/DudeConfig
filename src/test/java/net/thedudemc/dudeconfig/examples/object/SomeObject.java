package net.thedudemc.dudeconfig.examples.object;

import com.google.gson.annotations.Expose;

public class SomeObject {

    @Expose private String name;
    @Expose private int number;
    @Expose private float someFloat;

    public SomeObject(String name, int number, float someFloat) {
        this.name = name;
        this.number = number;
        this.someFloat = someFloat;
    }

    @Override
    public String toString() {
        return "SomeObject{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", someFloat=" + someFloat +
                '}';
    }
}
