package com.practice.java.streams_api._2_remove_duplicates;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Challenge: Remove Duplicates from List of Strings Tests (Streams API)")
class Challenge_RemoveDuplicatesTest {

    @Nested
    @DisplayName("Sample Data Scenarios")
    class SampleDataTests {

        @Test
        @DisplayName("Should remove duplicates from sample names while strictly preserving encounter order")
        void testSampleDataRemoveDuplicatesPreservingOrder() {
            List<String> names = Challenge_RemoveDuplicates.getSampleNames();

            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(names);

            assertNotNull(result, "Result should not be null");
            assertEquals(4, result.size(), "Expected 4 unique names in the output");

            List<String> expected = List.of("Rahul", "Priya", "Amit", "Sneha");
            assertEquals(expected, result, "The output must match the expected order: [Rahul, Priya, Amit, Sneha]");
        }

        @Test
        @DisplayName("Should preserve the first occurrence and discard subsequent occurrences")
        void testFirstOccurrencePreserved() {
            List<String> names = List.of("First", "Second", "Third", "First", "Second");

            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(names);

            assertNotNull(result);
            assertEquals(List.of("First", "Second", "Third"), result);
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should return an empty list when input list is empty")
        void testEmptyListReturnsEmptyList() {
            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(Collections.emptyList());

            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty for an empty input list");
        }

        @Test
        @DisplayName("Should handle null input list gracefully by returning an empty list")
        void testNullInputReturnsEmptyList() {
            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(null);

            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty when input list is null");
        }

        @Test
        @DisplayName("Should return single-element list when input has only one element")
        void testSingleElementListReturnsSameSingleElement() {
            List<String> single = List.of("Java");

            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(single);

            assertNotNull(result);
            assertEquals(List.of("Java"), result);
        }

        @Test
        @DisplayName("Should return single element when all elements in input list are identical")
        void testAllIdenticalElementsReturnsSingleElement() {
            List<String> allSame = List.of("duplicate", "duplicate", "duplicate", "duplicate");

            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(allSame);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("duplicate", result.get(0));
        }

        @Test
        @DisplayName("Should return identical list when all elements are already unique")
        void testAllUniqueElementsPreservedInOrder() {
            List<String> unique = List.of("alpha", "beta", "gamma", "delta");

            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(unique);

            assertNotNull(result);
            assertEquals(unique, result);
        }

        @Test
        @DisplayName("Should handle empty strings correctly by keeping only the first empty string")
        void testListWithEmptyStrings() {
            List<String> listWithEmptyStrings = List.of("", "hello", "", "world", "");

            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(listWithEmptyStrings);

            assertNotNull(result);
            assertEquals(List.of("", "hello", "world"), result);
        }

        @Test
        @DisplayName("Should correctly handle list containing null elements without throwing NullPointerException")
        void testListWithNullElements() {
            List<String> listWithNulls = Arrays.asList("Alpha", null, "Beta", null, "Alpha");

            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(listWithNulls);

            assertNotNull(result);
            assertEquals(Arrays.asList("Alpha", null, "Beta"), result);
        }
    }

    @Nested
    @DisplayName("Advanced & Algorithmic Scenarios")
    class AdvancedScenariosTests {

        @Test
        @DisplayName("Should treat strings with different casing as distinct elements (case-sensitivity)")
        void testCaseSensitivityMaintained() {
            List<String> names = List.of("Java", "java", "JAVA", "Java");

            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(names);

            assertNotNull(result);
            assertEquals(List.of("Java", "java", "JAVA"), result);
        }

        @Test
        @DisplayName("Should preserve encounter order when duplicates appear at extreme boundaries (start and end)")
        void testBoundaryDuplicatesPreserveOrder() {
            List<String> items = List.of("Z", "A", "B", "C", "Z");

            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(items);

            assertNotNull(result);
            assertEquals(List.of("Z", "A", "B", "C"), result);
        }

        @Test
        @DisplayName("Should handle interleaved duplicates properly")
        void testInterleavedDuplicates() {
            List<String> items = List.of("one", "two", "one", "two", "three", "one", "three");

            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(items);

            assertNotNull(result);
            assertEquals(List.of("one", "two", "three"), result);
        }

        @Test
        @DisplayName("Should correctly deduplicate a larger list while maintaining encounter order")
        void testLargerListOrderPreservation() {
            List<String> input = new ArrayList<>();
            // Pattern: 0, 1, 2, ..., 9 repeated 10 times = 100 elements
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    input.add("Item_" + j);
                }
            }

            List<String> result = Challenge_RemoveDuplicates.removeDuplicates(input);

            assertNotNull(result);
            assertEquals(10, result.size());
            for (int j = 0; j < 10; j++) {
                assertEquals("Item_" + j, result.get(j));
            }
        }
    }
}
