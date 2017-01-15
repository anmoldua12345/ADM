package com.example.joginderpal.ngo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by joginderpal on 05-01-2017.
 */
public class Fragmentonetwo extends Activity {

    ImageView imageView;
    List<String> li;
    String href;
    List<String> li1,li2;
    Button download;
    ProgressDialog progressDialog,progressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_one_two);
        imageView= (ImageView) findViewById(R.id.imageView2);
        download= (Button) findViewById(R.id.button);
        href=getIntent().getExtras().getString("link");
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new download().execute();
            }
        });
        new doit().execute();
    }

    public class doit extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog1=new ProgressDialog(Fragmentonetwo.this);
            progressDialog1.setMessage("Loading...");
            progressDialog1.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                li=new ArrayList<String>();
                Document doc= Jsoup.connect(href).timeout(30000).get();
                Elements div=doc.getElementsByTag("div");
                for (Element div1:div){
                    String cla=div1.attr("class");
                    if (cla.equals("albumCoverSmall")){
                        Elements img=div1.getElementsByTag("img");
                        for (Element img1:img){
                            String hr=img1.attr("src");
                            li.add(hr);
                        }

                    }
                }




            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Picasso.with(getApplicationContext()).load(li.get(0)).into(imageView);
            progressDialog1.dismiss();
        }
    }

    public class download extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                li2=new ArrayList<String>();
                li1=new ArrayList<String>();
                Document doc= Jsoup.connect(href).timeout(20000).get();
                Elements a=doc.getElementsByTag("a");
                for (Element a1: a){
                    String cl=a1.attr("class");
                    if (cl.equals("touch")){
                       String l=a1.text();
                        String l1=a1.attr("href");
                   li1.add(l);
                        li2.add(l1);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

          String[] array=new String[li1.size()];
            for (int i=0;i<li1.size();i++){
                array[i]=li1.get(i);
            }


            AlertDialog.Builder builder = new AlertDialog.Builder(Fragmentonetwo.this);
            builder.setTitle("Download")
                    .setItems(array, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            for (int i=0;i<li1.size();i++){
                                if (which==i){
                                    new downloadone().execute(li2.get(which));
                                    //Toast.makeText(getApplicationContext(),i,Toast.LENGTH_LONG).show();

                                }
                            }

                        }
                    });

            AlertDialog alertDialog=builder.create();
            alertDialog.show();


        }
    }


    public class downloadone extends AsyncTask<String,Integer,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


      //      waveProgressbar= (WaveProgressView) findViewById(R.id.waveProgressbar);

           // 77, "788M/1024M"
        //    waveProgressbar.setMaxProgress(100);
          //  waveProgressbar.setText("#FFFF00",41);//"#FFFF00", 41
          //  waveProgressbar.setWaveColor("#5b9ef4"); //"#5b9ef4"

//            waveProgressbar.setWave(10,40);
 //           waveProgressbar.setmWaveSpeed(30);//The larger the value, the slower the vibration


            progressDialog=new ProgressDialog(Fragmentonetwo.this);
            progressDialog.setTitle("Song is downloading......");
            progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(0);
            progressDialog.setProgress(0);
            progressDialog.show();


        }

        @Override
        protected Void doInBackground(String... voids) {

            String url=voids[0];


            try {
                URL myurl=new URL(url);
                HttpURLConnection connection= (HttpURLConnection) myurl.openConnection();
                connection.setDoOutput(true);
                int file_length=connection.getContentLength();
                connection.setRequestMethod("GET");
                connection.connect();
                File rootDirectory=new File(Environment.getExternalStorageDirectory(),"NGO");
                if (!rootDirectory.exists()){
                    rootDirectory.mkdir();
                }

                String nameOfFile= URLUtil.guessFileName(url,null,
                        MimeTypeMap.getFileExtensionFromUrl(url));
                File file=new File(rootDirectory,nameOfFile);
                file.createNewFile();

                InputStream inputStream=connection.getInputStream();
                FileOutputStream output=new FileOutputStream(file);
                byte[] buffer=new byte[1024];
                int byteCount=0;
                int total=0;
                while((byteCount=inputStream.read(buffer))>0){
                    total+=byteCount;
                    output.write(buffer,0,byteCount);
                      int progress=total*100/file_length;
                      publishProgress(progress);
                }
                output.close();

                Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file));
                getApplicationContext().sendBroadcast(intent);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }








            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            Toast.makeText(getApplicationContext(),"Done ...",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
          //  waveProgressbar.setCurrent(values[0],values[0].toString());
        }
    }



}
