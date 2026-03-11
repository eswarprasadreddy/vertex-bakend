package com.example.demo.DTO;

public class JudgeCodeResponse {
    private String mode;          // RUN or SUBMIT
    private String verdict;       // Passed / Partially Passed / Wrong Answer / Runtime Risk / Compile Risk
    private String feedback;      // AI explanation
    private String passedExamples;
    private String timeComplexity;
    private String spaceComplexity;

    public JudgeCodeResponse() {
    }

    public JudgeCodeResponse(String mode, String verdict, String feedback,
                             String passedExamples, String timeComplexity, String spaceComplexity) {
        this.mode = mode;
        this.verdict = verdict;
        this.feedback = feedback;
        this.passedExamples = passedExamples;
        this.timeComplexity = timeComplexity;
        this.spaceComplexity = spaceComplexity;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getPassedExamples() {
        return passedExamples;
    }

    public void setPassedExamples(String passedExamples) {
        this.passedExamples = passedExamples;
    }

    public String getTimeComplexity() {
        return timeComplexity;
    }

    public void setTimeComplexity(String timeComplexity) {
        this.timeComplexity = timeComplexity;
    }

    public String getSpaceComplexity() {
        return spaceComplexity;
    }

    public void setSpaceComplexity(String spaceComplexity) {
        this.spaceComplexity = spaceComplexity;
    }
}