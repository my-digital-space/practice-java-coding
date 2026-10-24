# Repository Guidelines & Coding Standards

This project is a dedicated practice repository for Java interview coding questions. All code generation, refactoring, and problem-solving within this repository must adhere to the following rules and conventions:

---

## 1. Project Purpose
- **Java Interview Practice**: This entire repository is dedicated to practicing Java interview coding questions (e.g., Collections Framework, Streams API, Concurrency, Algorithms, and Data Structures).
- Code should be organized into topic-specific packages (e.g., `com.practice.java.collections_framework`, `com.practice.java.streams_api`) with numbered challenge folders (e.g., `_1_group_anagrams_and_count`).

---

## 2. Detailed Code Comments & Step-by-Step Explanations
- **Comprehensive Documentation**: Make sure all code solutions have detailed comments. Include problem descriptions, intuition, approach, and time/space complexity analysis.
- **Explain Complex Steps**: If any part of the code is complex, explain step-by-step what each line or block is doing and why.
- **Visual & Example Traces**: Where helpful, include comment blocks illustrating the state of data structures at various stages of execution (e.g., map contents, frequency arrays).

---

## 3. Simplicity Over Complexity
- **Simple to Understand**: Keep all code solutions clean, readable, and easy to follow.
- **Avoid Over-Engineering**: Do not introduce unnecessary abstractions, complex enterprise design patterns, or overly convoluted logic. Solutions should reflect how a candidate would clearly and effectively solve problems in an actual technical interview.

---

## 4. Testing Guidelines (JUnit 5)
- **Challenge Tests Only**: Always create a corresponding JUnit 5 test file for the `Challenge_*.java` file **only**, and **never** for `Solution_*.java` files.
- **Exhaustive Edge Case Coverage**: Tests must thoroughly cover all scenarios, including:
  - Standard/sample test cases.
  - Edge cases (null input, empty inputs, single-element collections, duplicate elements, case sensitivity, etc.).
  - Boundary conditions and corner cases.
- **Structured Test Files**: Use JUnit 5 features such as `@DisplayName` and `@Nested` test classes to structure and document test cases clearly.

---

## 5. File Naming Conventions
- **Naming Pattern**: Always name files by adding a unique and short suffix representing the problem, while preserving the required prefixes:
  - **Challenge File**: `Challenge_<Suffix>.java` (e.g., `Challenge_Anagrams.java` or `Challenge_GroupAnagrams.java`)
  - **Solution File**: `Solution_<Suffix>.java` (e.g., `Solution_Anagrams.java` or `Solution_GroupAnagrams.java`)
  - **Test File**: `Challenge_<Suffix>Test.java` (e.g., `Challenge_AnagramsTest.java` located in `src/test/java/...`)
- Avoid generic file names such as bare `Challenge.java` or `Solution.java`.

---

## 6. Git Tracking
- **Automatic Git Staging**: Whenever new files are created, automatically add them to Git (`git add <file>`). Ensure newly created files and modifications are tracked.
