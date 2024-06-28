package com.tuandhpc05076.demo;

import com.tuandhpc05076.core.nlp.TextAnalyzer;

import java.util.*;
import java.util.stream.IntStream;
import java.text.BreakIterator;

public class VietnameseTextProcessor {

    public List<String> extractRelevantSentences(String text, String word, int noSentences) {
        List<String> sentences = tokenizeTextInSentences(text);
        List<Map<String, Double>> tfIdfList = extractRelevantPhrases(sentences, word);

        // Sắp xếp các câu theo điểm TF-IDF của chúng
        List<Integer> sentenceIndices = IntStream.range(0, sentences.size())
                                                .boxed()
                                                .sorted((i, j) -> Double.compare(getScore(tfIdfList.get(j)), getScore(tfIdfList.get(i))))
                                                .limit(noSentences)
                                                .toList();

        // Trích xuất n câu đầu
        List<String> result = new ArrayList<>();
        for (int i : sentenceIndices) {
            result.add(sentences.get(i));
        }

        return result;
    }

    private List<Map<String, Double>> extractRelevantPhrases(List<String> sentences, String word) {
        List<Map<String, Double>> tfIdfList = new ArrayList<>();
        for (String sentence : sentences) {
            Map<String, Double> tfMap = new HashMap<>();
            String[] words = sentence.split("\\s+");
            int wordCount = 0;
            for (String w : words) {
                if (w.matches("\\p{Punct}")) {
                    continue;
                }
                if (w.equalsIgnoreCase(word)) {
                    wordCount++;
                }
                tfMap.put(w, tfMap.getOrDefault(w, 0.0) + 1.0);
            }

            double maxTf = Collections.max(tfMap.values());
            Map<String, Double> tfIdfMap = new HashMap<>();
            for (Map.Entry<String, Double> entry : tfMap.entrySet()) {
                String w = entry.getKey();
                double tf = entry.getValue() / maxTf;
                double idf = Math.log(sentences.size() / (double) countSentencesWithWord(sentences, w));
                tfIdfMap.put(w, tf * idf);
            }
            tfIdfList.add(tfIdfMap);
        }
        return tfIdfList;
    }

    private int countSentencesWithWord(List<String> sentences, String word) {
        int count = 0;
        for (String sentence : sentences) {
            if (sentence.contains(word)) {
                count++;
            }
        }
        return count;
    }

    private double getScore(Map<String, Double> tfIdfMap) {
        return tfIdfMap.values().stream()
                       .mapToDouble(Double::doubleValue)
                       .sum();
    }

    private List<String> tokenizeTextInSentences(String text) {
        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(new Locale("vi", "VN"));
        sentenceIterator.setText(text);
        List<String> sentences = new ArrayList<>();
        int start = sentenceIterator.first();
        for (int end = sentenceIterator.next(); end != BreakIterator.DONE; start = end, end = sentenceIterator.next()) {
            sentences.add(text.substring(start, end));
        }
        return sentences;
    }

    public static void main(String[] args) {
        String text = "This is an example of how to process English text in Java. " +
                              "We will use the TextAnalyzer class to extract sentences related " +
                              "to the keyword 'processing'. " +
                              "In text processing, we need to identify key phrases " +
                              "to fully understand the content of the text. " +
                              "Natural language processing is a rapidly growing field in " +
                              "information technology and artificial intelligence.";

        String keyword = "intelligence ";
        int noSentences = 2;

        TextAnalyzer textAnalyzer = new TextAnalyzer();
        List<String> relevantSentences = textAnalyzer.extractRelevantSentences(text, keyword, noSentences);

        System.out.println("Các câu có liên quan:");
        for (String sentence : relevantSentences) {
            System.out.println(sentence);
        }
    }
}

