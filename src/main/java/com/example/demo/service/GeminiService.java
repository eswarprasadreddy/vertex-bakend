package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String buildGeneratePrompt(String topic, String pattern, String difficulty, int count) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are an expert DSA interview question generator.\n\n");
        prompt.append("Generate ").append(count).append(" original DSA interview questions.\n");
        prompt.append("Topic: ").append(topic).append("\n");

        if (pattern != null && !pattern.trim().isEmpty()) {
            prompt.append("Pattern: ").append(pattern).append("\n");
        }

        prompt.append("Difficulty: ").append(difficulty).append("\n\n");

        prompt.append("IMPORTANT OUTPUT FORMAT RULES:\n");
        prompt.append("- Return each question using this exact format:\n");
        prompt.append("###QUESTION_START###\n");
        prompt.append("Title:\n");
        prompt.append("Problem Statement:\n");
        prompt.append("Constraints:\n");
        prompt.append("Example 1:\n");
        prompt.append("Example 2:\n");
        prompt.append("Hint:\n");
        prompt.append("###QUESTION_END###\n\n");

        prompt.append("- Do not use numbering like 1. 2. 3.\n");
        prompt.append("- Do not add any introduction before the first question.\n");
        prompt.append("- Do not add any summary after the last question.\n");
        prompt.append("- Keep each question interview-style and original.\n");

        return prompt.toString();
    }

    public String generateQuestions(String topic, String pattern, String difficulty, int count) {
        String prompt = buildGeneratePrompt(topic, pattern, difficulty, count);
        return callGemini(prompt);
    }

    public String buildRunPrompt(String questionText, String language, String code) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are an interview coding judge.\n\n");
        prompt.append("Question:\n").append(questionText).append("\n\n");
        prompt.append("Language:\n").append(language).append("\n\n");
        prompt.append("Candidate Code:\n").append(code).append("\n\n");

        prompt.append("Evaluate the code ONLY against the example cases explicitly present in the question text.\n");
        prompt.append("Do not invent hidden edge cases here.\n\n");

        prompt.append("Return strictly in this format:\n");
        prompt.append("Verdict:\n");
        prompt.append("Passed Examples:\n");
        prompt.append("Time Complexity:\n");
        prompt.append("Space Complexity:\n");
        prompt.append("Feedback:\n");

        return prompt.toString();
    }

    public String buildSubmitPrompt(String questionText, String language, String code) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are a strict interview judge.\n\n");
        prompt.append("Question:\n").append(questionText).append("\n\n");
        prompt.append("Language:\n").append(language).append("\n\n");
        prompt.append("Candidate Code:\n").append(code).append("\n\n");

        prompt.append("Evaluate the solution more strictly than normal examples.\n");
        prompt.append("Consider edge cases like empty input, single element, duplicates, negatives, boundaries, large N, invalid assumptions.\n");
        prompt.append("Do NOT provide corrected code.\n\n");

        prompt.append("Return strictly in this format:\n");
        prompt.append("Verdict:\n");
        prompt.append("Passed Examples:\n");
        prompt.append("Time Complexity:\n");
        prompt.append("Space Complexity:\n");
        prompt.append("Feedback:\n");

        return prompt.toString();
    }

    public String buildReviewPrompt(String questionText, String language, String code) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are an expert DSA interview evaluator.\n\n");
        prompt.append("Question:\n").append(questionText).append("\n\n");
        prompt.append("Programming Language:\n").append(language).append("\n\n");
        prompt.append("Candidate Code:\n").append(code).append("\n\n");

        prompt.append("Your only task is to determine whether there is a better approach and compare complexities.\n");
        prompt.append("Do not focus on example test-case execution.\n");
        prompt.append("Do not provide full optimal code.\n\n");

        prompt.append("Return strictly in this format:\n");
        prompt.append("Category:\n");
        prompt.append("Current Time Complexity:\n");
        prompt.append("Better Time Complexity:\n");
        prompt.append("Current Space Complexity:\n");
        prompt.append("Better Space Complexity:\n");
        prompt.append("Improvement Suggestion:\n");
        prompt.append("Final Verdict:\n");

        return prompt.toString();
    }

    public String runCode(String questionText, String language, String code) {
        return callGemini(buildRunPrompt(questionText, language, code));
    }

    public String submitCode(String questionText, String language, String code) {
        return callGemini(buildSubmitPrompt(questionText, language, code));
    }

    public String reviewCode(String questionText, String language, String code) {
        return callGemini(buildReviewPrompt(questionText, language, code));
    }

    private String callGemini(String prompt) {
        String url = "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> part = new HashMap<>();
        part.put("text", prompt);

        List<Map<String, Object>> parts = new ArrayList<>();
        parts.add(part);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", parts);

        List<Map<String, Object>> contents = new ArrayList<>();
        contents.add(content);

        Map<String, Object> body = new HashMap<>();
        body.put("contents", contents);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        Map responseBody = response.getBody();

        if (responseBody == null) {
            throw new RuntimeException("Gemini returned empty response");
        }

        List candidates = (List) responseBody.get("candidates");
        if (candidates == null || candidates.isEmpty()) {
            throw new RuntimeException("Gemini returned no candidates: " + responseBody);
        }

        Map firstCandidate = (Map) candidates.get(0);
        Map contentMap = (Map) firstCandidate.get("content");
        List responseParts = (List) contentMap.get("parts");
        Map firstPart = (Map) responseParts.get(0);

        return (String) firstPart.get("text");
    }
}