package test;

import java.util.LinkedHashSet;

/**
 * This class implements a simple cache manager that stores a fixed number of elements
 * in a LinkedHashSet.
 */
public class CacheManager {

    /** The maximum number of elements that the cache can hold. */
    private final int cacheSize;

    /** The LinkedHashSet used to cache the elements. */
    private final LinkedHashSet<String> cache;

    /** An object that implements the CacheReplacementPolicy interface. */
    CacheReplacementPolicy crp;

    /**
     * Constructs a new CacheManager with the specified size and replacement policy.
     *
     * @param size the maximum number of elements that the cache can hold
     * @param crp an object that implements the CacheReplacementPolicy interface
     */
    public CacheManager(int size, CacheReplacementPolicy crp) {
        this.cache = new LinkedHashSet<>(size);
        this.cacheSize = size;
        this.crp = crp;
    }

    /**
     * Returns true if the specified word is present in the cache, and false otherwise.
     *
     * @param word the word to search for in the cache
     * @return true if the word is present in the cache, false otherwise
     */
    public boolean query(String word) {
        return cache.contains(word);
    }

    /**
     * Adds the specified word to the cache, and calls the add method on the
     * CacheReplacementPolicy object. If the size of the cache exceeds the cacheSize,
     * it removes an element from the cache using the remove method of the CacheReplacementPolicy object.
     *
     * @param word the word to add to the cache
     */
    public void add(String word) {
        crp.add(word);
        cache.add(word);
        if (cache.size() > getCacheSize()) {
            cache.remove(crp.remove());
        }
    }

    /**
     * Returns the LinkedHashSet used to cache the elements.
     *
     * @return the cache
     */
    public LinkedHashSet<String> getCache() {
        return cache;
    }

    /**
     * Returns the maximum number of elements that the cache can hold.
     *
     * @return the cache size
     */
    public int getCacheSize() {
        return cacheSize;
    }
}