/*
 * ============================================================================
 * Java Basics / Concurrency Solution: HashMap vs ConcurrentHashMap
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
 * 7. Explain why ConcurrentHashMap is designed for concurrent access and why HashMap is not thread-safe.
 *
 * Example:
 *   If 5 threads each increment a counter 10,000 times:
 *   - Expected final value: 50,000
 *   - ConcurrentHashMap: exactly 50,000 (thread-safe, atomic)
 *   - HashMap: < 50,000 (lost updates due to race conditions)
 *
 * ----------------------------------------------------------------------------
 * Deep-Dive Technical Explanation:
 * ----------------------------------------------------------------------------
 *
 * 1. Why HashMap is NOT Thread-Safe:
 *    - Lack of Synchronization:
 *      HashMap methods and internal operations (including put, compute, and merge)
 *      have NO synchronization, locks, or memory barriers.
 *    - Lost Updates (Race Conditions):
 *      When two threads read the existing value (e.g., v = 42) simultaneously,
 *      both increment it to 43 and write back 43. One increment is completely lost!
 *    - Memory Visibility Issues:
 *      Internal fields (e.g., table array, node values) are not declared `volatile`.
 *      Changes made by one thread in its CPU core cache may not be immediately
 *      visible to other threads, causing stale reads.
 *    - Structural Corruption:
 *      Under high concurrent write workloads, concurrent resizing or linked-list /
 *      red-black tree conversions can corrupt bucket pointers, leading to lost
 *      nodes or even infinite loops (in Java 7 circular list bug) or runtime exceptions.
 *
 * 2. Why ConcurrentHashMap IS Designed for Concurrent Access:
 *    - Bucket-Level (Bin-Level) Locking:
 *      Instead of locking the entire map (like Hashtable or Collections.synchronizedMap),
 *      ConcurrentHashMap locks ONLY the specific hash bucket being updated using
 *      `synchronized` on the head node of that bucket bin. Threads updating different
 *      keys in different bins execute completely concurrently without blocking!
 *    - Lock-Free Reads:
 *      Read operations (like get()) are completely lock-free. Node values and `next`
 *      pointers are declared `volatile`, ensuring immediate visibility across CPU cores
 *      without synchronization overhead.
 *    - Lock-Free Initial Node Insertion:
 *      When inserting the first node into an empty bin, ConcurrentHashMap uses
 *      hardware-level Compare-And-Swap (CAS via VarHandle / Unsafe), avoiding locking altogether.
 *    - Atomic Operations:
 *      Methods like compute(), computeIfAbsent(), and merge() execute the user-supplied
 *      mapping function while holding the lock on that specific bin's head node,
 *      guaranteeing that the entire read-modify-write cycle is strictly atomic.
 *    - Concurrent Resizing:
 *      ConcurrentHashMap supports cooperative multi-threaded resizing. When a resize
 *      occurs, other threads calling put() can help transfer bins to the new table
 *      concurrently rather than waiting on a global lock.
 *
 * Visual Trace: Concurrent Updates on Shared Map
 * ----------------------------------------------------------------------------
 * Scenario: 2 Threads incrementing "counter" from 10:
 *
 * HashMap (Race Condition / Lost Update):
 *   Thread 1: Reads value (10) ------> Computes 10 + 1 = 11 ------> Writes 11
 *   Thread 2: Reads value (10) ------> Computes 10 + 1 = 11 ------> Overwrites with 11!
 *   Result: Value is 11 instead of 12! (1 update lost)
 *
 * ConcurrentHashMap (Bucket Lock / Atomic Compute):
 *   Thread 1: Acquires lock on Bin("counter") -> Reads (10) -> Writes (11) -> Releases lock
 *   Thread 2: Waits for lock --------------> Acquires lock -> Reads (11) -> Writes (12) -> Releases lock
 *   Result: Value is 12! (Zero updates lost)
 * ----------------------------------------------------------------------------
 *
 * Complexity Analysis:
 * - Time Complexity:  O(T * N), where T is thread count and N is iterations per thread.
 *                     With ConcurrentHashMap, updates to the same key are serialized
 *                     at bucket-level locking, giving O(1) per increment operation.
 * - Space Complexity: O(1) auxiliary space, as both maps store only a single key ("counter").
 * ============================================================================
 */
package com.practice.java.basics._1_hashmap_vs_concurrenthashmap;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

public class Solution_HashMapVsConcurrentHashMap {

