package com.example.demo.DTO;

public class ReviewCodeResponse {
    private String category;
    private String currentTimeComplexity;
    private String betterTimeComplexity;
    private String currentSpaceComplexity;
    private String betterSpaceComplexity;
    private String improvementSuggestion;
    private String finalVerdict;
    private String rawFeedback;

    public ReviewCodeResponse() {
    }

    public ReviewCodeResponse(String category,
                              String currentTimeComplexity,
                              String betterTimeComplexity,
                              String currentSpaceComplexity,
                              String betterSpaceComplexity,
                              String improvementSuggestion,
                              String finalVerdict,
                              String rawFeedback) {
        this.category = category;
        this.currentTimeComplexity = currentTimeComplexity;
        this.betterTimeComplexity = betterTimeComplexity;
        this.currentSpaceComplexity = currentSpaceComplexity;
        this.betterSpaceComplexity = betterSpaceComplexity;
        this.improvementSuggestion = improvementSuggestion;
        this.finalVerdict = finalVerdict;
        this.rawFeedback = rawFeedback;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCurrentTimeComplexity() {
        return currentTimeComplexity;
    }

    public void setCurrentTimeComplexity(String currentTimeComplexity) {
        this.currentTimeComplexity = currentTimeComplexity;
    }

    public String getBetterTimeComplexity() {
        return betterTimeComplexity;
    }

    public void setBetterTimeComplexity(String betterTimeComplexity) {
        this.betterTimeComplexity = betterTimeComplexity;
    }

    public String getCurrentSpaceComplexity() {
        return currentSpaceComplexity;
    }

    public void setCurrentSpaceComplexity(String currentSpaceComplexity) {
        this.currentSpaceComplexity = currentSpaceComplexity;
    }

    public String getBetterSpaceComplexity() {
        return betterSpaceComplexity;
    }

    public void setBetterSpaceComplexity(String betterSpaceComplexity) {
        this.betterSpaceComplexity = betterSpaceComplexity;
    }

    public String getImprovementSuggestion() {
        return improvementSuggestion;
    }

    public void setImprovementSuggestion(String improvementSuggestion) {
        this.improvementSuggestion = improvementSuggestion;
    }

    public String getFinalVerdict() {
        return finalVerdict;
    }

    public void setFinalVerdict(String finalVerdict) {
        this.finalVerdict = finalVerdict;
    }

    public String getRawFeedback() {
        return rawFeedback;
    }

    public void setRawFeedback(String rawFeedback) {
        this.rawFeedback = rawFeedback;
    }
}