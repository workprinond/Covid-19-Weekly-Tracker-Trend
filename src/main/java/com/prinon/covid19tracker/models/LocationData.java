package com.prinon.covid19tracker.models;

public class LocationData {

    //private String state;
    private String country;
    private String state;
    private int latestTotalCases;
    private int diffFromPrevDay;
    private int diffFromPrevWeek;
    private double percentChange;
    private int latestTotalDeaths;
    private int latestTotalRecovery;
    private double latitude;
    private double longitude;

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



    public int getLatestTotalRecovery() {
        return latestTotalRecovery;
    }

    public void setLatestTotalRecovery(int latestTotalRecovery) {
        this.latestTotalRecovery = latestTotalRecovery;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    public int getLatestTotalDeaths() {
        return latestTotalDeaths;
    }

    public void setLatestTotalDeaths(int latestTotalDeaths) {
        this.latestTotalDeaths = latestTotalDeaths;
    }

    public double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(double percentChange) {
        this.percentChange = percentChange;
    }

    public int getDiffFromPrevWeek() {
        return diffFromPrevWeek;
    }

    public void setDiffFromPrevWeek(int diffFromPrevWeek) {
        this.diffFromPrevWeek = diffFromPrevWeek;
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }





    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases + ", latitude=" + latitude + ", longitude=" + longitude +
                '}';
    }
}
