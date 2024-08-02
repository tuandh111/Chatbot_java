package com.tuandhpc05076.core.knowledge;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Wikipedia {
    private static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/wiki/";
    @Autowired
    SolrAPI solrAPI;

    public String wikipediaGetContent(String inputString) throws Exception {

        String wikiPageUrl = WIKIPEDIA_SEARCH_URL + inputString.replace(" ", "_");
        String wikiPageContent = "";

        try {
            //Tìm nạp trang Wikipedia cho truy vấn tìm kiếm
            Document doc = Jsoup.connect(wikiPageUrl).get();

            // Trích xuất nội dung của trang
            Element content = doc.select("div#mw-content-text").first();
            wikiPageContent = content.text();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(wikiPageContent);

        return wikiPageContent;
    }

//    public String wikipediaGetContent(String inputString) throws Exception {
//        String wikiPageUrl = WIKIPEDIA_SEARCH_URL + inputString.replace(" ", "_");
//        StringBuilder wikiPageContent = new StringBuilder();
//
//        try {
//            Document doc = Jsoup.connect(wikiPageUrl).get();
//            Elements paragraphs = doc.select("div#mw-content-text p");
//
//            for (Element paragraph : paragraphs) {
//                String paragraphText = paragraph.text();
//                // Check if paragraph contains keywords related to health or medicine
//                if (containsHealthKeywords(paragraphText)) {
//                    wikiPageContent.append(paragraphText).append("\n");
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(wikiPageContent.toString());
//        return wikiPageContent.toString();
//    }
    private boolean containsHealthKeywords(String text) {
        // Add your keywords or logic here to identify health-related content
        String[] healthKeywords = {"health", "medical", "medicine", "hospital", "doctor"};
        for (String keyword : healthKeywords) {
            if (text.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
