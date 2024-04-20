package com.example.medwheels_hos1;

public class HelperClass_assign {
    String patEmail,driverEmail,patName,driverName;
    double latitude,longitude;

    public HelperClass_assign(String patEmail, String driverEmail, String patName, String driverName,double latitude,double longitude) {
        this.patEmail = patEmail;
        this.driverEmail = driverEmail;
        this.patName = patName;
        this.driverName = driverName;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getPatEmail() {
        return patEmail;
    }

    public void setPatEmail(String patEmail) {
        this.patEmail = patEmail;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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
