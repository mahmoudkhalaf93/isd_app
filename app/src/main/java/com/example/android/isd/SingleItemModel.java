package com.example.android.isd;

/**
 * Created by modye on 3/13/2018.
 */

public class SingleItemModel {

    private String name;
    private String id;
    private String url;
    private String description;

    public SingleItemModel() { }

    public SingleItemModel(String name, String url,String id) {
        this.name = name;
        this.url = url;
        this.id=id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}