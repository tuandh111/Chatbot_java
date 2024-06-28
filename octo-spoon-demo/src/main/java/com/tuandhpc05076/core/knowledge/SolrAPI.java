package com.tuandhpc05076.core.knowledge;

import com.tuandhpc05076.config.SolrConfig;
import com.tuandhpc05076.demo.Document;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class SolrAPI {

    private static final String solrUrl = SolrConfig.SOLR_BASE_URL + SolrConfig.SOLR_CORE;

    public void solrIndexer(String str, String key) throws Exception {
        //ket noi
        SolrClient solr = new HttpSolrClient.Builder(solrUrl).build();
        // dai dien cho truong du lieu moi
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField(SolrConfig.SOLR_CONTENT_FIELD, str);
        doc.addField(SolrConfig.SOLR_CONTENT_KEY, key);

        solr.add(doc);
        solr.commit();
        solr.close();
    }

    public String solrSearch(String str) throws SolrServerException, IOException {
        //lay duong dan solr
        SolrClient solr = new HttpSolrClient.Builder(solrUrl).build();
        SolrQuery query = new SolrQuery();
        //truy van du lieu trong solr
        query.setQuery("content:" + str + " OR key:" + str);
        query.setStart(0);
        query.setRows(1);
        System.out.println(query);//q=content:+OR+key:&start=0&rows=1
        // truy van doi tuong solr va truy van key -> tra ve List
        QueryResponse response = solr.query(query);
        SolrDocumentList results = response.getResults();

        ArrayList<String> content = new ArrayList<>();

        for (SolrDocument doc : results) {
            //lay gia tri cua content
            content.addAll((Collection<? extends String>) doc.getFieldValue("content"));
        }
        solr.close();
        if (!content.isEmpty()) {
            return content.get(0);
        }
        return "";
    }
    public void solrIndexer(Document document) throws IOException, SolrServerException {
        SolrClient solr = new HttpSolrClient.Builder(solrUrl).build();

        // Tạo một SolrInputDocument để lưu trữ dữ liệu
        SolrInputDocument solrDoc = new SolrInputDocument();
        solrDoc.addField("id", document.getId()); // Định danh (ID) của tài liệu
        solrDoc.addField("content", document.getContent()); // Nội dung của tài liệu
        solrDoc.addField("key", document.getKey()); // Khóa (key) của tài liệu
        System.out.println(solrDoc);
        // Thêm SolrInputDocument vào Solr
        solr.add(solrDoc);
        solr.commit();
        solr.close();
    }
    public String deleteDocumentFromSolr(String id) {
        try (SolrClient solr = new HttpSolrClient.Builder(solrUrl).build()) {
            solr.deleteById(id);
            solr.commit();
            return "successfully";
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return "fail";
        }
    }

}
