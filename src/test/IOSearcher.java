package test;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * If bloomfilter didn't find and also the cachemanger we'll reach here
 * The IOsearcher will look into the files to see if the words exist
 */

public class IOSearcher {

    /**
     * The IOSearcher class is used to search for a given word in a list of files.
     * It reads each file line by line and checks if the line contains the word.
     * If the word is found in any of the files, it returns true. Otherwise, it returns false.
     *
     * @param word The word to search for.
     * @param filesNames The names of the files to search in.
     * @return true if the word is found in any of the files, false if it is not.
     * @throws IOException if an I/O error occurs while reading the files.
     */
    public static boolean search(String word, String... filesNames) throws IOException
    {
        for(String nameOfFile : filesNames)
        {
            BufferedReader reader = new BufferedReader(new FileReader(nameOfFile));

            String Line;

            while((Line = reader.readLine()) != null)
                if(Line.contains(word))
                {
                    reader.close();
                    return true;
                }
        }

        return false;
    }
}