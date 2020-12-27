package com.example.transportationManagement.Model;

import com.example.transportationManagement.Entities.Travel;

import java.util.List;

public class RegisteredItem {
    private String source;
    private List <String> destinations;
    private String date;
    private Enum<Travel.RequestType> status;
    private List<String> company;

    public RegisteredItem(String source, List<String> destinations, String date, Enum<Travel.RequestType> status, List<String> company) {
        this.source = source;
        this.destinations = destinations;
        this.date = date;
        this.status = status;
        this.company = company;
    }

    public String getSource() {
        return source;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public String getDate() {
        return date;
    }

    public Enum<Travel.RequestType> getStatus() {
        return status;
    }

    public List<String> getCompany() {
        return company;
    }
}
