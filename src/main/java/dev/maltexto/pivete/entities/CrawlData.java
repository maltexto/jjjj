package dev.maltexto.pivete.entities;

import com.google.gson.annotations.Expose;

import dev.maltexto.pivete.entities.enums.CrawlStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrawlData {

    @Expose
    private String id;

    private String keyword;

    @Expose
    private CrawlStatus crawlResultStatus;

    @Expose
    private List<String> urls;

    private String generateID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public boolean addURL(String url) {
        return urls.add(url);
    }

    public CrawlData(String keyword) {
        this.id = generateID();
        this.crawlResultStatus = CrawlStatus.ACTIVE;
        this.urls = new ArrayList<>();
        this.keyword = keyword;
    }

    public String getId() {
        return id;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setStatus(CrawlStatus crawlResultStatus) {
        this.crawlResultStatus = crawlResultStatus;
    }

    public CrawlStatus getStatus() {
        return crawlResultStatus;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}