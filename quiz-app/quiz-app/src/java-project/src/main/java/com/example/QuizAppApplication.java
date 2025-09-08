package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for the Computer Science Engineering Quiz App
 * This class serves as the entry point for the application
 */
@SpringBootApplication
public class QuizAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizAppApplication.class, args);
        System.out.println("Quiz App Started Successfully!");
        System.out.println("Access the application at: http://localhost:8080");
    }
}
