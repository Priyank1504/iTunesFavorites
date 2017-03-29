package com.priyank.itunesfavoriteapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    Button Finish;
    public static final String MyPREFERENCES = "MyPrefs";

    ListView listView;
    ArrayList<App> appList;
    ArrayList<App> favoriteList;
    int index=0;
    AppAdapter adapter;
    ProgressBar pb;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        listView = (ListView) findViewById(R.id.listview);
        favoriteList = new ArrayList<App>();
        new GetAppAsyncTask(this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
        pb=(ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Finish=(Button) findViewById(R.id.buttonFinish);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FavoritesActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }

    public void set(ArrayList<App> apps){
        appList = apps;
        genFav();
        fetchImage(appList.get(0).getUrl());
        View();
    }

    public void View(){
        pb.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        adapter = new AppAdapter(this, R.layout.row_item_layout, favoriteList, false);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    public void displayImage(Bitmap result){
        appList.get(index).setResult(result);
        adapter.notifyDataSetChanged();
        if(index<appList.size()-1){
            index++;
            fetchImage(appList.get(index).getUrl());
        }
    }



    public void fetchImage(String url){
        new GetImageAsyncTask(this).execute(url);
    }

    public void genFav(){
        favoriteList.clear();
        for(int p=0; p<appList.size(); p++){
            boolean f = sharedpreferences.getBoolean(appList.get(p).getName(), false);
            if(f)
                favoriteList.add(appList.get(p));

        }
    }




}
