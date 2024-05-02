package test;

import java.util.LinkedHashSet;

/**
 * A cache replacement policy that removes the least recently used word from the cache.
 */
public class LRU implements CacheReplacementPolicy {

    private final LinkedHashSet<String> cache;
    private final int maxSize;

    /**
     * Creates a new LRU cache replacement policy.
     * maxSize the maximum size of the cache
     */
    public LRU() {
        this.cache = new LinkedHashSet<>();
        this.maxSize = 100;
    }

    /**
     * Adds a word to the cache.
     * If the word is already present in the cache, it is removed and re-added to the end of the cache.
     * If adding the word would cause the cache to exceed its maximum size, the least recently used word is removed.
     *
     * @param word the word to add to the cache
     */
    @Override
    public void add(String word) {
        this.cache.remove(word);
        this.cache.add(word);

        if (this.cache.size() > this.maxSize) {
            String leastRecentlyUsed = this.remove();
            this.cache.remove(leastRecentlyUsed);
        }
    }

    /**
     * Removes the least recently used word from the cache.
     *
     * @return the least recently used word that was removed from the cache
     */
    @Override
    public String remove() {
        String[] cacheArr = this.cache.toArray(new String[this.cache.size()]);
        return cacheArr[0];
    }
}
