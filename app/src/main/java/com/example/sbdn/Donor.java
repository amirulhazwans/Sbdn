package com.example.sbdn;

public class Donor {
    private String name;
    private String bloodType;
    private String location;

    public Donor(String name, String bloodType, String location) {
        this.name = name;
        this.bloodType = bloodType;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getLocation() {
        return location;
    }
}
