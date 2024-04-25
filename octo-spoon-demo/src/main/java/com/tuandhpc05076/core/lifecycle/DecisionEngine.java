package com.tuandhpc05076.core.lifecycle;

import com.tuandhpc05076.core.nlp.SentimentAnalyzer;
import com.tuandhpc05076.helper.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DecisionEngine {

    @Autowired
    Flow flow;

    @Autowired
    TextUtils textUtils;

    @Autowired
    SentimentAnalyzer sentimentAnalyzer;

    public State decideCurrentConversationState(String text) {
        List<State> candidateStates = new ArrayList<>();
        for (State state : flow.setupFlow()) {
            //chua it nhat 1 tu khoa trang thai
            if (textUtils.containsAnyWord(text, state.getKeywords())) {
                candidateStates.add(state);
            }
        }
        if (!candidateStates.isEmpty()) {
            //co do uu tien thap nhat hoac tra ve gia trị null
            return candidateStates.stream().
                    min(Comparator.comparing(State::getPriority)).orElse(null);
        }
        return flow.getDefaultState();
    }

    public String sentimentAnalyzerMessage(String inputText) {
        return "Cảm xúc: "
                + sentimentAnalyzer.sentimentAnalyzer(inputText);
    }


}
