import java.util.Map;
import java.util.TreeMap;
import java.util.NavigableMap;

public class BigramTreeMap {
    private TreeMap<String, Integer> bigramTreeMap;

    public BigramTreeMap() {
        bigramTreeMap = new TreeMap<>();
    }

    /**
     * Adds a bigram to the bigramTreeMap.
     * If the bigram is already in the bigramTreeMap, the value is incremented by 1.
     * @param word1 The first word in the bigram.
     * @param word2 The second word in the bigram.
     */
    public void addBigram(String word1, String word2) {
        assert word1 != null && word2 != null; //word1 and word2 should not be null.
        String bigram = word1 + " " + word2;
        //If the bigram is not already in the bigramTreeMap, add the bigram to the bigramTreeMap.
        bigramTreeMap.put(bigram, bigramTreeMap.getOrDefault(bigram, 0) + 1);
    }

    /**
     * Returns a submap of the bigramTreeMap.
     * @param word The word to start the bigram.
     * @return A submap of the bigramTreeMap.
     */
    public NavigableMap<String, Integer> getBigramsStartingWith(String word) {
        String fromKey = word + " ";
        String toKey = word + Character.MAX_VALUE;
        //Return a submap of the bigramTreeMap.
        return bigramTreeMap.subMap(fromKey, true, toKey, false);
    }

    /**
     * Returns the number of times the bigram appears in the bigramTreeMap.
     * @param word1 The first word in the bigram.
     * @param word2 The second word in the bigram.
     * @return The number of times the bigram appears in the bigramTreeMap.
     */
    public Integer getBigramCount(String word1, String word2) {
        String bigram = word1 + " " + word2;
        return bigramTreeMap.getOrDefault(bigram, 0);
    }

    /**
     * Returns the most likely word to follow the startingWord.
     * @param startingWord The word to start the bigram.
     * @return The most likely word to follow the startingWord.
     */
    public String mostLikely(String startingWord) {
            NavigableMap<String, Integer> bigramsStartingWith = getBigramsStartingWith(startingWord);
            String mostLikely = "";
            int max = 0;
            for (String bigram : bigramsStartingWith.keySet()) {
                if (bigramsStartingWith.get(bigram) > max) {
                    mostLikely = bigram;
                    max = bigramsStartingWith.get(bigram);
                }
            }
        return mostLikely;
    }

}
