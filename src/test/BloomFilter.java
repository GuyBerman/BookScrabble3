package test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;


/**
 * We'll reach here if the cacheManger hasn't found the word in the dictinaroy
 * Checks if a word is a member of a set
 */
public class BloomFilter {

    private final BitSet bitset;

    /**
     * An array of hash functions used to generate hashes for the elements being added to the filter.
     */
    private final MessageDigest[] hashGenerators;

    /**
     * The size of the filter, i.e. the number of bits in the underlying BitSet.
     */
    private final int size;


    /**
     * Constructs a new Bloom filter with the specified size and hash functions.
     *
     * @param size the size of the filter, i.e. the number of bits in the underlying BitSet
     * @param algs the names of the hash algorithms to use (e.g. "MD5", "SHA-1", "SHA-256")
     * @throws IllegalArgumentException if the size is not positive
     * @throws RuntimeException if a hash algorithm is not supported
     */
    public BloomFilter(int size, String... algs) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }

        this.size = size;
        this.bitset = new BitSet(this.size);
        hashGenerators = new MessageDigest[algs.length];

        for (int i = 0; i < algs.length; i++) {
            try {
                hashGenerators[i] = MessageDigest.getInstance(algs[i]);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Invalid hash algorithm: " + algs[i], e);
            }
        }
    }

    /**
     * Adds an element to the filter.
     *
     * @param word the element to add
     * @throws NullPointerException if the element is null
     */

    public void add(String word) {
        for (MessageDigest digest : hashGenerators) {
            byte[] bytes = digest.digest(word.getBytes(StandardCharsets.UTF_8));
            int hash = new BigInteger(1, bytes).intValue();
            bitset.set(Math.abs(hash % size));
        }
    }


    /**
     * Tests whether the filter might contain the specified element.
     *
     * @param word the elemnet to test
     * @return true if the element might be contained in the filter, false if it is definitely not contained
     * @throws NullPointerException if the word  is null
     */
    public boolean contains(String word) {
        if (word == null) {
            throw new NullPointerException("Word must not be null");
        }

        for (MessageDigest digest : hashGenerators) {
            byte[] bytes = digest.digest(word.getBytes(StandardCharsets.UTF_8));
            int hash = new BigInteger(1, bytes).intValue();
            if (!bitset.get(Math.abs(hash % size)))
                return false;
        }

        return true;
    }


    /**
     * Returns a string representation of the filter, where each '1' represents a set bit and each '0' represents
     * a unset bit.
     *
     * @return a string representation of the filter
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bitset.length(); i++) {
            sb.append(bitset.get(i) ? '1' : '0');
        }
        return sb.toString();
    }

}