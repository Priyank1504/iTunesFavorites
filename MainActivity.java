package com.priyank.itunesfavoriteapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/*
created by Priyank Verma
*/

public class MainActivity extends AppCompatActivity implements GetImageAsyncTask.IData{

    public static final String MyPREFERENCES = "MyPrefs";

    ListView listView;
    ArrayList<App> appList;
    int index=0;
    AppAdapter adapter;
    ProgressBar pb;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("iTunes Top Paid Apps");
        listView = (ListView) findViewById(R.id.listview);
        new GetAppAsyncTask(this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
        pb=(ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.refresh:
                refresh();
                return true;
            case R.id.favorites:
                Intent i = new Intent(this, FavoritesActivity.class);
                startActivity(i);
                return true;
            case R.id.sortI:
                sortAscending();
                return true;
            case R.id.sortD:
                sortDescending();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void set(ArrayList<App> apps){
        appList = apps;
        fetchImage(appList.get(0).getUrl());
        View();
    }

    public void View(){
        pb.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        adapter = new AppAdapter(this, R.layout.row_item_layout, appList, false);
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

    public void sortAscending(){
        Collections.sort(appList, new Comparator<App>() {
            @Override
            public int compare(App lhs, App rhs) {
                return lhs.getPrice().compareTo(rhs.getPrice());
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void sortDescending(){
        Collections.sort(appList, new Comparator<App>() {
            @Override
            public int compare(App lhs, App rhs) {
                return rhs.getPrice().compareTo(lhs.getPrice());
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void refresh(){
        index=0;
        adapter.clear();
        pb.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
        new GetAppAsyncTask(this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");

    }

}
