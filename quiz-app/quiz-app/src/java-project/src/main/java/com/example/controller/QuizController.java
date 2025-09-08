package com.example.controller;

import com.example.model.Question;
import com.example.model.QuizResult;
import com.example.service.QuestionService;
import com.example.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main controller for the Computer Science Engineering Quiz Application
 * Handles all web requests and navigation between pages
 */
@Controller
public class QuizController {
    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private QuizService quizService;
    
    /**
     * Home page - Landing page with welcome message
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalQuestions", questionService.getTotalQuestions());
        model.addAttribute("categories", questionService.getAllCategories());
        return "index";
    }
    
    /**
     * About page - Information about the quiz application
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
    /**
     * Quiz setup page - Configure quiz parameters
     */
    @GetMapping("/quiz/setup")
    public String quizSetup(Model model) {
        model.addAttribute("categories", questionService.getAllCategories());
        model.addAttribute("difficulties", questionService.getAllDifficulties());
        return "quiz-setup";
    }
    
    /**
     * Start quiz - Initialize quiz session
     */
    @PostMapping("/quiz/start")
    public String startQuiz(@RequestParam String studentName,
                           @RequestParam String studentEmail,
                           @RequestParam(defaultValue = "All") String category,
                           @RequestParam(defaultValue = "All") String difficulty,
                           @RequestParam(defaultValue = "20") int questionCount,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        
        try {
            // Generate quiz questions
            List<Question> questions = quizService.generateQuiz(category, difficulty, questionCount);
            
            if (questions.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "No questions available for the selected criteria. Please try different options.");
                return "redirect:/quiz/setup";
            }
            
            // Store quiz session data
            session.setAttribute("studentName", studentName);
            session.setAttribute("studentEmail", studentEmail);
            session.setAttribute("questions", questions);
            session.setAttribute("category", category);
            session.setAttribute("currentQuestionIndex", 0);
            session.setAttribute("answers", new HashMap<Long, String>());
            session.setAttribute("startTime", System.currentTimeMillis());
            
            return "redirect:/quiz/question";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error starting quiz: " + e.getMessage());
            return "redirect:/quiz/setup";
        }
    }
    
    /**
     * Display current quiz question
     */
    @GetMapping("/quiz/question")
    public String showQuestion(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        
        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Integer currentIndex = (Integer) session.getAttribute("currentQuestionIndex");
        
        if (questions == null || currentIndex == null) {
            redirectAttributes.addFlashAttribute("error", "Quiz session expired. Please start a new quiz.");
            return "redirect:/quiz/setup";
        }
        
        if (currentIndex >= questions.size()) {
            return "redirect:/quiz/submit";
        }
        
        Question currentQuestion = questions.get(currentIndex);
        model.addAttribute("question", currentQuestion);
        model.addAttribute("questionNumber", currentIndex + 1);
        model.addAttribute("totalQuestions", questions.size());
        model.addAttribute("progress", ((currentIndex + 1) * 100) / questions.size());
        
        return "quiz-question";
    }
    
    /**
     * Handle answer submission and navigate to next question
     */
    @PostMapping("/quiz/answer")
    public String submitAnswer(@RequestParam Long questionId,
                              @RequestParam String answer,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        
        @SuppressWarnings("unchecked")
        Map<Long, String> answers = (Map<Long, String>) session.getAttribute("answers");
        Integer currentIndex = (Integer) session.getAttribute("currentQuestionIndex");
        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        
        if (answers == null || currentIndex == null || questions == null) {
            redirectAttributes.addFlashAttribute("error", "Quiz session expired. Please start a new quiz.");
            return "redirect:/quiz/setup";
        }
        
        // Store the answer
        answers.put(questionId, answer);
        
        // Move to next question
        session.setAttribute("currentQuestionIndex", currentIndex + 1);
        
        // Check if quiz is complete
        if (currentIndex + 1 >= questions.size()) {
            return "redirect:/quiz/submit";
        }
        
        return "redirect:/quiz/question";
    }
    
    /**
     * Submit quiz and calculate results
     */
    @GetMapping("/quiz/submit")
    public String submitQuiz(HttpSession session, RedirectAttributes redirectAttributes) {
        
        String studentName = (String) session.getAttribute("studentName");
        String studentEmail = (String) session.getAttribute("studentEmail");
        String category = (String) session.getAttribute("category");
        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        @SuppressWarnings("unchecked")
        Map<Long, String> answers = (Map<Long, String>) session.getAttribute("answers");
        Long startTime = (Long) session.getAttribute("startTime");
        
        if (studentName == null || questions == null || answers == null || startTime == null) {
            redirectAttributes.addFlashAttribute("error", "Quiz session expired. Please start a new quiz.");
            return "redirect:/quiz/setup";
        }
        
        try {
            // Calculate time taken
            long timeTaken = (System.currentTimeMillis() - startTime) / 1000; // in seconds
            
            // Calculate and save result
            QuizResult result = quizService.calculateAndSaveResult(
                studentName, studentEmail, questions, answers, timeTaken, category
            );
            
            // Clear session
            session.invalidate();
            
            // Redirect to results page
            redirectAttributes.addAttribute("resultId", result.getId());
            return "redirect:/quiz/result";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error submitting quiz: " + e.getMessage());
            return "redirect:/quiz/setup";
        }
    }
    
    /**
     * Display quiz results
     */
    @GetMapping("/quiz/result")
    public String showResult(@RequestParam Long resultId, Model model, RedirectAttributes redirectAttributes) {
        
        try {
            QuizResult result = quizService.getResultById(resultId).orElse(null);
            
            if (result == null) {
                redirectAttributes.addFlashAttribute("error", "Quiz result not found.");
                return "redirect:/";
            }
            
            model.addAttribute("result", result);
            
            // Calculate time in minutes and seconds
            long totalSeconds = result.getTimeTaken();
            long minutes = totalSeconds / 60;
            long seconds = totalSeconds % 60;
            model.addAttribute("timeMinutes", minutes);
            model.addAttribute("timeSeconds", seconds);
            
            return "quiz-result";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error loading result: " + e.getMessage());
            return "redirect:/";
        }
    }
    
    /**
     * Statistics page - Show overall quiz statistics
     */
    @GetMapping("/statistics")
    public String statistics(Model model) {
        try {
            model.addAttribute("stats", quizService.getQuizStatistics());
            model.addAttribute("topScores", quizService.getTopScores());
            model.addAttribute("recentAttempts", quizService.getRecentAttempts());
            model.addAttribute("categoryStats", quizService.getPerformanceByCategory());
            return "statistics";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading statistics: " + e.getMessage());
            return "statistics";
        }
    }
    
    /**
     * Student results page - Show individual student performance
     */
    @GetMapping("/student/results")
    public String studentResults() {
        return "student-search";
    }
    
    /**
     * Search student results
     */
    @PostMapping("/student/search")
    public String searchStudent(@RequestParam String studentEmail, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<QuizResult> results = quizService.getResultsByStudentEmail(studentEmail);
            
            if (results.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "No results found for email: " + studentEmail);
                return "redirect:/student/results";
            }
            
            model.addAttribute("studentEmail", studentEmail);
            model.addAttribute("results", results);
            model.addAttribute("stats", quizService.getStudentStatistics(studentEmail));
            
            return "student-results";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error searching results: " + e.getMessage());
            return "redirect:/student/results";
        }
    }
    
    /**
     * Navigation helper - Return to home
     */
    @GetMapping("/home")
    public String returnHome() {
        return "redirect:/";
    }
    
    /**
     * Handle navigation errors and session timeouts
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "An unexpected error occurred: " + e.getMessage());
        return "redirect:/";
    }
}
