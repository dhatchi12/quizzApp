package com.example.service;

import com.example.model.Question;
import com.example.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Question entity
 * Contains business logic for managing quiz questions
 */
@Service
public class QuestionService {
    
    @Autowired
    private QuestionRepository questionRepository;
    
    /**
     * Get all questions
     */
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    
    /**
     * Get question by ID
     */
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }
    
    /**
     * Save a new question
     */
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }
    
    /**
     * Update an existing question
     */
    public Question updateQuestion(Question question) {
        return questionRepository.save(question);
    }
    
    /**
     * Delete a question by ID
     */
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
    
    /**
     * Get questions by category
     */
    public List<Question> getQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }
    
    /**
     * Get questions by difficulty
     */
    public List<Question> getQuestionsByDifficulty(String difficulty) {
        return questionRepository.findByDifficulty(difficulty);
    }
    
    /**
     * Get questions by category and difficulty
     */
    public List<Question> getQuestionsByCategoryAndDifficulty(String category, String difficulty) {
        return questionRepository.findByCategoryAndDifficulty(category, difficulty);
    }
    
    /**
     * Get random questions for quiz
     */
    public List<Question> getRandomQuestions(int limit) {
        return questionRepository.findRandomQuestions(limit);
    }
    
    /**
     * Get random questions by category
     */
    public List<Question> getRandomQuestionsByCategory(String category, int limit) {
        return questionRepository.findRandomQuestionsByCategory(category, limit);
    }
    
    /**
     * Get random questions by difficulty
     */
    public List<Question> getRandomQuestionsByDifficulty(String difficulty, int limit) {
        return questionRepository.findRandomQuestionsByDifficulty(difficulty, limit);
    }
    
    /**
     * Get all categories
     */
    public List<String> getAllCategories() {
        return questionRepository.findAllCategories();
    }
    
    /**
     * Get all difficulty levels
     */
    public List<String> getAllDifficulties() {
        return questionRepository.findAllDifficulties();
    }
    
    /**
     * Count questions by category
     */
    public long countQuestionsByCategory(String category) {
        return questionRepository.countByCategory(category);
    }
    
    /**
     * Count questions by difficulty
     */
    public long countQuestionsByDifficulty(String difficulty) {
        return questionRepository.countByDifficulty(difficulty);
    }
    
    /**
     * Get total number of questions
     */
    public long getTotalQuestions() {
        return questionRepository.count();
    }
    
    /**
     * Check if a question exists
     */
    public boolean questionExists(Long id) {
        return questionRepository.existsById(id);
    }
    
    /**
     * Validate question answer
     */
    public boolean isCorrectAnswer(Long questionId, String answer) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent()) {
            return question.get().getCorrectAnswer().equalsIgnoreCase(answer);
        }
        return false;
    }
    
    /**
     * Get explanation for a question
     */
    public String getQuestionExplanation(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        return question.map(Question::getExplanation).orElse("No explanation available.");
    }
}
