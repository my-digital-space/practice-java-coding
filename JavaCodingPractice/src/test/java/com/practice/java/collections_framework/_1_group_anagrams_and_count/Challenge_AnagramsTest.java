package com.practice.java.collections_framework._1_group_anagrams_and_count;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Challenge: Group Anagrams and Count Tests")
class Challenge_AnagramsTest {

    @Nested
    @DisplayName("Sample Data Scenarios")
    class SampleDataTests {

        @Test
        @DisplayName("Should correctly group anagrams and return counts for sample words")
        void testSampleDataAnagramGroupingAndCounts() {
            List<String> words = Challenge_Anagrams.getSampleWords();

            Map<List<String>, Long> result = Challenge_Anagrams.groupAnagramsAndCount(words);

            assertNotNull(result, "Result map should not be null");
            assertEquals(3, result.size(), "Expected 3 distinct anagram groups");

            // Verify "eat" group
            Map.Entry<List<String>, Long> eatGroup = findGroupContaining(result, "eat");
            assertNotNull(eatGroup, "Group containing 'eat' should be present");
            assertEquals(4L, eatGroup.getValue(), "Expected count 4 for group [eat, tea, ate, eat]");
            assertEquals(4, eatGroup.getKey().size());
            assertTrue(eatGroup.getKey().containsAll(List.of("eat", "tea", "ate")));

            // Verify "tan" group
            Map.Entry<List<String>, Long> tanGroup = findGroupContaining(result, "tan");
            assertNotNull(tanGroup, "Group containing 'tan' should be present");
            assertEquals(2L, tanGroup.getValue(), "Expected count 2 for group [tan, nat]");
            assertEquals(2, tanGroup.getKey().size());
            assertTrue(tanGroup.getKey().containsAll(List.of("tan", "nat")));

            // Verify "bat" group
            Map.Entry<List<String>, Long> batGroup = findGroupContaining(result, "bat");
            assertNotNull(batGroup, "Group containing 'bat' should be present");
            assertEquals(1L, batGroup.getValue(), "Expected count 1 for group [bat]");
            assertEquals(List.of("bat"), batGroup.getKey());

            // Total count should match total input size
            long totalCount = result.values().stream().mapToLong(Long::longValue).sum();
            assertEquals(words.size(), totalCount, "Sum of all group counts must equal total input size");
        }

        @Test
        @DisplayName("Should preserve duplicate words and count them correctly (Example 2)")
        void testSampleDataWithDuplicates() {
            List<String> words = List.of("abc", "abc", "bca", "xyz");

            Map<List<String>, Long> result = Challenge_Anagrams.groupAnagramsAndCount(words);

            assertNotNull(result, "Result map should not be null");
            assertEquals(2, result.size(), "Expected 2 groups: [abc, abc, bca] and [xyz]");

            Map.Entry<List<String>, Long> abcGroup = findGroupContaining(result, "abc");
            assertNotNull(abcGroup, "Group containing 'abc' should be present");
            assertEquals(3L, abcGroup.getValue());
            assertEquals(3, abcGroup.getKey().size());

            Map.Entry<List<String>, Long> xyzGroup = findGroupContaining(result, "xyz");
            assertNotNull(xyzGroup, "Group containing 'xyz' should be present");
            assertEquals(1L, xyzGroup.getValue());
            assertEquals(List.of("xyz"), xyzGroup.getKey());
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should return an empty map when given an empty list")
        void testEmptyListReturnsEmptyMap() {
            Map<List<String>, Long> result = Challenge_Anagrams.groupAnagramsAndCount(Collections.emptyList());

            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty for an empty input list");
        }

        @Test
        @DisplayName("Should handle null input gracefully by returning an empty map")
        void testNullInputReturnsEmptyMap() {
            Map<List<String>, Long> result = Challenge_Anagrams.groupAnagramsAndCount(null);

            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty when input is null");
        }

        @Test
        @DisplayName("Should return single group with count 1 when list has only one word")
        void testSingleWordReturnsSingleGroupWithCountOne() {
            List<String> words = List.of("hello");

            Map<List<String>, Long> result = Challenge_Anagrams.groupAnagramsAndCount(words);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(1L, result.get(List.of("hello")));
        }

        @Test
        @DisplayName("Should correctly group identical duplicate words into one group")
        void testAllIdenticalWordsGroupedTogether() {
            List<String> words = List.of("duplicate", "duplicate", "duplicate");

            Map<List<String>, Long> result = Challenge_Anagrams.groupAnagramsAndCount(words);

            assertNotNull(result);
            assertEquals(1, result.size());
            Map.Entry<List<String>, Long> entry = result.entrySet().iterator().next();
            assertEquals(3L, entry.getValue());
            assertEquals(List.of("duplicate", "duplicate", "duplicate"), entry.getKey());
        }

        @Test
        @DisplayName("Should group empty strings together as anagrams")
        void testEmptyStringsGroupedTogether() {
            List<String> words = List.of("", "", "a");

            Map<List<String>, Long> result = Challenge_Anagrams.groupAnagramsAndCount(words);

            assertNotNull(result);
            assertEquals(2, result.size());

            Map.Entry<List<String>, Long> emptyGroup = findGroupContaining(result, "");
            assertNotNull(emptyGroup);
            assertEquals(2L, emptyGroup.getValue());
            assertEquals(List.of("", ""), emptyGroup.getKey());

            Map.Entry<List<String>, Long> aGroup = findGroupContaining(result, "a");
            assertNotNull(aGroup);
            assertEquals(1L, aGroup.getValue());
        }
    }

