package net.thedudemc.dudeconfig.examples.object;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

public class SomeObject {

    @Expose private String name;
    @Expose private int number;
    @Expose private float someFloat;
    @Expose private HashMap<Integer, Integer> levelExp;

    public SomeObject(String name, int number, float someFloat, HashMap<Integer, Integer> levelExp) {
        this.name = name;
        this.number = number;
        this.someFloat = someFloat;
        this.levelExp = levelExp;
    }

    @Override
    public String toString() {
        return "SomeObject{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", someFloat=" + someFloat +
                ", levelExp=" + levelExp +
                '}';
    }

    public String getName() {
        return this.name;
    }

    public int getNumber() {
        return number;
    }

    public float getSomeFloat() {
        return someFloat;
    }

    public HashMap<Integer, Integer> getLevelExp() {
        return this.levelExp;
    }
}
