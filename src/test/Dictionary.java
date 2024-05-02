package test;

import java.io.BufferedReader;
import java.io.FileReader;

public class Dictionary {
    // cache manager for words that have been confirmed to exist
    private final CacheManager confirmedWords;
    // cache manager for words that have been confirmed to not exist
    private final CacheManager unconfirmedWords;
    // bloom filter for quick word existence checking
    private final BloomFilter bloomFilter;
    // list of text files to check for word existence
    private final String[] textFiles;

    public Dictionary(String... fileNames) {
        confirmedWords = new CacheManager(400, new LRU());
        unconfirmedWords = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(256, "MD5", "SHA1");
        textFiles = fileNames;

        // Reading the words from the text files and adds them to the bloom filter
        for (String fileName : fileNames) {
            try {
                FileReader fr = new FileReader(fileName);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] words = line.split(" ");
                    for (String word : words) {
                        bloomFilter.add(word);
                    }
                }
                fr.close();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public boolean query(String word)
    {
        return checkWordExistence(word);
    }

    public boolean challenge(String word)
    {
        return searchInTextFiles(word);
    }

    /**
     * Check if a given word is present in the text files
     * @param word the word to check for existence
     * @return true if the word exists in the text files, false otherwise
     */
    public boolean checkWordExistence(String word) {
        if (confirmedWords.query(word)) {
            return true;
        }
        if (unconfirmedWords.query(word)) {
            return false;
        }

        if (bloomFilter.contains(word)) {
            confirmedWords.add(word);
            return true;
        } else {
            unconfirmedWords.add(word);
            return false;
        }

    }

    /**
     * Search for the given word in the text files
     * @param word the word to search for
     * @return true if the word is found in the text files, false otherwise
     */
    public boolean searchInTextFiles(String word) {
        boolean isFound = false;
        try {
            isFound = IOSearcher.search(word, textFiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isFound;
    }
}