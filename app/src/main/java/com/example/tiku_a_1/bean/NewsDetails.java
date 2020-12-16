package com.example.tiku_a_1.bean;

public class NewsDetails {

    /**
     * newsid : 1
     * publicTime : 2020-10-01 06:00:00
     * subject : 国庆专题
     * recommand : 1
     * praiseCount : 500
     * audienceCount : 600
     */

    private String newsid;
    private String publicTime;
    private String subject;
    private int recommand;
    private int praiseCount;
    private String audienceCount;

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getRecommand() {
        return recommand;
    }

    public void setRecommand(int recommand) {
        this.recommand = recommand;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(String audienceCount) {
        this.audienceCount = audienceCount;
    }
}
