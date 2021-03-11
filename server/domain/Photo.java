package com.example.user.tidsnote;
import com.example.user.tidsnote.*;
import java.util.ArrayList;

public class Photo {
    
    ArrayList<byte[]> bytes;
    int rowid=0;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Photo(ArrayList<byte[]> bytes, int rowid) {
        this.bytes = bytes;
        this.rowid = rowid;
    }

    public ArrayList<byte[]> getBytes() {
        return bytes;
    }

    public void setBytes(ArrayList<byte[]> bytes) {
        this.bytes = bytes;
    }

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }
}
