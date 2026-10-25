# Repository Guidelines & Coding Standards

This project is a dedicated practice repository for Java interview coding questions. All code generation, refactoring, and problem-solving within this repository must adhere to the following rules and conventions:

---

## 1. Project Purpose
- **Java Interview Practice**: This entire repository is dedicated to practicing Java interview coding questions (e.g., Collections Framework, Streams API, Concurrency, Algorithms, and Data Structures).
- Code should be organized into topic-specific packages (e.g., `com.practice.java.collections_framework`, `com.practice.java.streams_api`) with numbered challenge folders (e.g., `_1_group_anagrams_and_count`).

---

## 2. Challenge Files (`Challenge_<Suffix>.java`) — Practice Templates
- **For Practice Only**: Challenge files are practice templates intended for the user to solve independently later. They must **never** contain the working solution or implementation code.
- **No Hints or Spoilers**: Do not add hints, pseudo-code clues, or spoiler comments that give away the solution or implementation strategy.
- **Anonymous Comments (No Topic/API Prefixes)**: Keep the comments anonymous. Do **not** prefix comments with the specific API, category, or framework name (e.g., do **not** write `"Java Streams API Challenge:"` or `"Java Collections Framework Challenge:"`, and do not mandate specific methods like `Stream.distinct()` in the challenge comment). Use neutral titles such as `Challenge: <Problem Title>` so the comment does not give away the tool, data structure, or API intended to solve the challenge.
- **Placeholder TODO Method**: The challenge file must provide only:
  - The problem description comment, examples, and requirements.
  - A sample data helper method (e.g., `getSampleNames()`).
  - A runnable `main` method that invokes the challenge method and prints the result.
  - A placeholder target method containing only a `// TODO: Implement this method` comment returning a default stub value (e.g., `Collections.emptyList()`, `Collections.emptyMap()`, or `null`).

---

## 3. Solution Files (`Solution_<Suffix>.java`) — Comprehensive Reference
- **All Solutions in Separate Methods**: All actual working implementations must reside in the corresponding `Solution_<Suffix>.java` file. If multiple approaches exist (e.g., recommended/idiomatic approach, alternative data structures, stateful filters, optimized time/space algorithms, or interview follow-up variations), each solution must be implemented in its own separate method (e.g., `solution1_*`, `solution2_*`).
- **Self-Contained Methods (Avoid Utility Methods)**: Keep solution methods self-contained and inline their logic directly. Do **not** create separate helper or utility methods (e.g., avoid creating extra generic methods like `distinctByKey(...)` or static helper classes). Inline necessary state or helper structures (such as a local `Set<String> seen = new HashSet<>()`) directly within the solution method to keep solutions clean, direct, and focused.
- **Comprehensive Documentation**: Make sure all code solutions have detailed comments including problem description, intuition, approach breakdown, and time/space complexity analysis ($O(...)$ notation).
- **Explain Complex Steps**: If any part of the code is complex, explain step-by-step what each line or block is doing and why.
- **Visual & Example Traces**: Where helpful, include comment blocks illustrating the state of data structures or stream pipelines at various stages of execution.
- **Demonstration in Main**: The `main` method must execute and display the output of each solution method against sample input data.

---

## 4. Simplicity Over Complexity
- **Simple to Understand**: Keep all code solutions clean, readable, and easy to follow.
- **Avoid Over-Engineering**: Do not introduce unnecessary abstractions, helper/utility methods, complex enterprise design patterns, or overly convoluted logic. Solutions should reflect how a candidate would clearly and effectively solve problems in an actual technical interview.

---

## 5. Testing Guidelines (JUnit 5)
- **Challenge Tests Only**: Always create a corresponding JUnit 5 test file for the `Challenge_*.java` file **only**, and **never** for `Solution_*.java` files.
- **Exhaustive Edge Case Coverage**: Tests must thoroughly cover all scenarios, including:
  - Standard/sample test cases.
  - Edge cases (null input, empty inputs, single-element collections, duplicate elements, case sensitivity, etc.).
  - Boundary conditions and corner cases.
- **Structured Test Files**: Use JUnit 5 features such as `@DisplayName` and `@Nested` test classes to structure and document test cases clearly.

---

## 6. File Naming Conventions
- **Naming Pattern**: Always name files by adding a unique and short suffix representing the problem, while preserving the required prefixes:
  - **Challenge File**: `Challenge_<Suffix>.java` (e.g., `Challenge_Anagrams.java` or `Challenge_RemoveDuplicates.java`)
  - **Solution File**: `Solution_<Suffix>.java` (e.g., `Solution_Anagrams.java` or `Solution_RemoveDuplicates.java`)
  - **Test File**: `Challenge_<Suffix>Test.java` (e.g., `Challenge_AnagramsTest.java` located in `src/test/java/...`)
- Avoid generic file names such as bare `Challenge.java` or `Solution.java`.

---

## 7. Git Tracking
- **Automatic Git Staging**: Whenever new files are created or modified, automatically add them to Git (`git add <file>`). Ensure newly created files and modifications are tracked.
