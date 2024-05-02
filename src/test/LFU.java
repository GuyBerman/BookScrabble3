package test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * This class implements the CacheReplacementPolicy interface and uses the Least Frequently Used (LFU) algorithm
 * to manage a cache of words.
 *
 * The cache is implemented using a LinkedHashMap, which maintains the insertion order of the elements.
 * When a new element is added to the cache, its frequency is set to 1. If the element is already present in the cache,
 * its frequency is incremented by 1.
 *
 * The remove() method removes the element with the lowest frequency from the cache and returns it. If multiple elements
 * have the same lowest frequency, the element that was least recently added is removed. If the cache is empty,
 * a NoSuchElementException is thrown.
 */
public class LFU implements CacheReplacementPolicy {

    // LinkedHashMap to store the elements in the cache and their frequencies
    private final LinkedHashMap<String, Integer> cache;

    // Minimum frequency of the elements in the cache
    private int min;

    /**
     * Constructs a new LFU cache.
     */
    public LFU() {
        this.cache = new LinkedHashMap<>();
        this.min = 0;
    }

    /**
     * Adds a new element to the cache.
     * If the element is already present in the cache, its frequency is incremented by 1.
     * If the element is not present in the cache, its frequency is set to 1.
     *
     * @param word the element to be added to the cache
     */
    @Override
    public void add(String word) {
        if (cache.containsKey(word)) {
            cache.put(word, cache.get(word) + 1);
        } else {
            cache.put(word, 1);
        }

        min = 1;
    }

    /**
     * Removes the element with the lowest frequency from the cache and returns it.
     * If multiple elements have the same lowest frequency, the element that was least recently added is removed.
     * If the cache is empty, a NoSuchElementException is thrown.
     *
     * @return the element with the lowest frequency in the cache
     * @throws NoSuchElementException if the cache is empty
     */
    @Override
    public String remove() throws NoSuchElementException {

        for (Map.Entry<String,Integer> entry : cache.entrySet()) {
            if (entry.getValue() == min) {
                String remove = entry.getKey();
                if (remove != null) {
                    min++;
                    return remove;
                }
            }
        }
        throw new NoSuchElementException("Element not found in cache");
    }

    /**
     * Returns the LinkedHashMap that stores the elements in the cache and their frequencies.
     *
     * @return the LinkedHashMap that stores the elements in the cache and their frequencies
     */
    public LinkedHashMap<String, Integer> getCache() {
        return cache;
    }
}