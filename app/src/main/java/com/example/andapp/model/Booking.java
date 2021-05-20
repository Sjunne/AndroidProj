package com.example.andapp.model;

public class Booking {
    private String userId;
    private String category;
    private String date;
    private String timeBooked;
    private String stringForFirebaseQuery;

public Booking()
{

}

    public Booking(String userId,  String date, String timeBooked, String category) {
        this.userId = userId;
        this.date = date;
        this.timeBooked = timeBooked;
        this.category = category;
    }

    public String getStringForFirebaseQuery() {
        return stringForFirebaseQuery;
    }

    public void setStringForFirebaseQuery(String stringForFirebaseQuery) {
        this.stringForFirebaseQuery = stringForFirebaseQuery;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeBooked() {
        return timeBooked;
    }

    public void setTimeBooked(String timeBooked) {
        this.timeBooked = timeBooked;
    }
}
