package com.example.service;

import com.example.model.Question;
import com.example.model.QuizResult;
import com.example.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service class for Quiz operations
 * Contains business logic for quiz management, scoring, and results
 */
@Service
public class QuizService {
    
    @Autowired
    private QuizResultRepository quizResultRepository;
    
    @Autowired
    private QuestionService questionService;
    
    /**
     * Calculate quiz score and create result
     */
    public QuizResult calculateAndSaveResult(String studentName, String studentEmail, 
                                           List<Question> questions, Map<Long, String> answers, 
                                           long timeTaken, String category) {
        
        int totalQuestions = questions.size();
        int correctAnswers = 0;
        int incorrectAnswers = 0;
        
        // Calculate correct answers
        for (Question question : questions) {
            String providedAnswer = answers.get(question.getId());
            if (providedAnswer != null && providedAnswer.equalsIgnoreCase(question.getCorrectAnswer())) {
                correctAnswers++;
            } else {
                incorrectAnswers++;
            }
        }
        
        // Calculate percentage score
        double score = (totalQuestions > 0) ? (double) correctAnswers / totalQuestions * 100 : 0.0;
        
        // Generate feedback
        String feedback = generateFeedback(score, correctAnswers, totalQuestions, timeTaken);
        
        // Create and save quiz result
        QuizResult result = new QuizResult(studentName, studentEmail, totalQuestions, 
                                         correctAnswers, incorrectAnswers, score, 
                                         timeTaken, category, feedback);
        
        return quizResultRepository.save(result);
    }
    
    /**
     * Generate personalized feedback based on performance
     */
    private String generateFeedback(double score, int correctAnswers, int totalQuestions, long timeTaken) {
        StringBuilder feedback = new StringBuilder();
        
        // Performance feedback
        if (score >= 90) {
            feedback.append("Excellent work! You have a strong grasp of Computer Science concepts. ");
        } else if (score >= 80) {
            feedback.append("Great job! You performed very well. ");
        } else if (score >= 70) {
            feedback.append("Good work! You have a solid understanding. ");
        } else if (score >= 60) {
            feedback.append("Fair performance. Consider reviewing the topics you missed. ");
        } else if (score >= 50) {
            feedback.append("You're getting there! More practice needed in key areas. ");
        } else {
            feedback.append("Keep practicing! Focus on fundamental concepts. ");
        }
        
        // Time feedback
        int averageTimePerQuestion = (int) (timeTaken / totalQuestions);
        if (averageTimePerQuestion > 120) { // More than 2 minutes per question
            feedback.append("Take time to review questions carefully, but try to improve your speed. ");
        } else if (averageTimePerQuestion < 30) { // Less than 30 seconds per question
            feedback.append("Good speed! Make sure you're reading questions thoroughly. ");
        } else {
            feedback.append("Good time management! ");
        }
        
        // Specific recommendations
        if (score < 70) {
            feedback.append("Focus on: Data Structures, Algorithms, and Programming fundamentals. ");
        }
        
        return feedback.toString();
    }
    
    /**
     * Get all quiz results
     */
    public List<QuizResult> getAllResults() {
        return quizResultRepository.findAll();
    }
    
    /**
     * Get quiz result by ID
     */
    public Optional<QuizResult> getResultById(Long id) {
        return quizResultRepository.findById(id);
    }
    
    /**
     * Get results by student email
     */
    public List<QuizResult> getResultsByStudentEmail(String studentEmail) {
        return quizResultRepository.findByStudentEmail(studentEmail);
    }
    
    /**
     * Get results by category
     */
    public List<QuizResult> getResultsByCategory(String category) {
        return quizResultRepository.findByCategory(category);
    }
    
    /**
     * Get top scoring results
     */
    public List<QuizResult> getTopScores() {
        return quizResultRepository.findTopScores();
    }
    
    /**
     * Get recent quiz attempts
     */
    public List<QuizResult> getRecentAttempts() {
        return quizResultRepository.findRecentAttempts();
    }
    
    /**
     * Get quiz statistics
     */
    public Map<String, Object> getQuizStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalAttempts", quizResultRepository.getTotalAttempts());
        stats.put("averageScore", quizResultRepository.getAverageScore());
        stats.put("highestScore", quizResultRepository.getHighestScore());
        stats.put("lowestScore", quizResultRepository.getLowestScore());
        stats.put("totalQuestions", questionService.getTotalQuestions());
        stats.put("totalStudents", quizResultRepository.findAllStudentEmails().size());
        
        return stats;
    }
    
    /**
     * Get student statistics
     */
    public Map<String, Object> getStudentStatistics(String studentEmail) {
        Map<String, Object> stats = new HashMap<>();
        
        List<QuizResult> studentResults = getResultsByStudentEmail(studentEmail);
        stats.put("totalAttempts", studentResults.size());
        
        if (!studentResults.isEmpty()) {
            double avgScore = studentResults.stream()
                    .mapToDouble(QuizResult::getScore)
                    .average()
                    .orElse(0.0);
            
            double bestScore = studentResults.stream()
                    .mapToDouble(QuizResult::getScore)
                    .max()
                    .orElse(0.0);
            
            stats.put("averageScore", avgScore);
            stats.put("bestScore", bestScore);
            stats.put("latestAttempt", studentResults.get(0).getAttemptDate());
        } else {
            stats.put("averageScore", 0.0);
            stats.put("bestScore", 0.0);
            stats.put("latestAttempt", null);
        }
        
        return stats;
    }
    
    /**
     * Get performance by category
     */
    public List<Object[]> getPerformanceByCategory() {
        return quizResultRepository.getStatisticsByCategory();
    }
    
    /**
     * Delete quiz result
     */
    public void deleteResult(Long id) {
        quizResultRepository.deleteById(id);
    }
    
    /**
     * Check if student has taken quiz before
     */
    public boolean hasStudentTakenQuiz(String studentEmail) {
        return quizResultRepository.countByStudentEmail(studentEmail) > 0;
    }
    
    /**
     * Get student's best score
     */
    public Double getStudentBestScore(String studentEmail) {
        return quizResultRepository.getBestScoreByStudent(studentEmail);
    }
    
    /**
     * Generate quiz questions based on preferences
     */
    public List<Question> generateQuiz(String category, String difficulty, int questionCount) {
        List<Question> questions;
        
        if (category != null && !category.equals("All") && difficulty != null && !difficulty.equals("All")) {
            questions = questionService.getQuestionsByCategoryAndDifficulty(category, difficulty);
        } else if (category != null && !category.equals("All")) {
            questions = questionService.getRandomQuestionsByCategory(category, questionCount);
        } else if (difficulty != null && !difficulty.equals("All")) {
            questions = questionService.getRandomQuestionsByDifficulty(difficulty, questionCount);
        } else {
            questions = questionService.getRandomQuestions(questionCount);
        }
        
        // Limit to requested number of questions
        if (questions.size() > questionCount) {
            return questions.subList(0, questionCount);
        }
        
        return questions;
    }
}
