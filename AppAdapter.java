package com.priyank.itunesfavoriteapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



/*
created by Priyank Verma
*/


public class AppAdapter extends ArrayAdapter<App> {
    List<App> mData;
    Context mContext;
    int mResource;
    App app;

    boolean fav;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean favorite;
    AlertDialog.Builder builder;
    public AppAdapter(Context context, int resource, List<App> objects, boolean fav){
        super(context, resource, objects);
        this.mContext=context;
        this.mData=objects;
        this.mResource=resource;
        favorite=fav;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        sharedPreferences = mContext.getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();

        app = mData.get(position);
        TextView appDisplay = (TextView) convertView.findViewById(R.id.app);
        appDisplay.setText(app.getName() + "\n" + "Price: USD " + app.getPrice());
        ImageView logo = (ImageView) convertView.findViewById(R.id.logo);
        final ImageView fav = (ImageView) convertView.findViewById(R.id.fav);
        fav.setTag(position);

        boolean f = sharedPreferences.getBoolean(app.getName(), false);

        if(f){
            fav.setImageDrawable(convertView.getResources().getDrawable(R.drawable.blackstar));
        }
        else{
            fav.setImageDrawable(convertView.getResources().getDrawable(R.drawable.whitestar));
        }

        if(app.getResult()!=null)
            logo.setImageBitmap(app.getResult());
        else{
            logo.setImageBitmap(null);
        }

        final View finalConvertView = convertView;
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean f = sharedPreferences.getBoolean(mData.get(position).getName(), false);
                if(f) {
                    builder = new AlertDialog.Builder(AppAdapter.super.getContext());
                    builder.setTitle("Add to Favourites")
                            .setMessage("Are you sure you want to remove this App from favourites?")
                            .setCancelable(false)
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(AppAdapter.super.getContext(), "App still in your favourites", Toast.LENGTH_SHORT).show();

                                }
                            });
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(AppAdapter.super.getContext(), "App is removed from your favourites", Toast.LENGTH_SHORT).show();
                            editor.putBoolean(mData.get(position).getName(), false);
                            editor.apply();
                            notifyDataSetChanged();

                        }
                    });
                    final AlertDialog alert=builder.create();
                    alert.show();
                    editor.commit();
                } else {
                    builder = new AlertDialog.Builder(AppAdapter.super.getContext());
                    builder.setTitle("Add to Favourites")
                            .setMessage("Are you sure you want to add this App to favourites?")
                            .setCancelable(false)
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(AppAdapter.super.getContext(), "App Not added", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(AppAdapter.super.getContext(), "App added", Toast.LENGTH_SHORT).show();
                            editor.putBoolean(mData.get(position).getName(), true);
                            editor.apply();
                            notifyDataSetChanged();
                        }
                    });
                    final AlertDialog alert = builder.create();
                    alert.show();
                    editor.commit();
                }

            }
        });


        return convertView;
    }

}