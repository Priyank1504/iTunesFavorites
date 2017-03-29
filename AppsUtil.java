package com.priyank.itunesfavoriteapp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/*
created by Priyank Verma
*/


public class AppsUtil {
    static public class AppJSONParser{
        static ArrayList<App> parseApp(String in) throws JSONException {
            ArrayList<App> appsList = new ArrayList<App>();

            JSONObject root = new JSONObject(in);
            JSONObject feed= root.getJSONObject("feed");
            JSONArray appsJSONArray = feed.getJSONArray("entry");

            for(int i=0; i<appsJSONArray.length(); i++) {
                JSONObject appJSONObject = appsJSONArray.getJSONObject(i);
                App app = App.createApp(appJSONObject);
                appsList.add(app);
            }



            return appsList;
        }
    }
}