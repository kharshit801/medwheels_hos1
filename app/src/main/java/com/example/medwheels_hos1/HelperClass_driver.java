package com.example.medwheels_hos1;

public class HelperClass_driver {
    String email,pass;

    public HelperClass_driver(String email,String pass) {
        this.email = email;
        this.pass=pass;
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
}
