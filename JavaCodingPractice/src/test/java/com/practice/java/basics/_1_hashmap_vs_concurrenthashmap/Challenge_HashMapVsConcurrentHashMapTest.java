package com.practice.java.basics._1_hashmap_vs_concurrenthashmap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Challenge: HashMap vs ConcurrentHashMap Concurrency Tests")
class Challenge_HashMapVsConcurrentHashMapTest {

    @Nested
    @DisplayName("Sample and Standard Concurrency Scenarios")
    class SampleAndStandardConcurrencyTests {

        @Test
        @DisplayName("Should correctly record updates with ConcurrentHashMap matching expected count under sample load")
        void testSampleParametersConcurrentUpdates() {
            int threadCount = 5;
            int iterationsPerThread = 1_000;
            int expected = threadCount * iterationsPerThread;

            Map<String, Integer> result = Challenge_HashMapVsConcurrentHashMap.compareMapConcurrency(
                    threadCount, iterationsPerThread);

            assertNotNull(result, "Result map should not be null");
            assertTrue(result.containsKey("ConcurrentHashMap"), "Result must contain 'ConcurrentHashMap' entry");
            assertTrue(result.containsKey("HashMap"), "Result must contain 'HashMap' entry");

            // ConcurrentHashMap must guarantee complete thread-safe updates
            assertEquals(expected, result.get("ConcurrentHashMap"),
                    "ConcurrentHashMap must equal exactly threadCount * iterationsPerThread");

            // HashMap is not thread-safe and will have lost updates (less than or equal to expected)
            assertTrue(result.get("HashMap") <= expected,
                    "HashMap count should never exceed expected count");
        }

        @Test
        @DisplayName("Should maintain atomic consistency in ConcurrentHashMap under moderate contention")
        void testModerateContentionScenario() {
            int threadCount = 8;
            int iterationsPerThread = 2_500;
            int expected = threadCount * iterationsPerThread;

            Map<String, Integer> result = Challenge_HashMapVsConcurrentHashMap.compareMapConcurrency(
                    threadCount, iterationsPerThread);

            assertNotNull(result);
            assertEquals(expected, result.get("ConcurrentHashMap"),
                    "ConcurrentHashMap must accurately record all 20,000 updates without loss");
            assertTrue(result.get("HashMap") <= expected);
        }

        @Test
        @DisplayName("Should reliably reflect concurrent updates with larger thread count")
        void testLargerThreadContention() {
            int threadCount = 10;
            int iterationsPerThread = 1_000;
            int expected = threadCount * iterationsPerThread;

            Map<String, Integer> result = Challenge_HashMapVsConcurrentHashMap.compareMapConcurrency(
                    threadCount, iterationsPerThread);

            assertNotNull(result);
            assertEquals(expected, result.get("ConcurrentHashMap"));
            assertTrue(result.get("HashMap") <= expected);
        }
    }

    @Nested
    @DisplayName("Edge Cases and Boundary Conditions")
    class EdgeCasesAndBoundaryTests {

        @Test
        @DisplayName("Should return an empty map when thread count is 0")
        void testZeroThreadsReturnsEmptyMap() {
            Map<String, Integer> result = Challenge_HashMapVsConcurrentHashMap.compareMapConcurrency(0, 1000);

            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty when thread count is 0");
        }

        @Test
        @DisplayName("Should return an empty map when iterations per thread is 0")
        void testZeroIterationsReturnsEmptyMap() {
            Map<String, Integer> result = Challenge_HashMapVsConcurrentHashMap.compareMapConcurrency(5, 0);

            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty when iterations count is 0");
        }

        @Test
        @DisplayName("Should return an empty map when thread count is negative")
        void testNegativeThreadsReturnsEmptyMap() {
            Map<String, Integer> result = Challenge_HashMapVsConcurrentHashMap.compareMapConcurrency(-3, 1000);

            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty when thread count is negative");
        }

        @Test
        @DisplayName("Should return an empty map when iterations count is negative")
        void testNegativeIterationsReturnsEmptyMap() {
            Map<String, Integer> result = Challenge_HashMapVsConcurrentHashMap.compareMapConcurrency(5, -100);

            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty when iterations count is negative");
        }

        @Test
        @DisplayName("Should yield exact identical count for both HashMap and ConcurrentHashMap when run with a single thread")
        void testSingleThreadProducesEqualResultsWithoutRaceCondition() {
            int threadCount = 1;
            int iterationsPerThread = 500;
            int expected = threadCount * iterationsPerThread;

            Map<String, Integer> result = Challenge_HashMapVsConcurrentHashMap.compareMapConcurrency(
                    threadCount, iterationsPerThread);

            assertNotNull(result);
            assertEquals(expected, result.get("ConcurrentHashMap"),
                    "ConcurrentHashMap should equal 500 with a single thread");
            assertEquals(expected, result.get("HashMap"),
                    "Without concurrent threads, HashMap experiences no race conditions and must equal 500");
        }

        @Test
        @DisplayName("Should correctly handle single iteration per thread across multiple threads")
        void testSingleIterationPerThread() {
            int threadCount = 4;
            int iterationsPerThread = 1;
            int expected = threadCount * iterationsPerThread;

            Map<String, Integer> result = Challenge_HashMapVsConcurrentHashMap.compareMapConcurrency(
                    threadCount, iterationsPerThread);

            assertNotNull(result);
            assertEquals(expected, result.get("ConcurrentHashMap"));
            assertTrue(result.get("HashMap") <= expected);
        }
    }

    @Nested
    @DisplayName("Structure and Map Integrity Tests")
    class StructureAndMapIntegrityTests {

        @Test
        @DisplayName("Result map must contain both 'ConcurrentHashMap' and 'HashMap' keys")
        void testResultMapContainsBothMapKeys() {
            Map<String, Integer> result = Challenge_HashMapVsConcurrentHashMap.compareMapConcurrency(2, 100);

            assertNotNull(result);
            assertEquals(2, result.size(), "Result map should contain exactly 2 entries");
            assertTrue(result.containsKey("ConcurrentHashMap"));
            assertTrue(result.containsKey("HashMap"));
        }

        @Test
        @DisplayName("Sample helper methods should return positive integers")
        void testSampleHelperMethods() {
            int threads = Challenge_HashMapVsConcurrentHashMap.getSampleThreadCount();
            int iterations = Challenge_HashMapVsConcurrentHashMap.getSampleIterationsPerThread();

            assertTrue(threads > 0, "Sample thread count should be positive");
            assertTrue(iterations > 0, "Sample iterations should be positive");
        }

        @Test
        @DisplayName("All returned counter values should be non-negative")
        void testNonNegativeCounterValues() {
            Map<String, Integer> result = Challenge_HashMapVsConcurrentHashMap.compareMapConcurrency(3, 50);

            assertNotNull(result);
            assertTrue(result.get("ConcurrentHashMap") >= 0, "Counter should be non-negative");
            assertTrue(result.get("HashMap") >= 0, "Counter should be non-negative");
        }
    }
}
