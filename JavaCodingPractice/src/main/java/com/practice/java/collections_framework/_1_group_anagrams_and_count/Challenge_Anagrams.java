/*
 * ============================================================================
 * Challenge: Group Anagrams and Count
 * ============================================================================
 *
 * Problem Description:
 * Given a List of Strings, group words that are anagrams of each other and
 * return the count of words in each anagram group.
 *
 * What are Anagrams?
 * Two words are anagrams if they contain the exact same characters with the
 * same frequencies, regardless of the order of characters.
 *
 * Example 1:
 * Input:
 *   List<String> words = List.of("eat", "tea", "tan", "ate", "nat", "bat", "eat");
 *
 * Expected Output:
 *   [eat, tea, ate, eat] -> 4
 *   [tan, nat]           -> 2
 *   [bat]                -> 1
 *
 * Example 2 (Including Duplicates):
 * Input:
 *   List<String> words = List.of("abc", "abc", "bca", "xyz");
 *
 * Expected Output:
 *   [abc, abc, bca] -> 3
 *   [xyz]           -> 1
 *
 * Requirements:
 * 1. Identify words that are anagrams of each other.
 * 2. Group the anagram words together into a List<String>.
 * 3. Preserve duplicate words and count each occurrence within its group.
 * 4. Words that have no anagrams belong to their own individual group with count 1.
 * 5. Return a Map<List<String>, Long> where:
 *      - Key   : List<String> representing the grouped anagram words
 *      - Value : Long representing the count of words in that anagram group
 * 6. Handle edge cases:
 *      - Empty list or null input should return an empty map.
 *      - Single-element list should return that single word in a group with count 1.
 *
 * Note:
 * Implement your solution inside the groupAnagramsAndCount method below.
 * You can verify your implementation by running the unit tests in Challenge_AnagramsTest.java.
 * ============================================================================
 */
package com.practice.java.collections_framework._1_group_anagrams_and_count;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Challenge_Anagrams {

    public static void main(String[] args) {
        List<String> words = getSampleWords();

        System.out.println("--- Input Words ---");
        System.out.println(words);

        System.out.println("\n--- Anagram Groups and Counts ---");
        Map<List<String>, Long> result = groupAnagramsAndCount(words);
        result.forEach((group, count) -> System.out.println(group + " -> " + count));
    }

    /**
     * Groups words that are anagrams of each other and returns each group
     * mapped to the count of words in that group.
     *
     * @param words list of input words
     * @return map where key is the list of anagram words and value is the count
     */
    public static Map<List<String>, Long> groupAnagramsAndCount(List<String> words) {
        // TODO: Implement this method
        return Collections.emptyMap();
    }

    /**
     * Provides sample words containing anagrams and duplicates.
     *
     * @return sample list of words
     */
    public static List<String> getSampleWords() {
        return List.of("eat", "tea", "tan", "ate", "nat", "bat", "eat");
    }
}
