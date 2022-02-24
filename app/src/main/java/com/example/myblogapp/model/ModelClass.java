package com.example.myblogapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelClass {
    private int id;
    private String Blogger_name;
    private String Article_Description;
    private String Article_Title;
    private Long Article_Date;
    private byte[] Article_Picture;
    //constructors
    public ModelClass() {
    }

    public ModelClass(int id, String blogger_name, String article_Description, String article_Title, Long article_Date, byte[] article_Picture) {
        this.id = id;
        Blogger_name = blogger_name;
        Article_Description = article_Description;
        Article_Title = article_Title;
        Article_Date = article_Date;
        Article_Picture = article_Picture;
    }
    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlogger_name() {
        return Blogger_name;
    }

    public void setBlogger_name(String blogger_name) {
        Blogger_name = blogger_name;
    }

    public String getArticle_Description() {
        return Article_Description;
    }

    public void setArticle_Description(String article_Description) {
        Article_Description = article_Description;
    }

    public String getArticle_Title() {
        return Article_Title;
    }

    public void setArticle_Title(String article_Title) {
        Article_Title = article_Title;
    }



    //ssss
    public Long getArticle_Date() {
        return Article_Date;
    }

    public void setArticle_Date(Long Article_Date) {
        this.Article_Date = Article_Date;
    }

    public String getStringDate() {
        Date date = new Date(getArticle_Date());
        // String pattern = "dd MMMM, yyyy HH:MM";
        String pattern = "dd MMMM, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String stringDate = simpleDateFormat.format(date);
        return stringDate;
    }
    //ssss end

    public byte[] getArticle_Picture() {
        return Article_Picture;
    }

    public void setArticle_Picture(byte[] article_Picture) {
        Article_Picture = article_Picture;
    }
}
