package com.example.user.tidsnote;

public class postData {
    public String postTitle;
    public String postDate;
    public String postWrite;

    public postData(String postTitle, String postDate, String postWrite) {
        this.postTitle = postTitle;
        this.postDate = postDate;
        this.postWrite = postWrite;
    }

    public String getPostTitle() {

        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostWrite() {
        return postWrite;
    }

    public void setPostWrite(String postWrite) {
        this.postWrite = postWrite;
    }
}
