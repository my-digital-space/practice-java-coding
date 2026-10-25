/*
 * ============================================================================
 * Java Streams API Solution: Remove Duplicates from a List of Strings
 * ============================================================================
 *
 * Problem Description:
 * Given a List of Strings, write a program using the Java Streams API to remove
 * duplicate strings while preserving the original encounter order of their first
 * occurrences.
 *
 * Example:
 * Input:
 *   List<String> names = List.of("Rahul", "Priya", "Amit", "Rahul", "Priya", "Sneha");
 *
 * Expected Output:
 *   ["Rahul", "Priya", "Amit", "Sneha"]
 *
 * Intuition:
 * In Java, elements in a List have a defined encounter order (index 0 to N-1).
 * When removing duplicates, standard Set implementations like HashSet do NOT preserve
 * order. To preserve encounter order, we need a mechanism that records elements
 * seen so far and only emits the first occurrence of each distinct element.
 *
 * Java Stream API's intermediate operation `.distinct()` is specifically specified
 * to be stable on ordered streams:
 * - It evaluates elements according to `Object.equals(Object)`.
 * - For ordered streams, the selection of distinct elements is stable (the element
 *   appearing first in encounter order is preserved).
 *
 * Visual Trace of `.distinct()`:
 * ----------------------------------------------------------------------------
 * Input Stream : ["Rahul", "Priya", "Amit", "Rahul", "Priya", "Sneha"]
 *
 * Element 1: "Rahul" -> Not seen yet -> Pass to downstream -> Seen: {"Rahul"}
 * Element 2: "Priya" -> Not seen yet -> Pass to downstream -> Seen: {"Rahul", "Priya"}
 * Element 3: "Amit"  -> Not seen yet -> Pass to downstream -> Seen: {"Rahul", "Priya", "Amit"}
 * Element 4: "Rahul" -> Already seen -> DROP (duplicate)
 * Element 5: "Priya" -> Already seen -> DROP (duplicate)
 * Element 6: "Sneha" -> Not seen yet -> Pass to downstream -> Seen: {"Rahul", "Priya", "Amit", "Sneha"}
 *
 * Output List: ["Rahul", "Priya", "Amit", "Sneha"]
 * ----------------------------------------------------------------------------
 *
 * Complexity Analysis:
 * - Time Complexity:  O(N), where N is the number of elements in the list.
 *                     Each element is checked and added to an internal hash structure in O(1) average time.
 * - Space Complexity: O(N) to store distinct elements in the internal buffer/set and result list.
 * ============================================================================
 */
package com.practice.java.streams_api._2_remove_duplicates;

import java.util.*;
import java.util.stream.Collectors;

public class Solution_RemoveDuplicates {

    public static void main(String[] args) {
        List<String> names = Challenge_RemoveDuplicates.getSampleNames();

        System.out.println("=================================================");
        System.out.println("Original Input List (with duplicates):");
        System.out.println(names);
        System.out.println("=================================================");

        // Solution 1: Standard Stream API using .distinct()
        System.out.println("\n--- Solution 1: Stream.distinct() (Recommended) ---");
        List<String> result1 = solution1_Distinct(names);
        System.out.println("Result: " + result1);

        // Solution 2: Stream collecting to LinkedHashSet via collectingAndThen
        System.out.println("\n--- Solution 2: Stream with Collectors.collectingAndThen(LinkedHashSet) ---");
        List<String> result2 = solution2_LinkedHashSetCollector(names);
        System.out.println("Result: " + result2);

        // Solution 3: Stream with stateful filter Predicate
        System.out.println("\n--- Solution 3: Stream with Stateful Filter Predicate ---");
        List<String> result3 = solution3_StatefulFilter(names);
        System.out.println("Result: " + result3);

        // Solution 4: Case-Insensitive Duplicate Removal (Interview Bonus)
        List<String> mixedCaseNames = List.of("Rahul", "priya", "Amit", "rahul", "PRIYA", "Sneha");
        System.out.println("\n--- Solution 4: Case-Insensitive Duplicate Removal (Bonus) ---");
        System.out.println("Mixed-case Input: " + mixedCaseNames);
        List<String> result4 = solution4_CaseInsensitive(mixedCaseNames);
        System.out.println("Result (First occurrence casing preserved): " + result4);
    }

