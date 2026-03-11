package com.example.demo.controller;

import com.example.demo.DTO.*;
import com.example.demo.entity.Question;
import com.example.demo.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class Problem {

    private final QuestionService service;

    public Problem(QuestionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody QuestionRequest req) {
        Question q = service.create(req);
        return ResponseEntity.status(201).body(q);
    }

    @PostMapping("/generate-questions")
    public ResponseEntity<GenerateQuestionResponse> generateQuestions(@RequestBody GenerateQuestionRequest req) {
        return ResponseEntity.ok(service.generateQuestions(req));
    }

    // 1) Run -> only example cases from question
    @PostMapping("/run-code")
    public ResponseEntity<JudgeCodeResponse> runCode(@RequestBody ReviewCodeRequest request) {
        return ResponseEntity.ok(service.runCode(request));
    }

    // 2) Submit -> stricter hidden/edge-case style judging
    @PostMapping("/submit-code")
    public ResponseEntity<JudgeCodeResponse> submitCode(@RequestBody ReviewCodeRequest request) {
        return ResponseEntity.ok(service.submitCode(request));
    }

    // 3) AI Review -> only better approach + complexity comparison
    @PostMapping("/review-code")
    public ResponseEntity<ReviewCodeResponse> reviewCode(@RequestBody ReviewCodeRequest request) {
        return ResponseEntity.ok(service.reviewCode(request));
    }

    @GetMapping
    public ResponseEntity<Page<Question>> getAllQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String pattern,
            @RequestParam(required = false) String difficulty
    ) {
        return ResponseEntity.ok(service.getQuestions(page, size, topic, pattern, difficulty));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        QuestionResponse q = service.getquestion(id);
        if (q == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(q);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Question>> search(
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String difficulty
    ) {
        return ResponseEntity.ok(service.search(topic, difficulty));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long id, @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<Page<QuestionSummary>> getQuestionList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(service.getQuestionSummaries(page, size));
    }
}