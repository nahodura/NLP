import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NavigableMap;

public class Main {
    protected static WordMap wordMap = new WordMap();
    protected static BigramTreeMap bigramTree = new BigramTreeMap();
    protected static HashMap<File, Integer> wordCount = new HashMap<File, Integer>();
    public static int totalDocs;
    public static void main(String[] args) throws IOException{

        String directory = "./dataset";

        //Create a File object for the directory.
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null; //listOfFiles should not be null.
        totalDocs = listOfFiles.length;

        //For each file in the directory, create a NLP object and lemmatise the file.
        for (File file : listOfFiles) {
            if (file.isFile()) {
                NLP nlp = new NLP(file);
                ArrayList<String> lemmatizedWords = nlp.lemmatize();
                wordCount.put(file, lemmatizedWords.size());

                int index = 0;
                for (String wordInDoc : lemmatizedWords) {
                    //Add the word to the WordMap.
                    wordMap.addWord(wordInDoc, file, index);
                    index++;
                    //If the index is equal to the size of the ArrayList, break out of the loop.
                    if (index == lemmatizedWords.size()) {
                        break;
                    }
                    //Add the bigram to the BigramTreeMap.
                    bigramTree.addBigram(wordInDoc, lemmatizedWords.get(index));
                }
            }
        }

        //Create a Queries object and run the queries.
        Queries queries = new Queries();
        queries.run();

    }
}

