package com.example.youngeun.parentapp;

import java.io.Serializable;

public class Comment implements Serializable {

    String s_id;
    public String s_name;
    public String c_date;
    public String c_name;
    public String t_name;
    public String c_feel;
    public String c_meal;
    public String c_health;
    public String m_menu;
    public String c_comment;
    public String c_same;

    public Comment(String s_id,String c_date) {
        this.s_id=s_id;
        this.s_name = s_name;
        this.c_date = c_date;
        this.c_name = c_name;
        this.t_name = t_name;
        this.c_feel = c_feel;
        this.c_meal = c_meal;
        this.c_health = c_health;
        this.m_menu = m_menu;
        this.c_comment = c_comment;
        this.c_same = c_same;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getC_feel() {
        return c_feel;
    }

    public void setC_feel(String c_feel) {
        this.c_feel = c_feel;
    }

    public String getC_meal() {
        return c_meal;
    }

    public void setC_meal(String c_meal) {
        this.c_meal = c_meal;
    }

    public String getC_health() {
        return c_health;
    }

    public void setC_health(String c_health) {
        this.c_health = c_health;
    }

    public String getM_menu() {
        return m_menu;
    }

    public void setM_menu(String m_menu) {
        this.m_menu = m_menu;
    }

    public String getC_comment() {
        return c_comment;
    }

    public void setC_comment(String c_comment) {
        this.c_comment = c_comment;
    }

    public String getC_same() {
        return c_same;
    }

    public void setC_same(String c_same) {
        this.c_same = c_same;
    }
}
