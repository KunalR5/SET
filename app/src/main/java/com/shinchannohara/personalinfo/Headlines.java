package com.shinchannohara.personalinfo;

import java.util.List;

public class Headlines {

    private String status;
    private String totalResults;
    private List<Articles> articles;

    public Headlines(String status, String totalResults, List<Articles> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public List<Articles> getArticles() {
        return articles;
    }

}
