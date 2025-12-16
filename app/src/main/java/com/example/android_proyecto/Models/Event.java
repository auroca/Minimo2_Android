package com.example.android_proyecto.Models;


public class Event {
    private int id;
    private String imageUrl;
    private String name;
    private String description;
    private String startDate;
    private String endDate;

    public Event() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}
