package com.armuzzelectro.myprofileinformation;

public class LinkModel {
    private String name;
    private final String url;

    public LinkModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public void setUrl(String url) {
        this.url = url;
    }

     */
}
