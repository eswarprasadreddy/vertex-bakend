package com.example.demo.DTO;

public class QuestionResponse {
    private Long id;
    private String topic;
    private String difficulty;
    private String question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public QuestionResponse(){}
    public QuestionResponse(Long id, String topic, String difficulty, String question){
        this.id=id;
        this.topic=topic;
        this.difficulty=difficulty;
        this.question=question;

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
