package com.example.interim;

public class Mission {

    private String name;
    private String location;
    private String salary;
    private String type;

    public Mission(String name, String location, String salary, String type) {
        this.name = name;
        this.location = location;
        this.salary = salary;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getSalary() {
        return salary;
    }

    public String getType() {
        return type;
    }
}
