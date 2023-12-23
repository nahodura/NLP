import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * FileMap is a HashMap that maps a File to an ArrayList of Integers.
 * The ArrayList contains the positions of a word in the file.
 *
 * The initial capacity of the FileMap is 50. If the size of the FileMap is greater
 * than 75% of the capacity, the capacity is doubled.
 */
public class WordMap {
    private HashMap<String, FileMap> wordMap;
    private final double sizeFactor = 0.75;

    public WordMap() {
        this.wordMap = new HashMap<>(50);
    }

    public void addWord(String word, File file, Integer position) {
        //If the size of the WordMap is greater than 75% of the capacity, double the capacity.
        if (wordMap.size() + 1 > (sizeFactor * wordMap.size())){
            HashMap<String, FileMap> newWordMap = new HashMap<>(wordMap.size() * 2 + 1);
            for (String wordInMap : wordMap.keySet()) {
                newWordMap.put(wordInMap, wordMap.get(wordInMap));
            }
            wordMap = newWordMap;
        }
        word = word.toLowerCase();
        //If the word is not already in the map, add the word to the WordMap.
        if (!wordMap.containsKey(word)) {
            wordMap.put(word, new FileMap());
        }
        //Add the file and position of the word to the FileMap.
        wordMap.get(word).addFile(file, position);
    }
    public HashMap<String, FileMap> getWordMap() {
        return new HashMap<>(wordMap);
    }

}