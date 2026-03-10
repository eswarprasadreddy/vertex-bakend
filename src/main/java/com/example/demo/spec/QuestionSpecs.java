package com.example.demo.spec;

import com.example.demo.entity.Question;
import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecs {
    public static Specification<Question> hasTopic(String topic){
        return (root, query, cb) -> cb.equal(cb.lower(root.get("topic")),topic.toLowerCase());
    }
    public static Specification<Question> hasDifficulty(String difficulty){
        return (root, query, cb) -> cb.equal(cb.lower(root.get("difficulty")),difficulty.toLowerCase());
    }
}
