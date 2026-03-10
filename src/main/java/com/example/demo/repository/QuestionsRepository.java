package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Question;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {
    List<Question> findByTopicAndDifficulty(String topic,String difficulty);


    List<Question> findByTopic(String topic);

    List<Question> findByDifficulty(String difficulty);
}
