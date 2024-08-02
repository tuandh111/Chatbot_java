package com.tuandhpc05076.core.nlp;

import com.tuandhpc05076.core.knowledge.Wikipedia;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentimentAnalyzer {
    @Autowired
    Wikipedia wikipedia;

    public String sentimentAnalyzer(String text) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        String retSentiment = "";
        //xu li ngon ngu tu nhien va nhan dien thuc the
        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        //xu li cam xuc
        List<CoreSentence> sentences = coreDocument.sentences();
        for (CoreSentence sentence : sentences) {
            String sentiment = sentence.sentiment();
            retSentiment = retSentiment + " " + sentiment;
        }
        return retSentiment;
    }
}
