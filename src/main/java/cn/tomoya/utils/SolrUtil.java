package cn.tomoya.utils;

import cn.tomoya.module.topic.Topic;
import cn.tomoya.module.topic.TopicAppend;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class SolrUtil {

    static {
        PropKit.use("config.properties");
    }

    private final static String URL = PropKit.get("solr.url");
    private final static SolrClient client = new HttpSolrClient(URL);

    /**
     * 将所有的topic都索引
     *
     * @return
     */
    public boolean indexAll() {
        try {
            List<Topic> topics = Topic.me.findAll();
            List<SolrInputDocument> docs = new ArrayList<>();
            for (Topic topic : topics) {
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("id", topic.getInt("id"));
                doc.addField("title", topic.getStr("title"));
                doc.addField("in_time", topic.getDate("in_time"));
                //话题内容与追加内容拼接作为一个整体索引
                StringBuffer content = new StringBuffer(topic.getStr("content"));
                for (TopicAppend ta : (List<TopicAppend>) topic.get("topicAppends")) {
                    content.append("\n")//换行
                            .append(ta.getStr("content"));
                }
                doc.addField("content", content.toString());
                docs.add(doc);
            }
            client.add(docs);
            client.commit();
            return true;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 索引单个话题
     *
     * @param topic
     * @return
     */
    public boolean indexTopic(Topic topic) {
        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", topic.getInt("id"));
            doc.addField("title", topic.getStr("title"));
            doc.addField("in_time", topic.getDate("in_time"));
            List<TopicAppend> topicAppends = TopicAppend.me.findByTid(topic.getInt("id"));
            StringBuffer content = new StringBuffer(topic.getStr("content"));
            for(TopicAppend ta: topicAppends) {
                content.append("\n")//换行
                        .append(ta.getStr("content"));
            }
            doc.addField("content", content.toString());
            client.add(doc);
            client.commit();
            return true;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据
     *
     * @param pageNumber
     * @param q
     * @return
     */
    public Page indexQuery(Integer pageNumber, String q) {
        try {
            String URL = PropKit.get("solr.url");
            Integer pageSize = PropKit.getInt("solr.pageSize");
            SolrClient solrClient = new HttpSolrClient(URL);
            SolrQuery query = new SolrQuery(q);
            query.setStart((pageNumber - 1) * pageSize);
            query.setRows(pageSize);
            query.setSort("in_time", SolrQuery.ORDER.desc);
            query.setHighlight(true);
            query.addHighlightField("title");
            query.addHighlightField("content");
            query.setHighlightSimplePre("<font color='red'><b>");
            query.setHighlightSimplePost("</b></font>");
            query.setHighlightSnippets(1);
            query.setHighlightFragsize(150);
            QueryResponse res = solrClient.query(query);
            Map<String, Map<String, List<String>>> highlightMap = res.getHighlighting();
            SolrDocumentList docs = res.getResults();
            List<Topic> list = new ArrayList<>();
            for (SolrDocument doc : docs) {
                Topic topic = new Topic();
                String id = doc.getFieldValue("id").toString();
                topic.set("id", id);
                List<String> titleList = highlightMap.get(id).get("title");
                if (titleList != null && titleList.size() > 0) {
                    topic.set("title", titleList.get(0));
                } else {
                    topic.set("title", doc.getFieldValueMap().get("title"));
                }
                List<String> contentList = highlightMap.get(id).get("content");
                if (contentList != null && contentList.size() > 0) {
                    topic.set("content", Jsoup.clean(contentList.get(0) + "...", Whitelist.none().addTags("b").addAttributes("font", "color")));
                }
                list.add(topic);
            }
            int totalCount = (int) docs.getNumFound();
            int totalPage = totalCount / pageSize;
            if (totalCount % pageSize != 0) totalPage++;
            Page page = new Page(list, pageNumber, pageSize, totalPage, totalCount);
            return page;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除索引
     * @param id
     */
    public void indexDelete(String id) {
        try {
            client.deleteById(id);
            client.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            client.deleteByQuery("*");
            client.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
