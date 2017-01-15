package com.example.joginderpal.ngo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class RecyclerAdapterthree   extends RecyclerView.Adapter<RecyclerAdapterthree.ViewHolder> {
    int j=0;
    List<String> list,list2,list4;
    String message="";
    List<String> items;
    Context ctx;
    String[] option={"Media Player","Youtube","Add To Favourites"};
    ArrayList<File> mySongs;
    public RecyclerAdapterthree(Context ctx, List<String> items, ArrayList<File> mySongs) {
      //  this.list2=list2;
      //  this.list=list;
      //  this.list4=list4;
        this.items=items;
        this.ctx=ctx;
        this.mySongs=mySongs;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private int currentitem;
        public TextView itemTitle;
        public TextView itemTitle1;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle= (TextView) itemView.findViewById(R.id.tx3);
           // itemTitle1= (TextView) itemView.findViewById(R.id.tx1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        final int position=getAdapterPosition();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setTitle("Choose Option")

                            .setItems(option, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch(which){

                                        case 1:
                                            message=items.get(position);
                                          new LongOperation().execute("");
                                            break;
                                        case 0:

                                           ctx.startActivity(new Intent(ctx,Player.class).putExtra("pos",position).putExtra("songlist",mySongs));


                                            break;

                                        case 2:
                                          //  Intent intent=new Intent(ctx,Favourites.class);
                                          //  intent.putExtra("items",ite)
                                          //  ctx.startActivity(intent);
                                            break;




                                    }
                                }
                            });
                    AlertDialog alertDialog =builder.create();
                    alertDialog.show();





                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_two,parent,false);
        RecyclerView.ViewHolder v=new ViewHolder(view);



        return (ViewHolder) v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemTitle.setText(items.get(position));
       // holder.itemTitle1.setText(list2.get(position));
        // for (int i=1;i<list.size();i++){
        //      holder.itemTitle.setText(list.get(i));
        //  }

        //  Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.bounce1);
        //  holder.itemView.startAnimation(animation);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    private class LongOperation extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... strings) {
            String string ="http://www.youtube.com/watch_videos?video_ids=";
            String a="";
            try {
                a+=link(message)+",";
            } catch (IOException e) {
                e.printStackTrace();
            }
            string+=a;
            return string;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(s));
            ctx.startActivity(i);
          //  Intent i=new Intent(ctx,Youtube.class);
           // i.putExtra("id",s);
            //ctx.startActivity(i);
        }
    }

    public static String link(String name) throws IOException{
        String url="https://www.youtube.com/results?search_query="+name;
        Document doc= Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36").get();
        Element list=doc.select("h3.yt-lockup-title > a").first();
        String ok=list.attr("href");
        //return ok.substring(9);
        return ok.replace("/watch?v=","");
    }

}

