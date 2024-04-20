package com.example.medwheels_hos1;

public class HelperClass_driver {
    String email, pass,name;
    double latitude, longitude;

    public HelperClass_driver(String email, String pass, double latitude, double longitude,String name) {
        this.email = email;
        this.pass = pass;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}