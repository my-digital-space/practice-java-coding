/*
 * ============================================================================
 * Java Collections Framework Solution: Group Anagrams and Count
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
 * Intuition:
 * If we sort the characters of any word alphabetically, all words that are
 * anagrams of each other will produce the EXACT same sorted string!
 * Example:
 *   "eat" -> sorted: "aet"
 *   "tea" -> sorted: "aet"
 *   "ate" -> sorted: "aet"
 * Therefore, we can use the sorted string ("aet") as a key in a HashMap,
 * and collect all matching words into an ArrayList under that key!
 *
 * Solution Steps:
 * 1. Create a Map<String, List<String>> to hold our anagram buckets.
 * 2. Loop through each word, sort its letters, and put the word into its bucket.
 * 3. Create a final Map<List<String>, Long> to hold (group -> count).
 * 4. Loop through each bucket (group) and put (group -> group.size()).
 * ============================================================================
 */
package com.practice.java.collections_framework._1_group_anagrams_and_count;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Solution_Anagrams {

    public static void main(String[] args) {
        List<String> words = Challenge_Anagrams.getSampleWords();

        System.out.println("=================================================");
        System.out.println("Input Words:");
        System.out.println(words);
        System.out.println("=================================================");

        System.out.println("\n--- Solution 1: HashMap + Sorting Characters ---");
        Map<List<String>, Long> result1 = solution1(words);
        result1.forEach((group, count) -> System.out.println("  " + group + " -> " + count));

        System.out.println("\n--- Solution 2: HashMap + Character Frequency ---");
        Map<List<String>, Long> result2 = solution2(words);
        result2.forEach((group, count) -> System.out.println("  " + group + " -> " + count));
    }



    /**
     * Solution 1: HashMap + sorting characters (Recommended — Simple and Intuitive)
     *
     * How it works:
     * 1. Sort each word's characters to create a common key. Words with the same key
     *    belong to the same anagram group (e.g. "eat", "tea", "ate" all sort to "aet").
     * 2. Use a LinkedHashMap to group words by this key while preserving order.
     * 3. Build and return the final Map mapping each group of words to its count.
     *
     * Complexity:
     * - Time Complexity:  O(N * K log K), where N is the number of words and K is word length.
     * - Space Complexity: O(N * K) to store the grouped words.
     *
     * @param words list of input words
     * @return map where key is the list of anagram words and value is the count
     */
    public static Map<List<String>, Long> solution1(List<String> words) {
        // Step 0: Guard check against null or empty input
        if (words == null || words.isEmpty()) {
            return Collections.emptyMap();
        }

        // Key: sorted characters (e.g. "aet"); Value: list of original words [eat, tea, ate]
        Map<String, List<String>> groups = new LinkedHashMap<>();

        for (String word : words) {
            // Handle null word elements gracefully
            if (word == null) {
                word = "";
            }

            // Convert the word into a sorted character key
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);

            // Add the word to its anagram group bucket
            groups.computeIfAbsent(key, k -> new ArrayList<>())
                    .add(word);
            /**
             * groups:
             * {
             *     "aet" -> ["eat", "tea", "ate", "eat"],
             *     "ant" -> ["tan", "nat"],
             *     "abt" -> ["bat"]
             * }
             */
        }

        // Build the final result map: (group of words -> count)
        Map<List<String>, Long> result = new LinkedHashMap<>();
        for (List<String> group : groups.values()) {
            result.put(group, (long) group.size());
        }

        return result;
    }

    /**
     * Solution 2: HashMap + character frequency — more efficient for long words.
     *
     * How this is more efficient for long words?
     * 1. Sorting approach
     * For the word "listen":
     * listen → sort characters → eilnst
     * Sorting takes approximately (O(nlog n)) time.
     * As the word gets longer, sorting requires more comparisons and rearrangements.
     * 2. Character-frequency approach
     * For the same word:
     * listen → count each character
     * a:0, b:0, c:0, ... e:1, i:1, l:1, n:1, s:1, t:1, ...
     * We visit each character once, so counting takes (O(n)) time.
     * Creating the frequency key takes another (O(26)) for lowercase English letters.
     * Since 26 is a constant, the overall complexity is (O(n)).
     *
     * How it works:
     * 1. Instead of sorting characters, count how many times each character occurs.
     *    For example:
     *      - "eat" -> a:1, e:1, t:1
     *      - "tea" -> a:1, e:1, t:1
     *      - "ate" -> a:1, e:1, t:1
     *    All three words produce the exact same frequency array.
     * 2. Convert the frequency array to a String key using Arrays.toString(frequency).
     * 3. Group words with identical frequency keys together in a LinkedHashMap.
     * 4. Build and return the final Map mapping each group of words to its count.
     *
     * Complexity:
     * - Time Complexity:  O(N * K), where N is number of words and K is word length.
     * - Space Complexity: O(N * K)
     *
     * Note: Assumes words contain English letters.
     *
     * @param words list of input words
     * @return map where key is the list of anagram words and value is the count
     */
    public static Map<List<String>, Long> solution2(List<String> words) {
        // Step 0: Guard check against null or empty input
        if (words == null || words.isEmpty()) {
            return Collections.emptyMap();
        }

        // Key: character frequency string; Value: list of original words
        Map<String, List<String>> groups = new LinkedHashMap<>();

        for (String word : words) {
            // Handle null word elements gracefully
            if (word == null) {
                word = "";
            }

            // Count English letters in a 26-slot array (case-insensitive)
            int[] frequency = new int[26];
            for (char ch : word.toCharArray()) {
                if (ch >= 'a' && ch <= 'z') {
                    frequency[ch - 'a']++;
                } else if (ch >= 'A' && ch <= 'Z') {
                    frequency[ch - 'A']++;
                }
            }

            // Convert the frequency array into a map key (e.g. "[1, 0, 0, ..., 1, 1]")
            String key = Arrays.toString(frequency);

            // Group words with identical character frequencies
            groups.computeIfAbsent(key, k -> new ArrayList<>())
                    .add(word);
            /**
             * groups
             * {
             *   "[1, 0, 0, 0, 1, 0, ..., 1, 0, 0, 0, 0, 0, 0]" -> ["eat", "tea", "ate", "eat"],
             *   "[1, 0, 0, 0, 0, 0, ..., 1, 0, 1, 0, 0, 0, 0]" -> ["tan", "nat"],
             *   "[1, 1, 0, 0, 0, 0, ..., 0, 0, 1, 0, 0, 0, 0]" -> ["bat"]
             * }
             */
        }

        // Build the final result map: (group of words -> count)
        Map<List<String>, Long> result = new LinkedHashMap<>();
        for (List<String> group : groups.values()) {
            result.put(group, (long) group.size());
        }

        return result;
    }
}
