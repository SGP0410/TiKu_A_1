package com.example.tiku_a_1.bean;

public class News {

    /**
     * newsid : 11
     * newsType : 疫情
     * picture : http://192.168.155.202:8080/mobileA/images/11.jpg
     * abstract : 德国联邦议院一年一度就财政预算展开的一般性辩论历来都是德国政坛的重头戏。但今年的辩论重点毫不意外地变成了新冠疫情以及德国政府的防范措施。面对反对党的责难，德国总理默克尔当地时间30日在辩论中强调，德国迄今为止的防疫成果令人满意。但她同时警告，各方都不应放松警惕。
     * title : 默克尔宣布取消统一30周年国庆庆祝活动 呼吁德国民众重新重视疫情
     * url : https://baijiahao.baidu.com/s?id=1679303124270764876&wfr=spider&for=pc
     */

    private String newsid;
    private String newsType;
    private String picture;
    @com.google.gson.annotations.SerializedName("abstract")
    private String abstractX;
    private String title;
    private String url;

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
