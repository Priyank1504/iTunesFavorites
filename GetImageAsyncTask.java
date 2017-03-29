package com.priyank.itunesfavoriteapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/*
created by Priyank Verma
*/


public class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    FavoritesActivity activityFA;
    MainActivity activityMA;

    public GetImageAsyncTask(FavoritesActivity activity){
        activityFA=activity;
    }
    public GetImageAsyncTask(MainActivity activity){
        activityMA=activity;
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        InputStream in = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET"); //on.setRequestMethod("POST")
            in = con.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
            return image;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(activityFA!=null)
            activityFA.displayImage(result);
        else if(activityMA!=null){
            activityMA.displayImage(result);
        }
    }

    static public interface IData{
        //public Context getContext();
        public void displayImage(Bitmap result);
    }

    static public interface IData2{
        public void displayImage(Bitmap result);
    }

}