    public static void main(String[] args) {
        int threadCount = Challenge_HashMapVsConcurrentHashMap.getSampleThreadCount();
        int iterationsPerThread = Challenge_HashMapVsConcurrentHashMap.getSampleIterationsPerThread();
        int expectedCount = threadCount * iterationsPerThread;

        System.out.println("====================================================================");
        System.out.println("Java Basics / Concurrency: HashMap vs ConcurrentHashMap");
        System.out.println("Threads: " + threadCount + ", Iterations per thread: " + iterationsPerThread);
        System.out.println("Expected Final Count: " + expectedCount);
        System.out.println("====================================================================");

        // Solution 1: CountDownLatch + Threads (Recommended Interview Approach)
        System.out.println("\n--- Solution 1: CountDownLatch + Threads (Recommended) ---");
        Map<String, Integer> result1 = solution1_CountDownLatch(threadCount, iterationsPerThread);
        printResults(result1, expectedCount);

        // Solution 2: ExecutorService Thread Pool + Map.merge
        System.out.println("\n--- Solution 2: ExecutorService Thread Pool + Map.merge ---");
        Map<String, Integer> result2 = solution2_ExecutorService(threadCount, iterationsPerThread);
        printResults(result2, expectedCount);

        // Solution 3: CompletableFuture.allOf
        System.out.println("\n--- Solution 3: CompletableFuture.allOf ---");
        Map<String, Integer> result3 = solution3_CompletableFuture(threadCount, iterationsPerThread);
        printResults(result3, expectedCount);

        // Solution 4: High-Contention Optimization via LongAdder (Interview Bonus)
        System.out.println("\n--- Solution 4: ConcurrentHashMap + LongAdder (High Contention Bonus) ---");
        Map<String, Long> result4 = solution4_LongAdderHighContention(threadCount, iterationsPerThread);
        result4.forEach((type, count) -> {
            System.out.println("  " + type + " final count: " + count +
                    " | Matches expected (" + expectedCount + "): " + (count == expectedCount));
        });
    }

