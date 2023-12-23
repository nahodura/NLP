abstract class TFIDF {

    // int countW; number of times the word appears in the document
    // int totalWordsInDoc; total number of words in the document
    //int totalDocs; number of documents in the corpus
    //int docsWithWord; number of documents in the corpus that contain the word

    public double getTF(int countW, int totalWordsInDoc) {
        return (double) countW / totalWordsInDoc;
    }

    public double getIDF(int totalDocs, int docsWithWord) {
        return 1 + Math.log((double)( 1 + totalDocs) / ( 1+ docsWithWord));
    }

    public double getTFIDF(int countW, int totalWordsInDoc, int totalDocs, int docsWithWord) {
        return getTF(countW, totalWordsInDoc) * getIDF(totalDocs, docsWithWord);
    }


}
