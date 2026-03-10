package com.example.demo.DTO;

public class QuestionSummary {

    private Long id;
    private String topic;
    private String pattern;
    private String difficulty;
    private String title;

    public QuestionSummary() {}

    public QuestionSummary(Long id, String topic, String pattern, String difficulty, String title) {
        this.id = id;
        this.topic = topic;
        this.pattern = pattern;
        this.difficulty = difficulty;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getPattern() {
        return pattern;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}