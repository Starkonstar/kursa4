package com.example.dogwalk.Backend.Objects;

public class DayStatObject {
    int weight;
    String time;

    public DayStatObject(int weight, String time) {
        this.weight = weight;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
