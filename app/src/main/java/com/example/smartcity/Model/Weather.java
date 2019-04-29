package com.example.smartcity.Model;

public class Weather {

    private long id ;
    private String main ;
    private String description ;
    private String icon ;

    public Weather(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        icon = icon;
    }
}
