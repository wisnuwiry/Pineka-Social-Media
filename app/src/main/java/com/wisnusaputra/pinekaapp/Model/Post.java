package com.wisnusaputra.pinekaapp.Model;

public class Post {
    private String postid;
    private String postimage;
    private String description;
    private String category;
    private String publisher;


    public Post(String postid, String postimage, String description, String category, String publisher) {
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.category = category;
        this.publisher = publisher;
    }

    public Post(){

    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
