package com.hangzhou.novaldev.model;

import lombok.Data;

@Data
public class SearchBean {
    private String id;
    private String title;
    private String cat;
    private String author;
    private String cover;
    private String shortIntro;
    private String lastChapter;
    private String wordTotal;
    private String updateDate;
    private String updateStatus;

    public SearchBean() {
    }
}
