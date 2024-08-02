package com.tuandhpc05076.core.knowledge;

import com.tuandhpc05076.core.nlp.TextAnalyzer;
import com.tuandhpc05076.helper.TextUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KnowledgeBase {

    public static final String HELLO_MESSAGE = "Tôi tên là Tuấn, tôi có thể giúp gì được cho bạn";
    public static final String DEFAULT_ANSWER = "Tôi không hiểu bạn đang nói gì";
    public static final String DEFAULT_IDK = "Tại thời điểm này, tôi không có đủ kiến thức cần thiết để đưa ra câu trả lời thuyết phục. Tuy nhiên, tôi cam kết mở rộng kiến \u200B\u200Bthức của mình và sẽ quay lại với câu trả lời đầy đủ thông tin hơn sau. Cảm ơn bạn đã hiểu biết của bạn.";
    @Autowired
    SolrAPI solrAPI;

    @Autowired
    TextAnalyzer textAnalyzer;

    @Autowired
    Wikipedia wikipedia;

    @Autowired
    TextUtils textUtils;

    public String getdataFromSolr(String inputStr) throws SolrServerException, IOException {
        return solrAPI.solrSearch(inputStr);
    }

    public String searchForMoreKnowledge(String inputStr) throws Exception {
        String keyWord = textUtils.extractWordsAfterAbout(inputStr);
        System.out.println(keyWord);
        String answer = textAnalyzer.extractRelevantSentences(wikipedia.wikipediaGetContent(keyWord), keyWord, 1).get(0);

        //solrAPI.solrIndexer(answer, keyWord);

        return answer;

    }

}
