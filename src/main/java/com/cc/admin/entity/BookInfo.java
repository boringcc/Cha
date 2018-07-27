package com.cc.admin.entity;

public class BookInfo {
    private String id;       //序号
    private String bookName; //书名
    private String author;   //作者
    private String pub;      //出版社
    private String barCode;  //图书条码，靠这个去搜索详细信息
    private String bookCode; //索书号,在哪个书架上
    private String place;    //藏书的部门
    private String statu;    //流通状态
    private String date;     //还书日期

    @Override
    public String toString() {
        return "BookInfo{" +
                "id='" + id + '\'' +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", pub='" + pub + '\'' +
                ", barCode='" + barCode + '\'' +
                ", bookCode='" + bookCode + '\'' +
                ", place='" + place + '\'' +
                ", statu='" + statu + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String xxBookInfo(){
        return place+", "+bookCode+", "+statu+"! ";
    }


    public BookInfo() {
    }

    public BookInfo(String id, String bookName, String author, String pub, String barCode, String bookCode, String place, String statu, String date) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.pub = pub;
        this.barCode = barCode;
        this.bookCode = bookCode;
        this.place = place;
        this.statu = statu;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
