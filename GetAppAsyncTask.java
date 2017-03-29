package com.priyank.itunesfavoriteapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


/*
created by Priyank Verma
*/


public class GetAppAsyncTask extends AsyncTask<String, Void, ArrayList<App>> {
    MainActivity activityMA;
    FavoritesActivity activityFA;


    public GetAppAsyncTask(MainActivity activity){
        this.activityMA=activity;
    }
    public GetAppAsyncTask(FavoritesActivity activity){
        this.activityFA=activity;
    }

    @Override
    protected ArrayList<App> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while(line!=null){
                    sb.append(line);
                    line=reader.readLine();
                }
                return AppsUtil.AppJSONParser.parseApp(sb.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<App> result) {
        super.onPostExecute(result);
        if(result!=null){
            Log.d("demo", result.toString());
        }
        if(activityFA!=null)
            activityFA.set(result);
        else if(activityMA!=null){
            activityMA.set(result);
        }
    }
}
