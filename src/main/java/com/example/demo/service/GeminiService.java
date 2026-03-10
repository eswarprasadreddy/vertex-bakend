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
    public String buildReviewPrompt(String questionText, String language, String code) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("You are an expert DSA interview evaluator.\n\n");

        prompt.append("Question:\n");
        prompt.append(questionText).append("\n\n");

        prompt.append("Programming Language:\n");
        prompt.append(language).append("\n\n");

        prompt.append("Candidate Code:\n");
        prompt.append(code).append("\n\n");

        prompt.append("Your tasks:\n");
        prompt.append("1. First classify the solution into one of the following categories:\n");
        prompt.append("BROKEN -> code likely has compile/runtime issues or clearly incorrect logic\n");
        prompt.append("SUBOPTIMAL -> solution works but not the best complexity\n");
        prompt.append("OPTIMAL -> solution is correct and efficient\n\n");

        prompt.append("Return response strictly in this format:\n\n");

        prompt.append("Category:\n");
        prompt.append("Correctness:\n");
        prompt.append("Compile Issues:\n");
        prompt.append("Runtime Issues:\n");
        prompt.append("Performance Issues:\n");
        prompt.append("Time Complexity:\n");
        prompt.append("Space Complexity:\n");
        prompt.append("Improvement Suggestion:\n");
        prompt.append("Final Verdict:\n\n");

        prompt.append("Rules:\n");
        prompt.append("- If category is BROKEN, focus mainly on compile/runtime issues.\n");
        prompt.append("- If category is SUBOPTIMAL, suggest improvement but do not give full optimal code.\n");
        prompt.append("- If category is OPTIMAL, say the solution is interview ready.\n");
        prompt.append("- Never reveal optimal code in this review stage.\n");

        return prompt.toString();
    }

    public String reviewCode(String questionText, String language, String code) {
        String prompt = buildReviewPrompt(questionText, language, code);

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