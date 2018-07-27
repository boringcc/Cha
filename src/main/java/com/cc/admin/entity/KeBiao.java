package com.cc.admin.entity;

public class KeBiao {

    private String time;
    private String className;
    private String teacher;
    private String week;
    private String place;

    public KeBiao(String time, String className, String teacher, String week, String place) {
        this.time = time;
        this.className = className;
        this.teacher = teacher;
        this.week = week;
        this.place = place;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    @Override
    public String toString() {
        String result = "";
        result=result+ getTime()+"," + getClassName() +"," +getTeacher() +"," + getWeek()+"," + getPlace() +"!";
        return result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
