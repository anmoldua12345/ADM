package com.example.joginderpal.ngo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joginderpal on 06-01-2017.
 */
public class Sdcard extends Activity {

    String[] items;
    String[] option={"Media Player","Youtube"};
    String message="";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<String> li1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdcard);
        li1=new ArrayList<String>();
        final ArrayList<File> mySongs=findroot(Environment.getExternalStorageDirectory());
        items=new String[mySongs.size()];
        for (int i=0;i<mySongs.size();i++){
            //   toast(mySongs.get(i).getName().toString());
            items[i]=(mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav",""));
            li1.add(items[i]);
        }
        recyclerView= (RecyclerView) findViewById(R.id.rvactivitymainthree);
        layoutManager=new LinearLayoutManager(Sdcard.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecyclerAdapterthree(Sdcard.this,li1,mySongs);
        recyclerView.setAdapter(adapter);
    }
    public ArrayList<File> findroot (File root){
        ArrayList<File> al=new ArrayList<File>();
        File[] files=root.listFiles();
        for (File singlefile : files){
            if (singlefile.isDirectory() && !singlefile.isHidden()){
                al.addAll(findroot(singlefile));
            }
            else{
                if (singlefile.getName().endsWith(".mp3")|| singlefile.getName().endsWith(".wav")){
                    al.add(singlefile);
                }
            }
        }
        return al;
    }
    public void toast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }



}



