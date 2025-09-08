package com.example.repository;

import com.example.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Question entity
 * Provides database operations for quiz questions
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    /**
     * Find questions by category
     */
    List<Question> findByCategory(String category);
    
    /**
     * Find questions by difficulty level
     */
    List<Question> findByDifficulty(String difficulty);
    
    /**
     * Find questions by category and difficulty
     */
    List<Question> findByCategoryAndDifficulty(String category, String difficulty);
    
    /**
     * Get random questions for quiz
     * @param limit number of questions to fetch
     */
    @Query(value = "SELECT * FROM questions ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Question> findRandomQuestions(@Param("limit") int limit);
    
    /**
     * Get random questions by category
     * @param category the category to filter by
     * @param limit number of questions to fetch
     */
    @Query(value = "SELECT * FROM questions WHERE category = :category ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(@Param("category") String category, @Param("limit") int limit);
    
    /**
     * Get random questions by difficulty
     * @param difficulty the difficulty level to filter by
     * @param limit number of questions to fetch
     */
    @Query(value = "SELECT * FROM questions WHERE difficulty = :difficulty ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Question> findRandomQuestionsByDifficulty(@Param("difficulty") String difficulty, @Param("limit") int limit);
    
    /**
     * Get all distinct categories
     */
    @Query("SELECT DISTINCT q.category FROM Question q")
    List<String> findAllCategories();
    
    /**
     * Get all distinct difficulty levels
     */
    @Query("SELECT DISTINCT q.difficulty FROM Question q")
    List<String> findAllDifficulties();
    
    /**
     * Count questions by category
     */
    long countByCategory(String category);
    
    /**
     * Count questions by difficulty
     */
    long countByDifficulty(String difficulty);
}
