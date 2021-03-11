package com.example.user.tidsnote;

import java.io.Serializable;

public class MenuFile implements Serializable {
    String fileName;
    byte[] pdfFile;

    public MenuFile(String fileName, byte[] pdfFile) {
        this.fileName = fileName;
        this.pdfFile = pdfFile;
    }


    public MenuFile(String fileName) {
        this.fileName = fileName;
        this.pdfFile = pdfFile;
    }

    public MenuFile(){

    }



    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(byte[] pdfFile) {
        this.pdfFile = pdfFile;
    }
}
