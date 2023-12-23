import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class NLP {

    File file;
    BufferedReader br;

    public NLP(File file) throws FileNotFoundException {
        this.file = file;
        this.br = new BufferedReader(new FileReader(file));
    }

    /**
     * Lemmatizes the file.
     *
     * @throws IOException
     */
    public ArrayList<String> lemmatize() throws IOException {
        ArrayList<String> lemmatizedWords = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        String line;

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,pos,lemma");
        props.setProperty("coref.algorithm", "neural");
        RedwoodConfiguration.current().clear().apply();
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        while ((line = br.readLine()) != null) {
            String newline = line.replaceAll("[^’'a-zA-Z0-9]", " ");
            String finalLine = newline.replaceAll("\\s+", " ").trim();

            CoreDocument document = new CoreDocument(finalLine);
            pipeline.annotate(document);

            //For each token in the document, add the lemma to the ArrayList.
            for (CoreLabel tok : document.tokens()) {
                String str = String.valueOf(tok.lemma()); //Converts the CoreLabel object to a String.
                lemmatizedWords.add(str);
            }
        }
        return lemmatizedWords;
    }
}
