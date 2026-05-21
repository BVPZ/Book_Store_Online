package com.bookshop.model;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeDocument {
    private String id;
    private String content;
    private String type; // "book", "author", "category", "faq"
    private List<Double> embedding;
    private double similarity;

    public KnowledgeDocument() {
        this.embedding = new ArrayList<>();
        this.similarity = 0.0;
    }

    public KnowledgeDocument(String id, String content, String type) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.embedding = new ArrayList<>();
        this.similarity = 0.0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    @Override
    public String toString() {
        return "KnowledgeDocument{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", similarity=" + similarity +
                '}';
    }
}
