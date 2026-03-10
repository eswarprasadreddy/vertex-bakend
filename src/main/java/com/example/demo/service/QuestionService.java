package com.example.demo.service;

import com.example.demo.DTO.*;
import com.example.demo.entity.Question;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.spec.QuestionSpecs;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Question;
import com.example.demo.exception.QuestionNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionsRepository repo;
    private final GeminiService geminiService;

    public QuestionService(QuestionsRepository repo,GeminiService geminiService){
        this.repo=repo;
        this.geminiService=geminiService;
    }
    @CacheEvict(value="questions",allEntries = true)
    public Question create(QuestionRequest req){
        Question q=new Question(req.getTopic(),req.getDifficulty(),req.getQuestion(),req.getPattern(),req.getCreatedAt(),req.getSourceType());
        return repo.save(q);
    }
    public List<Question> getAll(){
        return repo.findAll();
    }
    public ReviewCodeResponse reviewCode(ReviewCodeRequest request) {

            Question q = repo.findById(request.getQuestionId())
                    .orElseThrow(() -> new QuestionNotFoundException(request.getQuestionId()));

            String feedback = geminiService.reviewCode(
                    q.getQuestion(),   // if your field name is questionText, use q.getQuestionText()
                    request.getLanguage(),
                    request.getCode()
            );
            feedback=feedback.replace("\\n","\n\n").trim();
            return new ReviewCodeResponse(feedback);
    }

    public GenerateQuestionResponse generateQuestions(GenerateQuestionRequest request){
        System.out.println("generate questions method entered");
        String aiResponse=geminiService.generateQuestions(
                request.getTopic(),
                request.getDifficulty(),
                request.getPattern(),
                request.getCount()

        );

        System.out.println(aiResponse);
        String[] parts=aiResponse.split("###QUESTION_START###" );
        int savedCount=0;
        for(String part:parts){
            String questionText=part.replace("###QUESTION_END###","").trim();
            if(!questionText.isEmpty()){
                Question q=new Question();
                q.setTopic(request.getTopic());
                q.setPattern(request.getPattern());
                q.setDifficulty(request.getDifficulty());
                q.setQuestion(questionText);
                q.setSourceType("AI");
                q.setCreatedAt(LocalDateTime.now());
                repo.save(q);
                savedCount++;
            }
        }
        return new GenerateQuestionResponse(savedCount);
    }

    public List<Question> getByTopicAndDifficulty(String topic, String difficulty) {
        return repo.findByTopicAndDifficulty(topic,difficulty);
    }
    public String clean(String s){

        if(s==null) return null;
        s=s.trim();
        return s.isEmpty()?null:s;
    }
    public List<Question> search(String topic,String difficulty){
        topic =clean(topic);
        difficulty=clean(difficulty);
        Specification<Question> spec=Specification.where(null);

        if(topic!=null) spec=spec.and(QuestionSpecs.hasTopic(topic));
        if(difficulty!=null) spec=spec.and(QuestionSpecs.hasDifficulty(difficulty));
        return repo.findAll(spec);

    }
    @Cacheable("questions")
    public QuestionResponse getquestion(Long id) {
        Question q=repo.findById(id).orElseThrow(()->new QuestionNotFoundException(id));

        return new QuestionResponse(q.getId(),q.getTopic(),q.getDifficulty(),q.getQuestion());

    }
    @CacheEvict(value="questions",key="#id")
    public QuestionResponse update(Long id, QuestionRequest request) {
        Question question  = repo.findById(id).orElseThrow(()-> new QuestionNotFoundException(id));


        question.setTopic(request.getTopic());
        question.setDifficulty(request.getDifficulty());
        question.setQuestion(request.getQuestion());

        Question q=repo.save(question);
        return new QuestionResponse(q.getId(),q.getTopic(), q.getDifficulty(), q.getQuestion());
    }
    @CacheEvict(value="questions",key="id")
    public void delete(Long id){

        if(!repo.existsById(id)){
            throw new QuestionNotFoundException(id);
        }
        repo.deleteById(id);



    }

    public Page<Question> getQuestions(int page, int size, String topic, String pattern, String difficulty) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Specification<Question> spec = Specification.where(null);

        if (topic != null && !topic.trim().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("topic")), topic.trim().toLowerCase()));
        }

        if (pattern != null && !pattern.trim().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("pattern")), pattern.trim().toLowerCase()));
        }

        if (difficulty != null && !difficulty.trim().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("difficulty")), difficulty.trim().toLowerCase()));
        }

        return repo.findAll(spec, pageable);
    }
    private String extractTitle(String questionText) {

        if (questionText == null) return "Question";

        String[] lines = questionText.split("\n");

        for (String line : lines) {
            if (line.toLowerCase().startsWith("title:")) {
                return line.replace("Title:", "").trim();
            }
        }

        return "Question";
    }
    public Page<QuestionSummary> getQuestionSummaries(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Question> questionPage = repo.findAll(pageable);

        return questionPage.map(q ->
                new QuestionSummary(
                        q.getId(),
                        q.getTopic(),
                        q.getPattern(),
                        q.getDifficulty(),
                        extractTitle(q.getQuestion())
                )
        );
    }
}
