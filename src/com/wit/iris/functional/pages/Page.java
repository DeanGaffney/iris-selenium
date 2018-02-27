package com.wit.iris.functional.pages;

import com.codeborne.selenide.Condition;
import com.google.gson.JsonObject;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by dean on 12/12/17.
 */
public class Page {

    public static final String GUITAR_NAME = "Matt Heafy 6-String";
    
    protected double price;
    protected String siteName;

    public Page(String identifier, Condition condition, Wait waitType){
        assert identify(identifier, condition, waitType);
    }

    public boolean identify(String cssSelector, Condition condition, Wait waitType){
        return $(cssSelector).waitUntil(condition, waitType.getTime()).exists();
    }
    
    public void setPrice(double price) {
    	this.price = price;
    }
    
    public double getPrice() {
    	return this.price;
    }
    
    public void setSiteName(String name) {
    	this.siteName = name;
    }
    
    public String getSiteName() {
    	return this.siteName;
    }
    
    public String getJson() {
    	JsonObject json = new JsonObject();
    	json.addProperty("site", getSiteName());
    	json.addProperty("price", getPrice());
    	return json.toString();
    }
}