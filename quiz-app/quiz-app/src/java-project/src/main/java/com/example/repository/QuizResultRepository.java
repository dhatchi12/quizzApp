package com.example.repository;

import com.example.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for QuizResult entity
 * Provides database operations for quiz results and statistics
 */
@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    
    /**
     * Find quiz results by student email
     */
    List<QuizResult> findByStudentEmail(String studentEmail);
    
    /**
     * Find quiz results by student name
     */
    List<QuizResult> findByStudentNameContainingIgnoreCase(String studentName);
    
    /**
     * Find quiz results by category
     */
    List<QuizResult> findByCategory(String category);
    
    /**
     * Find quiz results between dates
     */
    List<QuizResult> findByAttemptDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find top scores (limit to top N)
     */
    @Query("SELECT qr FROM QuizResult qr ORDER BY qr.score DESC")
    List<QuizResult> findTopScores();
    
    /**
     * Find recent attempts (last N attempts)
     */
    @Query("SELECT qr FROM QuizResult qr ORDER BY qr.attemptDate DESC")
    List<QuizResult> findRecentAttempts();
    
    /**
     * Get average score for all attempts
     */
    @Query("SELECT AVG(qr.score) FROM QuizResult qr")
    Double getAverageScore();
    
    /**
     * Get average score by category
     */
    @Query("SELECT AVG(qr.score) FROM QuizResult qr WHERE qr.category = :category")
    Double getAverageScoreByCategory(@Param("category") String category);
    
    /**
     * Get highest score
     */
    @Query("SELECT MAX(qr.score) FROM QuizResult qr")
    Double getHighestScore();
    
    /**
     * Get lowest score
     */
    @Query("SELECT MIN(qr.score) FROM QuizResult qr")
    Double getLowestScore();
    
    /**
     * Count total attempts
     */
    @Query("SELECT COUNT(qr) FROM QuizResult qr")
    Long getTotalAttempts();
    
    /**
     * Count attempts by student email
     */
    long countByStudentEmail(String studentEmail);
    
    /**
     * Find student's best score
     */
    @Query("SELECT MAX(qr.score) FROM QuizResult qr WHERE qr.studentEmail = :studentEmail")
    Double getBestScoreByStudent(@Param("studentEmail") String studentEmail);
    
    /**
     * Find all distinct student emails (for statistics)
     */
    @Query("SELECT DISTINCT qr.studentEmail FROM QuizResult qr")
    List<String> findAllStudentEmails();
    
    /**
     * Get performance statistics by category
     */
    @Query("SELECT qr.category, COUNT(qr), AVG(qr.score), MAX(qr.score), MIN(qr.score) " +
           "FROM QuizResult qr GROUP BY qr.category")
    List<Object[]> getStatisticsByCategory();
}
