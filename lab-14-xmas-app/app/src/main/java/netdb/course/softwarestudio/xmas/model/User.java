package netdb.course.softwarestudio.xmas.model;

import netdb.course.softwarestudio.xmas.rest.model.Resource;

public class User extends Resource{

    private String name;
    private String fbId;
    private String fbAccessToken;

    public static String getCollectionName(){
        return "users";
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setFbAccessToken(String fbAccessToken) {
        this.fbAccessToken = fbAccessToken;
    }
}
