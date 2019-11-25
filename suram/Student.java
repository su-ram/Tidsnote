package com.example.user.tidsnote;
import com.example.user.tidsnote.*;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Date;


public class Student implements Serializable {
    public void setStatus(int[] status) {
        this.status = status;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    int pos,gender, age;
    int[] status = new int[3];
    String student_name, student_id,comment,parent_name,parent_message,parent_request,common,phone,class_id;
    boolean done=false;
    Date date;
    ArrayList<String> stt_message;
 	
    
    Student(){
        this.status[0]=-1;
        this.status[1]=-1;
        this.status[2]=-1;
        this.comment="comment";
        this.parent_message="";
        this.stt_message=new ArrayList<>();
        this.common="";
        this.parent_request="";
        this.parent_name="parent ";
        this.student_name="student";

    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPos(int pos){
        this.pos=pos;
    }
    public int getPos(){
        return pos;
    }

    public int[] getStatus(){
        return this.status;
    }

    public void setStatus(int h, int m, int f){
        this.status[0]=h;
        this.status[1]=m;
        this.status[2]=f;
    }
    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getParent_message() {
        return parent_message;
    }

    public void setParent_message(String parent_message) {
        this.parent_message = parent_message;
    }


    public String showLast(){
        return stt_message.get(stt_message.size()-1);
    }

    public String getParent_request() {
        return parent_request;
    }

    public void setParent_request(String parent_request) {
        this.parent_request = parent_request;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void add_Common(String str){
        this.comment=this.comment+"\n\n[공통사항]\n"+str;
    }

public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

	public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }


     public ArrayList<String> getStt_message() {
        return stt_message;
    }

    public void setStt_message(ArrayList<String> stt_message) {
        this.stt_message = stt_message;
    }

    public void addStt_message(String str){
        this.stt_message.add(str);
    }
    public boolean isWriting(){


        if (this.done){
            return false;
        }else{

            if (comment.equals("") && comment.equals("comment") && status[0] == -1 && status[1] == -1 && status[2] == -1){
                //아예 미입력
                return false;
            }else{
                return true;
            }
        }
}
}	
