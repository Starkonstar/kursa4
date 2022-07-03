package com.example.dogwalk.Backend.Objects;

import java.util.ArrayList;
import java.util.List;

public class StatsObject {

    int walk = 0;
    int food = 0;
    String date = "";
    public List<DayStatObject> dayStats = new ArrayList<>();
    public int getWalk() {
        return walk;
    }

    public StatsObject(int walk, int food, String date) {
        this.walk = walk;
        this.food = food;
        this.date = date;
    }
//
//    public void setWalk(int walk) {
//        this.walk = walk;
//    }
//
//    public int getFood() {
//        return food;
//    }
//
//    public void setFood(int food) {
//        this.food = food;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    public StatsObject(int walk, int food, String date, List<DayStatObject> dayStats) {
        this.walk = walk;
        this.food = food;
        this.date = date;
        this.dayStats = dayStats;
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

    public List<DayStatObject> getDayStats() {
        return dayStats;
    }

    public void setDayStats(List<DayStatObject> dayStats) {
        this.dayStats = dayStats;
    }
}
