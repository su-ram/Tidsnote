package com.example.user.tidsnote;

public class studentData {


    public String stdName;
    public String stdClass;
    public String stdAge;
    public String stdPnum;
    public String stdMemo;

    public studentData(String stdName, String stdClass, String stdAge, String stdPnum, String stdMemo) {
        this.stdName = stdName;
        this.stdClass = stdClass;
        this.stdAge = stdAge;
        this.stdPnum = stdPnum;
        this.stdMemo = stdMemo;

    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getStdClass() {
        return stdClass;
    }

    public void setStdClass(String stdClass) {
        this.stdClass = stdClass;
    }

    public String getStdAge() {
        return stdAge;
    }

    public void setStdAge(String stdAge) {
        this.stdAge = stdAge;
    }

    public String getStdPnum() {
        return stdPnum;
    }

    public void setStdPnum(String stdPnum) {
        this.stdPnum = stdPnum;
    }

    public String getStdMemo() {
        return stdMemo;
    }

    public void setStdMemo(String stdMemo) {
        this.stdMemo = stdMemo;
    }
}
