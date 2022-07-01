package com.example.dogwalk.Backend.Objects;

public class StatsObject {

    int walk = 0;
    int food = 0;
    String date = "";

    public int getWalk() {
        return walk;
    }

    public StatsObject(int walk, int food, String date) {
        this.walk = walk;
        this.food = food;
        this.date = date;
    }

    public void setWalk(int walk) {
        this.walk = walk;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
