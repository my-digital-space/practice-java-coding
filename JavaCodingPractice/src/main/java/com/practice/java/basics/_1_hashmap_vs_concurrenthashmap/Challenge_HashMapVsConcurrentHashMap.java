/*
 * ============================================================================
 * Challenge: HashMap vs ConcurrentHashMap Under Multi-Threaded Updates
 * ============================================================================
 *
 * Problem Description:
 * Demonstrate and compare the behavior of HashMap and ConcurrentHashMap when
 * multiple threads update a shared map concurrently.
 *
 * Requirements:
 * 1. Create a HashMap and a ConcurrentHashMap.
 * 2. Start multiple threads that increment the same shared key's counter (e.g., "counter").
 * 3. Each thread should perform a fixed number of updates.
 * 4. Coordinate all threads so that the main thread waits for all thread updates to finish.
 * 5. Compare the final counter values of both maps with the expected value.
 * 6. Return a Map<String, Integer> containing:
 *      - Key "ConcurrentHashMap" -> final counter value in ConcurrentHashMap
 *      - Key "HashMap"           -> final counter value in HashMap
 * 7. Handle edge cases:
 *      - If threadCount <= 0 or iterationsPerThread <= 0, return an empty map.
 *
 * Example:
 *   If 5 threads each increment a counter 10,000 times:
 *   - Expected final value: 50,000
 *   - ConcurrentHashMap final value: 50,000 (thread-safe, atomic)
 *   - HashMap final value: < 50,000 (lost updates due to race conditions)
 *
 * Note:
 * Implement your solution inside the compareMapConcurrency method below.
 * You can verify your implementation by running the unit tests in Challenge_HashMapVsConcurrentHashMapTest.java.
 * ============================================================================
 */
package com.practice.java.basics._1_hashmap_vs_concurrenthashmap;

import java.util.Collections;
import java.util.Map;

public class Challenge_HashMapVsConcurrentHashMap {

    public static void main(String[] args) {
        int threadCount = getSampleThreadCount();
        int iterationsPerThread = getSampleIterationsPerThread();
        int expectedCount = threadCount * iterationsPerThread;

        System.out.println("=================================================");
        System.out.println("Concurrent Map Updates Comparison");
        System.out.println("Threads: " + threadCount + ", Iterations per thread: " + iterationsPerThread);
        System.out.println("Expected Final Count: " + expectedCount);
        System.out.println("=================================================");

        Map<String, Integer> results = compareMapConcurrency(threadCount, iterationsPerThread);

        System.out.println("\n--- Simulation Results ---");
        results.forEach((mapType, count) -> {
            System.out.println(mapType + " final count: " + count +
                    " (Matches expected: " + (count == expectedCount) + ")");
        });
    }

    /**
     * Compares the behavior of HashMap and ConcurrentHashMap when multiple threads
     * increment a shared counter concurrently.
     *
     * @param threadCount         the number of concurrent threads to spawn
     * @param iterationsPerThread the number of counter updates each thread performs
     * @return a map containing the final counter value for "ConcurrentHashMap" and "HashMap"
     */
    public static Map<String, Integer> compareMapConcurrency(int threadCount, int iterationsPerThread) {
        // TODO: Implement this method
        return Collections.emptyMap();
    }

    /**
     * Provides sample number of concurrent threads.
     *
     * @return sample thread count (e.g., 5)
     */
    public static int getSampleThreadCount() {
        return 5;
    }

    /**
     * Provides sample number of iterations per thread.
     *
     * @return sample iterations per thread (e.g., 10,000)
     */
    public static int getSampleIterationsPerThread() {
        return 10_000;
    }
}
