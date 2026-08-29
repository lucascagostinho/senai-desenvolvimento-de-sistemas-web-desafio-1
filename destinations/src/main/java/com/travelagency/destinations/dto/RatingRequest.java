package com.travelagency.destinations.dto;

public class RatingRequest {

    private Integer rating;

    public RatingRequest() {
    }

    public RatingRequest(Integer rating) {
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
