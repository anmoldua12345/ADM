package com.example.joginderpal.ngo;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joginderpal on 05-01-2017.
 */
public class Fragmentone   extends Fragment {

List<String> list,list2,list3,list4;
    TextView tx;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;


    public Fragmentone() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_one, container, false);
      //  tx= (TextView) v.findViewById(R.id.text3);
        recyclerView= (RecyclerView) v.findViewById(R.id.rvactivitymain);
        new doit().execute();
        return v;
    }

    public class doit extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("loading");
                progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {




            try {
                list=new ArrayList<String>();
                list2=new ArrayList<String>();
                list3=new ArrayList<String>();
                list4=new ArrayList<String>();
               Document doc = Jsoup.connect("http://mr-johal.com/topTracks.php?cat=English").timeout(30000).get();
             //   Document doc = Jsoup.connect("https://mr-jatt.com/punjabisong-top20-singletracks.html").get();
                Elements a=doc.getElementsByTag("a");
                for (Element a1:a) {
                    String cla = a1.attr("class");
                    if (cla.equals("touch")) {
                        String text = a1.text();
                        String href=a1.attr("href");
                        Elements font=a1.getElementsByTag("font");
                        String lis1=font.text();
                        list2.add(lis1);
                        list.add(text);
                        list4.add(href);
                    }

                }
                for (int i=0;i<list.size();i++){
                    String s=list.get(i).replace(list2.get(i),"");
                    String s1=s.replace(i+".","");
                    list3.add(s1);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            layoutManager=new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter=new RecyclerAdapter(list3,list2,list4,getActivity());
            recyclerView.setAdapter(adapter);
        }
    }

}