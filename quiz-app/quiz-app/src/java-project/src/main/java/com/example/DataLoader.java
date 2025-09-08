package com.example;

import com.example.model.Question;
import com.example.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DataLoader class to populate the database with Computer Science Engineering questions
 * This class runs at startup and loads 50 sample questions if the database is empty
 */
@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Only load data if the database is empty
        if (questionRepository.count() == 0) {
            loadQuestions();
            System.out.println("Loaded " + questionRepository.count() + " questions into the database.");
        } else {
            System.out.println("Database already contains " + questionRepository.count() + " questions.");
        }
    }
    
    private void loadQuestions() {
        // Programming Questions (15 questions)
        questionRepository.save(new Question(
            "What is the time complexity of binary search?",
            "O(n)",
            "O(log n)",
            "O(n²)",
            "O(1)",
            "B",
            "Programming",
            "Easy",
            "Binary search divides the search space in half at each step, resulting in O(log n) time complexity."
        ));
        
        questionRepository.save(new Question(
            "Which data structure uses LIFO (Last In, First Out) principle?",
            "Queue",
            "Stack",
            "Array",
            "Tree",
            "B",
            "Data Structures",
            "Easy",
            "Stack follows LIFO principle where the last element added is the first one to be removed."
        ));
        
        questionRepository.save(new Question(
            "What is the space complexity of merge sort?",
            "O(1)",
            "O(log n)",
            "O(n)",
            "O(n²)",
            "C",
            "Algorithms",
            "Medium",
            "Merge sort requires O(n) additional space to store the merged subarrays."
        ));
        
        questionRepository.save(new Question(
            "In object-oriented programming, what does polymorphism mean?",
            "Having multiple constructors",
            "Ability to take multiple forms",
            "Inheritance from multiple classes",
            "Creating multiple objects",
            "B",
            "Programming",
            "Easy",
            "Polymorphism allows objects of different types to be treated as objects of a common base type."
        ));
        
        questionRepository.save(new Question(
            "Which sorting algorithm has the best average-case time complexity?",
            "Bubble Sort",
            "Selection Sort",
            "Quick Sort",
            "Insertion Sort",
            "C",
            "Algorithms",
            "Medium",
            "Quick Sort has an average-case time complexity of O(n log n), which is optimal for comparison-based sorting."
        ));
        
        questionRepository.save(new Question(
            "What is the maximum number of nodes at level k in a binary tree?",
            "2^k",
            "2^(k-1)",
            "2^(k+1)",
            "k²",
            "A",
            "Data Structures",
            "Medium",
            "At level k, a binary tree can have at most 2^k nodes (assuming level starts from 0)."
        ));
        
        questionRepository.save(new Question(
            "Which of the following is NOT a linear data structure?",
            "Array",
            "Stack",
            "Tree",
            "Queue",
            "C",
            "Data Structures",
            "Easy",
            "Tree is a hierarchical (non-linear) data structure, while arrays, stacks, and queues are linear."
        ));
        
        questionRepository.save(new Question(
            "What is the worst-case time complexity of insertion in a hash table?",
            "O(1)",
            "O(log n)",
            "O(n)",
            "O(n²)",
            "C",
            "Data Structures",
            "Hard",
            "In the worst case, all keys hash to the same bucket, leading to O(n) insertion time."
        ));
        
        questionRepository.save(new Question(
            "Which algorithm is used to find the shortest path in a weighted graph?",
            "BFS",
            "DFS",
            "Dijkstra's Algorithm",
            "Kruskal's Algorithm",
            "C",
            "Algorithms",
            "Medium",
            "Dijkstra's Algorithm finds the shortest path from a source vertex to all other vertices in a weighted graph."
        ));
        
        questionRepository.save(new Question(
            "What does the 'final' keyword mean in Java?",
            "The variable can be changed",
            "The variable cannot be reassigned",
            "The variable is static",
            "The variable is private",
            "B",
            "Programming",
            "Easy",
            "The 'final' keyword in Java means the variable cannot be reassigned once initialized."
        ));
        
        // Database Questions (10 questions)
        questionRepository.save(new Question(
            "What does ACID stand for in database systems?",
            "Atomicity, Consistency, Isolation, Durability",
            "Association, Consistency, Isolation, Dependency",
            "Atomicity, Concurrency, Isolation, Durability",
            "Association, Concurrency, Integration, Dependency",
            "A",
            "Database",
            "Medium",
            "ACID properties ensure reliable processing of database transactions."
        ));
        
        questionRepository.save(new Question(
            "Which normal form eliminates partial functional dependencies?",
            "1NF",
            "2NF",
            "3NF",
            "BCNF",
            "B",
            "Database",
            "Hard",
            "Second Normal Form (2NF) eliminates partial functional dependencies on composite primary keys."
        ));
        
        questionRepository.save(new Question(
            "What is a primary key in a database?",
            "A key that can have null values",
            "A unique identifier for each record",
            "A key that can have duplicate values",
            "A key used for indexing only",
            "B",
            "Database",
            "Easy",
            "A primary key uniquely identifies each record in a database table and cannot have null values."
        ));
        
        questionRepository.save(new Question(
            "Which SQL command is used to remove a table from database?",
            "DELETE",
            "REMOVE",
            "DROP",
            "CLEAR",
            "C",
            "Database",
            "Easy",
            "DROP TABLE command is used to remove an entire table structure from the database."
        ));
        
        questionRepository.save(new Question(
            "What is the purpose of indexing in databases?",
            "To slow down queries",
            "To speed up data retrieval",
            "To increase storage space",
            "To create relationships",
            "B",
            "Database",
            "Medium",
            "Database indexing creates data structures that improve the speed of data retrieval operations."
        ));
        
        // Operating Systems Questions (10 questions)
        questionRepository.save(new Question(
            "What is a deadlock in operating systems?",
            "A fast execution state",
            "A state where processes wait for each other indefinitely",
            "A memory allocation error",
            "A CPU scheduling algorithm",
            "B",
            "Operating Systems",
            "Medium",
            "Deadlock occurs when processes are blocked indefinitely, each waiting for resources held by others."
        ));
        
        questionRepository.save(new Question(
            "Which scheduling algorithm can cause starvation?",
            "FCFS",
            "Round Robin",
            "Priority Scheduling",
            "SJF",
            "C",
            "Operating Systems",
            "Hard",
            "Priority scheduling can cause starvation of low-priority processes if high-priority processes keep arriving."
        ));
        
        questionRepository.save(new Question(
            "What is virtual memory?",
            "Physical RAM only",
            "A memory management technique using disk space",
            "Cache memory",
            "ROM memory",
            "B",
            "Operating Systems",
            "Medium",
            "Virtual memory extends physical memory by using disk space to store pages not currently in RAM."
        ));
        
        questionRepository.save(new Question(
            "What is the purpose of system calls?",
            "To call other programs",
            "To interface between user programs and OS kernel",
            "To manage memory",
            "To schedule processes",
            "B",
            "Operating Systems",
            "Easy",
            "System calls provide an interface for user programs to request services from the operating system kernel."
        ));
        
        questionRepository.save(new Question(
            "Which page replacement algorithm is optimal?",
            "FIFO",
            "LRU",
            "Optimal Page Replacement",
            "Random",
            "C",
            "Operating Systems",
            "Hard",
            "Optimal Page Replacement algorithm replaces the page that will be referenced furthest in the future."
        ));
        
        // Computer Networks Questions (8 questions)
        questionRepository.save(new Question(
            "What does TCP stand for?",
            "Transfer Control Protocol",
            "Transmission Control Protocol",
            "Transport Control Protocol",
            "Technical Control Protocol",
            "B",
            "Computer Networks",
            "Easy",
            "TCP (Transmission Control Protocol) provides reliable, ordered delivery of data between applications."
        ));
        
        questionRepository.save(new Question(
            "Which layer of OSI model handles routing?",
            "Data Link Layer",
            "Network Layer",
            "Transport Layer",
            "Session Layer",
            "B",
            "Computer Networks",
            "Medium",
            "The Network Layer (Layer 3) is responsible for routing packets between different networks."
        ));
        
        questionRepository.save(new Question(
            "What is the purpose of ARP protocol?",
            "To resolve domain names to IP addresses",
            "To resolve IP addresses to MAC addresses",
            "To route packets",
            "To establish connections",
            "B",
            "Computer Networks",
            "Medium",
            "ARP (Address Resolution Protocol) maps IP addresses to physical MAC addresses in local networks."
        ));
        
        questionRepository.save(new Question(
            "Which protocol is used for secure web communication?",
            "HTTP",
            "FTP",
            "HTTPS",
            "SMTP",
            "C",
            "Computer Networks",
            "Easy",
            "HTTPS (HTTP Secure) uses SSL/TLS encryption to provide secure communication over networks."
        ));
        
        questionRepository.save(new Question(
            "What is the maximum size of an IPv4 address?",
            "32 bits",
            "64 bits",
            "128 bits",
            "16 bits",
            "A",
            "Computer Networks",
            "Easy",
            "IPv4 addresses are 32-bit numbers, typically represented in dotted decimal notation."
        ));
        
        // Software Engineering Questions (7 questions)
        questionRepository.save(new Question(
            "What does UML stand for?",
            "Unified Modeling Language",
            "Universal Modeling Language",
            "Unified Management Language",
            "Universal Management Language",
            "A",
            "Software Engineering",
            "Easy",
            "UML (Unified Modeling Language) is a standardized modeling language for software design."
        ));
        
        questionRepository.save(new Question(
            "Which software development model is iterative?",
            "Waterfall Model",
            "Spiral Model",
            "V-Model",
            "Big Bang Model",
            "B",
            "Software Engineering",
            "Medium",
            "The Spiral Model combines iterative development with systematic aspects of the waterfall model."
        ));
        
        questionRepository.save(new Question(
            "What is the main purpose of version control systems?",
            "To compile code",
            "To track changes in files",
            "To debug programs",
            "To test software",
            "B",
            "Software Engineering",
            "Easy",
            "Version control systems track changes to files and coordinate work among multiple developers."
        ));
        
        questionRepository.save(new Question(
            "What does API stand for?",
            "Application Programming Interface",
            "Advanced Programming Interface",
            "Application Process Interface",
            "Advanced Process Interface",
            "A",
            "Software Engineering",
            "Easy",
            "API (Application Programming Interface) defines methods of communication between software components."
        ));
        
        questionRepository.save(new Question(
            "Which testing approach tests individual components?",
            "Integration Testing",
            "System Testing",
            "Unit Testing",
            "Acceptance Testing",
            "C",
            "Software Engineering",
            "Medium",
            "Unit testing focuses on testing individual components or modules in isolation."
        ));
        
        questionRepository.save(new Question(
            "What is refactoring in software development?",
            "Adding new features",
            "Fixing bugs",
            "Restructuring code without changing functionality",
            "Writing documentation",
            "C",
            "Software Engineering",
            "Medium",
            "Refactoring involves restructuring existing code without changing its external behavior to improve readability and maintainability."
        ));
        
        questionRepository.save(new Question(
            "Which design pattern ensures a class has only one instance?",
            "Factory Pattern",
            "Observer Pattern",
            "Singleton Pattern",
            "Strategy Pattern",
            "C",
            "Software Engineering",
            "Hard",
            "The Singleton pattern ensures that a class has only one instance and provides global access to it."
        ));
        
        // Additional Programming and Theory Questions
        questionRepository.save(new Question(
            "What is the time complexity of accessing an element in an array?",
            "O(1)",
            "O(log n)",
            "O(n)",
            "O(n²)",
            "A",
            "Data Structures",
            "Easy",
            "Array elements can be accessed directly using their index, providing constant time complexity."
        ));
        
        questionRepository.save(new Question(
            "Which principle states that software entities should be open for extension but closed for modification?",
            "Single Responsibility Principle",
            "Open/Closed Principle",
            "Liskov Substitution Principle",
            "Interface Segregation Principle",
            "B",
            "Software Engineering",
            "Hard",
            "The Open/Closed Principle is one of the SOLID principles of object-oriented design."
        ));
        
        questionRepository.save(new Question(
            "What is the purpose of garbage collection in programming languages?",
            "To delete unused variables",
            "To automatically manage memory deallocation",
            "To optimize code performance",
            "To handle exceptions",
            "B",
            "Programming",
            "Medium",
            "Garbage collection automatically reclaims memory occupied by objects that are no longer reachable or referenced."
        ));
        
        questionRepository.save(new Question(
            "Which data structure is best suited for implementing recursion?",
            "Queue",
            "Stack",
            "Array",
            "Heap",
            "B",
            "Data Structures",
            "Medium",
            "The call stack is used to manage recursive function calls, making stack the ideal data structure for recursion."
        ));
        
        questionRepository.save(new Question(
            "What is Big O notation used for?",
            "Measuring actual runtime",
            "Describing algorithmic complexity",
            "Calculating memory usage",
            "Determining code quality",
            "B",
            "Algorithms",
            "Easy",
            "Big O notation describes the upper bound of algorithmic complexity as input size approaches infinity."
        ));
        
        questionRepository.save(new Question(
            "In a binary search tree, what is the average time complexity for search operation?",
            "O(1)",
            "O(log n)",
            "O(n)",
            "O(n log n)",
            "B",
            "Data Structures",
            "Medium",
            "In a balanced BST, search operation takes O(log n) time on average due to the tree's height."
        ));
        
        questionRepository.save(new Question(
            "Which HTTP status code indicates a successful request?",
            "404",
            "500",
            "200",
            "301",
            "C",
            "Computer Networks",
            "Easy",
            "HTTP status code 200 indicates that the request was successful and the server has returned the requested resource."
        ));
        
        questionRepository.save(new Question(
            "What is the main difference between abstraction and encapsulation?",
            "They are the same concept",
            "Abstraction hides complexity, encapsulation bundles data with methods",
            "Abstraction is for data, encapsulation is for methods",
            "Encapsulation hides complexity, abstraction bundles data",
            "B",
            "Programming",
            "Hard",
            "Abstraction focuses on hiding complexity and showing only essential features, while encapsulation bundles data with methods that operate on that data."
        ));
        
        questionRepository.save(new Question(
            "Which algorithm is used to detect cycles in a linked list?",
            "Binary Search",
            "Floyd's Cycle Detection (Tortoise and Hare)",
            "Depth First Search",
            "Breadth First Search",
            "B",
            "Algorithms",
            "Hard",
            "Floyd's Cycle Detection algorithm uses two pointers moving at different speeds to detect cycles in O(n) time and O(1) space."
        ));
        
        questionRepository.save(new Question(
            "What is the purpose of a foreign key in a relational database?",
            "To uniquely identify records",
            "To establish relationships between tables",
            "To index data",
            "To encrypt data",
            "B",
            "Database",
            "Medium",
            "A foreign key establishes and maintains referential integrity between tables by linking records in different tables."
        ));
        
        questionRepository.save(new Question(
            "Which sorting algorithm is stable and has O(n log n) time complexity?",
            "Quick Sort",
            "Heap Sort",
            "Merge Sort",
            "Selection Sort",
            "C",
            "Algorithms",
            "Medium",
            "Merge Sort is stable (maintains relative order of equal elements) and always has O(n log n) time complexity."
        ));
        
        questionRepository.save(new Question(
            "What is the difference between process and thread?",
            "They are the same thing",
            "Process is lighter than thread",
            "Process has its own memory space, threads share memory",
            "Threads cannot communicate with each other",
            "C",
            "Operating Systems",
            "Medium",
            "Processes have separate memory spaces while threads within a process share the same memory space and resources."
        ));
        
        questionRepository.save(new Question(
            "Which design pattern is used to create objects without specifying their exact classes?",
            "Singleton Pattern",
            "Factory Pattern",
            "Observer Pattern",
            "Strategy Pattern",
            "B",
            "Software Engineering",
            "Medium",
            "The Factory Pattern creates objects without exposing the instantiation logic and refers to the created object through a common interface."
        ));
        
        System.out.println("Successfully loaded all 50 Computer Science Engineering questions!");
    }
}
