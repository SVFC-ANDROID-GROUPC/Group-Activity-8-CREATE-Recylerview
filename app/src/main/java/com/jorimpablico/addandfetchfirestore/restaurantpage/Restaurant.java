package com.jorimpablico.addandfetchfirestore.restaurantpage;

public class Restaurant {
    private String name;
    private String type;

    public Restaurant(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Restaurant() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
