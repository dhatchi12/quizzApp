package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing a quiz result/attempt for Computer Science Engineering students
 */
@Entity
@Table(name = "quiz_results")
public class QuizResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String studentName;
    
    @Column(nullable = false)
    private String studentEmail;
    
    @Column(nullable = false)
    private int totalQuestions;
    
    @Column(nullable = false)
    private int correctAnswers;
    
    @Column(nullable = false)
    private int incorrectAnswers;
    
    @Column(nullable = false)
    private double score; // Percentage score
    
    @Column(nullable = false)
    private LocalDateTime attemptDate;
    
    @Column(nullable = false)
    private long timeTaken; // Time taken in seconds
    
    @Column
    private String category; // Quiz category
    
    @Column(length = 1000)
    private String feedback; // Performance feedback
    
    // Default constructor
    public QuizResult() {
        this.attemptDate = LocalDateTime.now();
    }
    
    // Constructor
    public QuizResult(String studentName, String studentEmail, int totalQuestions, 
                     int correctAnswers, int incorrectAnswers, double score, 
                     long timeTaken, String category, String feedback) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = incorrectAnswers;
        this.score = score;
        this.timeTaken = timeTaken;
        this.category = category;
        this.feedback = feedback;
        this.attemptDate = LocalDateTime.now();
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getStudentEmail() {
        return studentEmail;
    }
    
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
    
    public int getTotalQuestions() {
        return totalQuestions;
    }
    
    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
    
    public int getCorrectAnswers() {
        return correctAnswers;
    }
    
    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
    
    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }
    
    public void setIncorrectAnswers(int incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }
    
    public double getScore() {
        return score;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    public LocalDateTime getAttemptDate() {
        return attemptDate;
    }
    
    public void setAttemptDate(LocalDateTime attemptDate) {
        this.attemptDate = attemptDate;
    }
    
    public long getTimeTaken() {
        return timeTaken;
    }
    
    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getFeedback() {
        return feedback;
    }
    
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    /**
     * Calculate and return grade based on score
     */
    public String getGrade() {
        if (score >= 90) return "A+";
        else if (score >= 80) return "A";
        else if (score >= 70) return "B+";
        else if (score >= 60) return "B";
        else if (score >= 50) return "C+";
        else if (score >= 40) return "C";
        else return "F";
    }
    
    @Override
    public String toString() {
        return "QuizResult{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", score=" + score +
                ", attemptDate=" + attemptDate +
                '}';
    }
}
