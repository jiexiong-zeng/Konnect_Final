package com.disaintworking.konnect;

/**
 * Created by Swaggiott on 26/6/2015.
 */
public class People {
    public String name;
    public String number;
    public String URI;

    public People(String name, String number, String URI) {
        this.name = name;
        this.number = number;
        this.URI = URI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
