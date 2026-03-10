package com.example.demo.DTO;

public class ReviewCodeResponse {
    private String feedback;

    public ReviewCodeResponse() {
    }

    public ReviewCodeResponse(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