    @Nested
    @DisplayName("Advanced & Algorithmic Scenarios")
    class AdvancedScenariosTests {

        @Test
        @DisplayName("Should create separate groups with count 1 when no words are anagrams")
        void testNoAnagramsAllDistinctWords() {
            List<String> words = List.of("cat", "dog", "bird", "fish");

            Map<List<String>, Long> result = Challenge_Anagrams.groupAnagramsAndCount(words);

            assertNotNull(result);
            assertEquals(4, result.size(), "Each distinct word should form its own group");
            result.values().forEach(count -> assertEquals(1L, count));
        }

        @Test
        @DisplayName("Should group all words into a single group when all are anagrams")
        void testAllWordsAreAnagrams() {
            List<String> words = List.of("listen", "silent", "enlist", "tinsel");

            Map<List<String>, Long> result = Challenge_Anagrams.groupAnagramsAndCount(words);

            assertNotNull(result);
            assertEquals(1, result.size(), "All words are permutations and should form 1 group");
            Map.Entry<List<String>, Long> entry = result.entrySet().iterator().next();
            assertEquals(4L, entry.getValue());
            assertTrue(entry.getKey().containsAll(words));
        }

        @Test
        @DisplayName("Should separate words that have the same characters but different lengths/frequencies")
        void testSameCharactersDifferentFrequenciesNotAnagrams() {
            List<String> words = List.of("a", "aa", "aaa", "aaaa");

            Map<List<String>, Long> result = Challenge_Anagrams.groupAnagramsAndCount(words);

            assertNotNull(result);
            assertEquals(4, result.size(), "Words with different character frequencies must not be grouped together");
            result.values().forEach(count -> assertEquals(1L, count));
        }

        @Test
        @DisplayName("Should correctly separate anagrams from near-anagrams with extra characters")
        void testAnagramsWithSubstringsAndNearMatches() {
            List<String> words = List.of("loop", "pool", "polo", "spool", "pools");

            Map<List<String>, Long> result = Challenge_Anagrams.groupAnagramsAndCount(words);

            assertNotNull(result);
            assertEquals(2, result.size());

            Map.Entry<List<String>, Long> loopGroup = findGroupContaining(result, "loop");
            assertNotNull(loopGroup);
            assertEquals(3L, loopGroup.getValue(), "[loop, pool, polo] should have count 3");

            Map.Entry<List<String>, Long> spoolGroup = findGroupContaining(result, "spool");
            assertNotNull(spoolGroup);
            assertEquals(2L, spoolGroup.getValue(), "[spool, pools] should have count 2");
        }
    }

    private static Map.Entry<List<String>, Long> findGroupContaining(Map<List<String>, Long> map, String word) {
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().contains(word))
                .findFirst()
                .orElse(null);
    }
}
