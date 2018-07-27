package com.cc.admin.entity;

public class JiDian {
    private String avgGPA;
    private String avgScore;
    private String GPARankClass;
    private String GPARankSpecialty;

    @Override
    public String toString() {
        return "JiDian{" +
                "avgGPA='" + avgGPA + '\'' +
                ", avgScore='" + avgScore + '\'' +
                ", GPARankClass='" + GPARankClass + '\'' +
                ", GPARankSpecialty='" + GPARankSpecialty + '\'' +
                '}';
    }

    public JiDian() {
    }

    public JiDian(String avgGPA, String avgScore, String GPARankClass, String GPARankSpecialty) {
        this.avgGPA = avgGPA;
        this.avgScore = avgScore;
        this.GPARankClass = GPARankClass;
        this.GPARankSpecialty = GPARankSpecialty;
    }

    public String getAvgGPA() {
        return avgGPA;
    }

    public void setAvgGPA(String avgGPA) {
        this.avgGPA = avgGPA;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }

    public String getGPARankClass() {
        return GPARankClass;
    }

    public void setGPARankClass(String GPARankClass) {
        this.GPARankClass = GPARankClass;
    }

    public String getGPARankSpecialty() {
        return GPARankSpecialty;
    }

    public void setGPARankSpecialty(String GPARankSpecialty) {
        this.GPARankSpecialty = GPARankSpecialty;
    }
}
