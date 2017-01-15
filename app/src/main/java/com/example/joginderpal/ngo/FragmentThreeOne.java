package com.example.joginderpal.ngo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by joginderpal on 06-01-2017.
 */
public class FragmentThreeOne extends Activity {

    ImageView img;
    Button b1;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_three_one);
        img= (ImageView) findViewById(R.id.imageView3);
        b1= (Button) findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String link=getIntent().getExtras().getString("link");
                new doit().execute(link);
            }
        });
    }


    public class doit extends AsyncTask<String,Integer,Void>{

        String link1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(FragmentThreeOne.this);
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
                 link1=getIntent().getExtras().getString("link1");
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
            Picasso.with(getApplicationContext()).load(link1).into(img);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
            //  waveProgressbar.setCurrent(values[0],values[0].toString());
        }
    }
}
