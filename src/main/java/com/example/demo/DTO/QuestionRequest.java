package com.example.demo.DTO;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class QuestionRequest {
    @NotBlank(message="This field is required")
    private String topic;
    @NotBlank(message = "This field is required")
    private String difficulty;
    @NotBlank(message = "This field is required")
    private String question;
    private Long id;
    String sourceType;
    String pattern;
    LocalDateTime createdAt;

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
