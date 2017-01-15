package com.example.joginderpal.ngo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joginderpal on 05-01-2017.
 */
public class Fragmentthree extends Fragment {
    TextView tx;
    Button b1;
    RequestQueue requestQueue;
    List<String> list1,list2,list3;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;


    public Fragmentthree() {
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
        View v= inflater.inflate(R.layout.fragment_three, container, false);
      recyclerView= (RecyclerView) v.findViewById(R.id.rvactivitymaintwo);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,"https://freemusicarchive.org/recent.json?api_key=O3HSHXY1B7ELGLH0", null,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String title = null;
                                try {
                                    list1=new ArrayList<String>();
                                    list2=new ArrayList<String>();
                                    list3=new ArrayList<String>();
                                  // String auth=response.getString("bManage");
                                  // tx.setText(auth);
                                    JSONArray array=response.getJSONArray("aTracks");
                                    for (int i=0;i<array.length();i++){
                                  JSONObject object=array.getJSONObject(i);
                                        title=object.getString("track_title");
                                        list1.add(title);
                                        String image=object.getString("track_image_file");
                                        list2.add(image);
                                        String tracklisten=object.getString("track_file_url");
                                        list3.add(tracklisten);
                                     }

                                    layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
                                    recyclerView.setLayoutManager(layoutManager);
                                    adapter=new RecyclerAdaptertwo(list1,list2,list3,getActivity());
                                    recyclerView.setAdapter(adapter);



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {



                            }
                          }


                );
                requestQueue.add(jsonObjectRequest);




       return v;
    }

}