    /**
     * Solution 1: Standard Stream API using .distinct() (Recommended — Clean and Idiomatic)
     *
     * How it works:
     * 1. Check for null or empty input.
     * 2. Call `.stream()` on the list to create an ordered sequential stream.
     * 3. Call `.distinct()`:
     *    - On an ordered stream, `.distinct()` preserves the encounter order of the first occurrence.
     *    - Uses `Objects.equals()` and `hashCode()` internally.
     * 4. Terminate with `.toList()` (available in Java 16+) to return an unmodifiable List.
     *
     * Complexity:
     * - Time Complexity:  O(N) where N is the number of elements.
     * - Space Complexity: O(N) auxiliary space for internal tracking set.
     *
     * @param list input list of strings
     * @return list containing unique strings in first-seen order
     */
    public static List<String> solution1_Distinct(List<String> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        return list.stream()
                .distinct()
                .toList();
    }

    /**
     * Solution 2: Stream collecting to LinkedHashSet via Collectors.collectingAndThen
     *
     * How it works:
     * 1. Streams elements into a `LinkedHashSet` via `Collectors.toCollection(LinkedHashSet::new)`.
     * 2. `LinkedHashSet` preserves insertion order while discarding duplicate entries.
     * 3. Uses `Collectors.collectingAndThen` to immediately transform the set into an `ArrayList`
     *    in a single stream pipeline without creating an unnecessary second stream.
     *
     * Complexity:
     * - Time Complexity:  O(N)
     * - Space Complexity: O(N)
     *
     * @param list input list of strings
     * @return list containing unique strings in first-seen order
     */
    public static List<String> solution2_LinkedHashSetCollector(List<String> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        return list.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(LinkedHashSet::new),
                        ArrayList::new
                ));
    }

    /**
     * Solution 3: Stream with stateful filter predicate
     *
     * How it works:
     * 1. Maintain a `Set<String>` to remember elements encountered so far.
     * 2. In `.filter(seen::add)`:
     *    - `Set.add(e)` returns `true` if the element was newly added (first time seen).
     *    - `Set.add(e)` returns `false` if already in the set, thus filtering out duplicates.
     *
     * Complexity:
     * - Time Complexity:  O(N)
     * - Space Complexity: O(N)
     *
     * @param list input list of strings
     * @return list containing unique strings in first-seen order
     */
    public static List<String> solution3_StatefulFilter(List<String> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> seen = new HashSet<>();
        return list.stream()
                .filter(seen::add)
                .toList();
    }

    /**
     * Solution 4: Case-Insensitive Duplicate Removal (Interview Follow-Up Scenario)
     *
     * Problem variation:
     * "What if 'Rahul' and 'rahul' are duplicates, but we want to preserve the first occurrence
     * along with its original casing?"
     *
     * How it works:
     * 1. Uses a local `Set<String> seen = new HashSet<>()` to track lowercase representations.
     * 2. In `.filter(s -> seen.add(s == null ? null : s.toLowerCase()))`:
     *    - If the lowercase version is already in 'seen', Set.add returns false (filtering out duplicate).
     *    - If not seen before, Set.add returns true, preserving the original string and its casing.
     * 3. Self-contained directly inside the method without separate utility helper methods.
     *
     * Complexity:
     * - Time Complexity:  O(N * K), where K is the average string length.
     * - Space Complexity: O(N * K)
     *
     * @param list input list of strings
     * @return list containing unique strings ignoring case, preserving first occurrence casing
     */
    public static List<String> solution4_CaseInsensitive(List<String> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> seen = new HashSet<>();
        return list.stream()
                .filter(s -> seen.add(s == null ? null : s.toLowerCase()))
                .toList();
    }
}
