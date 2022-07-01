package com.example.dogwalk.Backend.Objects;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class DogObject {

    private String id = "";
    public List<StatsObject> stats = new ArrayList<>();

    public List<StatsObject> getStats() {
        return stats;
    }

    public void setStats(List<StatsObject> stats) {
        this.stats = stats;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getFoodCounter() {
        return foodCounter;
    }

    public void setFoodCounter(int foodCounter) {
        this.foodCounter = foodCounter;
    }

    public int getWalkCounter() {
        return walkCounter;
    }

    public void setWalkCounter(int walkCounter) {
        this.walkCounter = walkCounter;
    }


    private Uri uri = null;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name = "";
    private String age = "";
    private String breed = "";
    public int foodCounter = 0;
    public int walkCounter = 0;

    public DogObject(String name, String age, String breed , String id) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.id = id;
    }

    public DogObject() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }
}
