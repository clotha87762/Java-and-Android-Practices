package netdb.course.softwarestudio.netdbtalk.model;

import java.sql.Date;

import netdb.course.softwarestudio.netdbtalk.service.rest.model.Resource;

public class Message extends Resource {

    private String user;
    private String content;
    private Date stamp;

    public static String getCollectionName() {
        return "messages";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }




}
