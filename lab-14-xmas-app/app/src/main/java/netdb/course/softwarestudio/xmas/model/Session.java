package netdb.course.softwarestudio.xmas.model;

import netdb.course.softwarestudio.xmas.rest.model.Resource;

public class Session extends Resource{

    private String fbAccessToken;
    private Long userId;

    public void setFbAccessToken(String fbAccessToken) {
        this.fbAccessToken = fbAccessToken;
    }

    public Long getUserId(){
        return userId;
    }

    public static String getCollectionName(){
        return "session";
    }

}
