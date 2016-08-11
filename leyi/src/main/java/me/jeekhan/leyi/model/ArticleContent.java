package me.jeekhan.leyi.model;

import javax.validation.constraints.Size;

public class ArticleContent {
    private Integer articleId;
    
    @Size(max=10240)
    private String content;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}