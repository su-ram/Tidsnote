package com.example.user.tidsnote;
import com.example.user.tidsnote.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;
public class Request implements Serializable{

    String stu_name, par_name, content,stu_id,request_id;
    Date date;
    byte[] photo;

    boolean checked=false;

    Request (){
        
    }

    public Request(String sname,String pname,String text){
        //SimpleDateFormat s = new SimpleDateFormat("yyyy/dd/MM hh:ss");
        //String curDate = s.format(new Date());
        this.stu_name=sname;
        this.par_name=pname;
        this.content=text;
        date=new Date();
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getPar_name() {
        return par_name;
    }

    public void setPar_name(String par_name) {
        this.par_name = par_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
