import java.io.*;
import java.util.HashMap;
import java.util.NavigableMap;

import static edu.stanford.nlp.util.StringUtils.levenshteinDistance;

public class Queries extends TFIDF{
    BufferedReader fr;
    BufferedWriter fw;

    public Queries() throws IOException {
        this.fr = new BufferedReader(new FileReader("./query.txt"));
        this.fw = new BufferedWriter(new FileWriter("./solutions.txt"));
    }

    /**
     * Runs the queries for either "search" or "the most probable bigram of".
     * @throws IOException
     */
    public void run() throws IOException {
        String line;

        //For each line in the query.txt file, split the line into an array of Strings.
        while ((line = fr.readLine()) != null) {
            String[] query = line.split(" ");

            switch (query[0]) {
                case "search" -> {
                    //Create a HashMap to store the files and their TFIDF scores.
                    HashMap<File, Double> fileScores = new HashMap<>();
                    for (int i = 1; i < query.length; i++) {
                        String queryWord = query[i];
                        queryWord = levenshtein(queryWord).toLowerCase();

                        //Check if the word is in the WordMap.
                        if (Main.wordMap.getWordMap().containsKey(queryWord)) {
                            for (File file : Main.wordMap.getWordMap().get(queryWord).getFiles()) {
                                //Create a TFIDF object.
                                int wordCount = Main.wordCount.get(file);
                                int wordFrequency = Main.wordMap.getWordMap().get(queryWord).getPositions(file).size();
                                double tfidfScore = getTFIDF(
                                        //Number of times the word appears in the file.
                                        wordFrequency,
                                        //Number of words in the file.
                                        wordCount,
                                        //Total number of documents
                                        Main.totalDocs,
                                        //Number of documents the word appears in.
                                        Main.wordMap.getWordMap().get(queryWord).getFiles().size());

                                //Add the file and the TFIDF score to the HashMap.
                                fileScores.put(file, fileScores.getOrDefault(file, 0.0) + tfidfScore);
                            }
                        } else {
                            String result = "No results found for " + queryWord;
                            writeToFile(result);
                        }
                    }
                    //Return the file with the highest TFIDF score.
                    File fileWithHighestScore = null;
                    double highestScore = 0;
                    for (File file : fileScores.keySet()) {
                        if (fileScores.get(file) > highestScore) {
                            fileWithHighestScore = file;
                            highestScore = fileScores.get(file);
                        }
                    }
                    String result = fileWithHighestScore.getName();
                    writeToFile(result);
                }
                case "the" -> { //"the most probable bigram of"
                    String queryWord = query[query.length - 1];
                    queryWord = levenshtein(queryWord).toLowerCase();

                    String mostProbableWord = Main.bigramTree.mostLikely(queryWord);
                    writeToFile(mostProbableWord);
                }
                default -> System.out.println("Invalid query");
            }
            }
        }

    /**
     * Writes the result to the solutions.txt file.
     * @param result The result to be written to the solutions.txt file.
     * @throws IOException
     */
    private void writeToFile(String result) throws IOException {
        fw.write(result);
        fw.newLine();
        fw.flush();
    }

    /**
     * Returns the word with the smallest Levenshtein distance between the queryWord and the words in the WordMap.
     * @param queryWord The word to be compared to the words in the WordMap.
     * @return The word with the smallest Levenshtein distance between the queryWord and the words in the WordMap.
     */
    private static String levenshtein(String queryWord) {
        //Check the smallest Levenshtein distance between the queryWord and the words in the WordMap.
        int minDistance = Integer.MAX_VALUE;
        String word = "";
        for (String wordInMap : Main.wordMap.getWordMap().keySet()) {
            int distance = levenshteinDistance(queryWord, wordInMap);
            if (distance < minDistance) {
                minDistance = distance;
                word = wordInMap;
            }
        }
        return word;
    }

}
