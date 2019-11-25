package com.example.user.tidsnote;
import com.example.user.tidsnote.*;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

public class Notice implements Serializable {

    String writer, content,title;
    String t_id;
    Date date;
    ArrayList<byte[]> photos;
    int rowid;

    public Notice(String writer, String title,String content,Date date){
        this.writer=writer;
        this.title=title;
        this.content=content;
        this.date=date;
	this.photos=new ArrayList<>();

    }

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }


public ArrayList<byte[]> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<byte[]> photos) {
        this.photos = photos;
    }




    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public  Notice(){

    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getWriter(){
        return this.writer;
    }
    public String getContent(){
        return this.content;
    }

    public String getTitle(){return this.title;}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