    /**
     * Solution 1: CountDownLatch + Threads (Recommended — Clean and Idiomatic)
     *
     * How it works:
     * 1. Check for invalid or non-positive threadCount or iterationsPerThread.
     * 2. Instantiate both HashMap and ConcurrentHashMap instances.
     * 3. Initialize a CountDownLatch with a count of threadCount.
     * 4. Spawn threadCount individual Thread instances. Inside each thread's runnable:
     *    - Loop iterationsPerThread times.
     *    - Call map.compute("counter", (k, v) -> (v == null ? 1 : v + 1)) on both maps.
     *    - In a `finally` block, invoke latch.countDown() to guarantee notification.
     * 5. The calling thread blocks on latch.await() until all threads complete.
     * 6. Retrieve the counter from both maps and return the comparison result.
     *
     * Why ConcurrentHashMap succeeds:
     * - `compute()` acquires the intrinsic lock on the specific hash bucket head node,
     *   ensuring the increment is atomic across threads.
     * Why HashMap fails:
     * - `compute()` on HashMap is not synchronized; multiple threads interleave their
     *   reads and writes, resulting in lost updates.
     *
     * Complexity:
     * - Time Complexity:  O(T * N), where T = threadCount, N = iterationsPerThread.
     * - Space Complexity: O(1) auxiliary space (single key stored in each map).
     *
     * @param threadCount         number of concurrent threads
     * @param iterationsPerThread increments per thread
     * @return map with final counts for "ConcurrentHashMap" and "HashMap"
     */
    public static Map<String, Integer> solution1_CountDownLatch(int threadCount, int iterationsPerThread) {
        if (threadCount <= 0 || iterationsPerThread <= 0) {
            return Collections.emptyMap();
        }

        Map<String, Integer> hashMap = new HashMap<>();
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < iterationsPerThread; j++) {
                        hashMap.compute("counter", (k, v) -> (v == null ? 1 : v + 1));
                        concurrentMap.compute("counter", (k, v) -> (v == null ? 1 : v + 1));
                    }
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Map<String, Integer> result = new LinkedHashMap<>();
        result.put("ConcurrentHashMap", concurrentMap.getOrDefault("counter", 0));
        result.put("HashMap", hashMap.getOrDefault("counter", 0));
        return result;
    }

    /**
     * Solution 2: ExecutorService Thread Pool + Map.merge (Industry Standard)
     *
     * How it works:
     * 1. Uses a fixed thread pool of size threadCount via Executors.newFixedThreadPool.
     * 2. Submits threadCount tasks to the executor pool.
     * 3. Uses `map.merge("counter", 1, Integer::sum)` to perform updates.
     *    - On ConcurrentHashMap, `merge()` is an atomic operation synchronized on the bin.
     *    - On HashMap, `merge()` is non-thread-safe and suffers lost updates.
     * 4. Shuts down the executor pool gracefully and awaits termination.
     *
     * Complexity:
     * - Time Complexity:  O(T * N), where T = threadCount, N = iterationsPerThread.
     * - Space Complexity: O(1) auxiliary space.
     *
     * @param threadCount         number of concurrent threads
     * @param iterationsPerThread increments per thread
     * @return map with final counts for "ConcurrentHashMap" and "HashMap"
     */
    public static Map<String, Integer> solution2_ExecutorService(int threadCount, int iterationsPerThread) {
        if (threadCount <= 0 || iterationsPerThread <= 0) {
            return Collections.emptyMap();
        }

        Map<String, Integer> hashMap = new HashMap<>();
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < iterationsPerThread; j++) {
                    hashMap.merge("counter", 1, Integer::sum);
                    concurrentMap.merge("counter", 1, Integer::sum);
                }
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        Map<String, Integer> result = new LinkedHashMap<>();
        result.put("ConcurrentHashMap", concurrentMap.getOrDefault("counter", 0));
        result.put("HashMap", hashMap.getOrDefault("counter", 0));
        return result;
    }

    /**
     * Solution 3: CompletableFuture.allOf (Modern Asynchronous Approach)
     *
     * How it works:
     * 1. Creates an array of CompletableFuture tasks using CompletableFuture.runAsync().
     * 2. Uses a dedicated ExecutorService so threads run in parallel as intended.
     * 3. Calls CompletableFuture.allOf(futures).join() to non-blockingly compose
     *    and synchronize on the completion of all asynchronous operations.
     * 4. Shuts down the executor and compiles results.
     *
     * Complexity:
     * - Time Complexity:  O(T * N), where T = threadCount, N = iterationsPerThread.
     * - Space Complexity: O(T) space for the CompletableFuture array.
     *
     * @param threadCount         number of concurrent threads
     * @param iterationsPerThread increments per thread
     * @return map with final counts for "ConcurrentHashMap" and "HashMap"
     */
    public static Map<String, Integer> solution3_CompletableFuture(int threadCount, int iterationsPerThread) {
        if (threadCount <= 0 || iterationsPerThread <= 0) {
            return Collections.emptyMap();
        }

        Map<String, Integer> hashMap = new HashMap<>();
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        CompletableFuture<?>[] futures = new CompletableFuture[threadCount];
        for (int i = 0; i < threadCount; i++) {
            futures[i] = CompletableFuture.runAsync(() -> {
                for (int j = 0; j < iterationsPerThread; j++) {
                    hashMap.compute("counter", (k, v) -> (v == null ? 1 : v + 1));
                    concurrentMap.compute("counter", (k, v) -> (v == null ? 1 : v + 1));
                }
            }, executor);
        }

        CompletableFuture.allOf(futures).join();
        executor.shutdown();

        Map<String, Integer> result = new LinkedHashMap<>();
        result.put("ConcurrentHashMap", concurrentMap.getOrDefault("counter", 0));
        result.put("HashMap", hashMap.getOrDefault("counter", 0));
        return result;
    }

    /**
     * Solution 4: ConcurrentHashMap with LongAdder (High Contention Optimization — Interview Bonus)
     *
     * Interview Follow-Up Question:
     * "In ConcurrentHashMap, when 100 threads update the SAME key simultaneously,
     * they all contend for that single bucket's synchronized lock. How can we optimize this
     * for massive throughput?"
     *
     * How it works:
     * 1. Store a `LongAdder` as the map value: `ConcurrentHashMap<String, LongAdder>`.
     * 2. Instead of locking the map entry on every increment, each thread retrieves the
     *    `LongAdder` reference and calls `adder.increment()`.
     * 3. `LongAdder` maintains a striped array of internal `Cell` counters across CPU cores.
     *    Under high contention, threads write to different cells without blocking each other.
     * 4. The final sum is aggregated across all cells via `adder.sum()`.
     *
     * Complexity:
     * - Time Complexity:  O(T * N) throughput, but orders of magnitude faster due to zero lock contention.
     * - Space Complexity: O(Cells) where cells dynamically adapt up to the number of CPU cores.
     *
     * @param threadCount         number of concurrent threads
     * @param iterationsPerThread increments per thread
     * @return map containing the aggregated sum from LongAdder
     */
    public static Map<String, Long> solution4_LongAdderHighContention(int threadCount, int iterationsPerThread) {
        if (threadCount <= 0 || iterationsPerThread <= 0) {
            return Collections.emptyMap();
        }

        ConcurrentHashMap<String, LongAdder> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.putIfAbsent("counter", new LongAdder());

        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    LongAdder adder = concurrentMap.get("counter");
                    for (int j = 0; j < iterationsPerThread; j++) {
                        adder.increment();
                    }
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Map<String, Long> result = new LinkedHashMap<>();
        result.put("ConcurrentHashMap+LongAdder", concurrentMap.get("counter").sum());
        return result;
    }

    private static void printResults(Map<String, Integer> result, int expectedCount) {
        result.forEach((type, count) -> {
            boolean matches = (count == expectedCount);
            int lost = expectedCount - count;
            System.out.printf("  %-18s : %6d / %6d | Matches expected: %-5s | Lost updates: %d%n",
                    type, count, expectedCount, matches, Math.max(0, lost));
        });
    }
}
