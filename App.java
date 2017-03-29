package com.priyank.itunesfavoriteapp;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/*
created by Priyank Verma
*/

public class App {
    String name, url;
    double price;
    boolean favorite;

    public Bitmap getResult() {
        return result;
    }

    public void setResult(Bitmap result) {
        this.result = result;
    }

    Bitmap result;

    static public App createApp(JSONObject js) throws JSONException{
        App app = new App();
        app.setName(js.getJSONObject("title").getString("label"));
        app.setUrl(js.getJSONArray("im:image").getJSONObject(2).getString("label"));
        app.setPrice(js.getJSONObject("im:price").getJSONObject("attributes").getDouble("amount"));
        return  app;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price+"";
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return
                "name='" + name + '\'' +
                        ", url='" + url + '\'' +
                        ", price=" + price +
                        ", favorite=" + favorite +
                        '}';
    }
}
