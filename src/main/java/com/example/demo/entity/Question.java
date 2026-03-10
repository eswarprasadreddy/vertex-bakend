package com.example.demo.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topic;
    private String difficulty;
    @Column(columnDefinition = "TEXT")
    private String question;
    private LocalDateTime createdAt;
    private String sourceType;
    private String pattern;
    public Question(){

    }
    public Question(String topic,String difficulty,String question,String sourceType,LocalDateTime createdAt,String pattern){
        this.topic=topic;
        this.difficulty=difficulty;
        this.question=question;
        this.sourceType=sourceType;
        this.createdAt=createdAt;
        this.pattern=pattern;
    }
    public Long getId(){return id;}
    public String getTopic(){return topic;}

    public String getDifficulty(){return difficulty;}



    public String getQuestion() {
        return question;
    }
    public void setTopic(String topic){
        this.topic =topic;

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setDifficulty(String difficulty){
        this.difficulty=difficulty;
    }
    public void setQuestion(String question){
        this.question=question;
    }

    public String getPattern() {
        return pattern;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
