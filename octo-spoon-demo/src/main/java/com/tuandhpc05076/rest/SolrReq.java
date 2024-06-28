package com.tuandhpc05076.rest;

import com.tuandhpc05076.core.knowledge.SolrAPI;
import com.tuandhpc05076.demo.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SolrReq {

    @Autowired
    SolrAPI solrAPI;

    @GetMapping("/solrIndexer/{inputWord}")
    public String solrIndexer(@PathVariable("inputWord") String inputWord) throws Exception {
        solrAPI.solrIndexer(inputWord, inputWord);
        return "let's see";
    }

    @GetMapping("/solrSearch/{inputWord}")
    public String solrSearch(@PathVariable("inputWord") String inputWord) throws Exception {
        return solrAPI.solrSearch(inputWord);
    }

    @PostMapping("add")
    public String addDocument(@RequestBody Document document) {
        try {
            solrAPI.solrIndexer(document);
            return "Document added to Solr!";
        } catch (Exception e) {
            return "Failed to add document to Solr: " + e.getMessage();
        }
    }
    @DeleteMapping("delete/{id}")
    public String deleteDocument(@PathVariable String id) {
        try {
            solrAPI.deleteDocumentFromSolr(id);
            return "Document deleted to Solr!";
        } catch (Exception e) {
            return "Failed to deleted document to Solr: " + e.getMessage();
        }
    }
}
