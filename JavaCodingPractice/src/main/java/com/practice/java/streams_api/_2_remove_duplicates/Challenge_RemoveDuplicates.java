/*
 * ============================================================================
 * Challenge: Remove Duplicates from a List of Strings
 * ============================================================================
 *
 * Problem Description:
 * Given a List of Strings, write a Java program to remove duplicate strings
 * while strictly preserving the original encounter order of their first occurrences.
 *
 * Example:
 * Input:
 *   List<String> names = List.of("Rahul", "Priya", "Amit", "Rahul", "Priya", "Sneha");
 *
 * Expected Output:
 *   ["Rahul", "Priya", "Amit", "Sneha"]
 *
 * Requirements:
 * 1. Remove duplicate strings from the list.
 * 2. Preserve the encounter order of the first occurrences.
 * 3. Handle edge cases:
 *    - Null or empty input list should return an empty list.
 *    - Single-element list should return a list with that element.
 *    - Lists with all unique elements should remain unchanged.
 *    - Lists with all identical elements should return a single element.
 *
 * Note:
 * Implement your solution inside the removeDuplicates method below.
 * You can verify your implementation by running the unit tests in Challenge_RemoveDuplicatesTest.java.
 * ============================================================================
 */
package com.practice.java.streams_api._2_remove_duplicates;

import java.util.Collections;
import java.util.List;

public class Challenge_RemoveDuplicates {

    public static void main(String[] args) {
        List<String> names = getSampleNames();

        System.out.println("--- Input List ---");
        System.out.println(names);

        System.out.println("\n--- List Without Duplicates (Order Preserved) ---");
        List<String> result = removeDuplicates(names);
        System.out.println(result);
    }

    /**
     * Removes duplicate elements from the input list of strings while preserving
     * the original encounter order.
     *
     * @param list the list of strings (may contain duplicates)
     * @return a new list containing only unique strings in their first encounter order
     */
    public static List<String> removeDuplicates(List<String> list) {
        // TODO: Implement this method
        return Collections.emptyList();
    }

    /**
     * Provides sample input list containing duplicate names.
     *
     * @return sample list of names
     */
    public static List<String> getSampleNames() {
        return List.of("Rahul", "Priya", "Amit", "Rahul", "Priya", "Sneha");
    }
}
