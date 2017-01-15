package com.example.joginderpal.ngo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by joginderpal on 05-01-2017.
 */
public class RecyclerAdaptertwo   extends RecyclerView.Adapter<RecyclerAdaptertwo.ViewHolder> {
    int j=0;
    List<String> list,list2,list4;
    Context ctx;

    public RecyclerAdaptertwo(List<String> list, List<String> list2, List<String> list4, Context ctx) {
        this.list2=list2;
        this.list=list;
        this.list4=list4;
        this.ctx=ctx;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private int currentitem;
        public TextView itemTitle;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle= (TextView) itemView.findViewById(R.id.tx2);
           img= (ImageView) itemView.findViewById(R.id.ima);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                   Intent i=new Intent(ctx,FragmentThreeOne.class);
                    i.putExtra("link",list4.get(position));
                    i.putExtra("link1",list2.get(position));
                    ctx.startActivity(i);

                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_three,parent,false);
        RecyclerView.ViewHolder v=new ViewHolder(view);



        return (ViewHolder) v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemTitle.setText(list.get(position));
        Picasso.with(ctx).load(list2.get(position)).into(holder.img);
        // for (int i=1;i<list.size();i++){
        //      holder.itemTitle.setText(list.get(i));
        //  }

        //  Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.bounce1);
        //  holder.itemView.startAnimation(animation);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}

