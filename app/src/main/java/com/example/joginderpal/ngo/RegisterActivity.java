package com.example.joginderpal.ngo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joginderpal on 04-01-2017.
 */
public class RegisterActivity extends Activity {
    EditText ed1,ed2,ed3;
    RequestQueue requestQueue;
    StringRequest request;

    FrameLayout frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ed1= (EditText) findViewById(R.id.ed3);
        ed2= (EditText) findViewById(R.id.ed4);
        ed3= (EditText) findViewById(R.id.ed5);
        frame= (FrameLayout) findViewById(R.id.framethree);
        requestQueue= Volley.newRequestQueue(this);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url="http://akshathash.comxa.com/register.php";

                StringRequest request=new StringRequest(Request.Method.POST,url,


                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    if (jsonObject.names().get(0).equals("success")){
                                        if (jsonObject.getString("error").equals("You must fill in both fields")){
                                            Toast.makeText(getApplicationContext(),"One or two fields are empty",Toast.LENGTH_LONG).show();
                                        }
                                        if (jsonObject.getString("error").equals("The user already exists")){
                                            Toast.makeText(getApplicationContext(),"already exists",Toast.LENGTH_LONG).show();
                                        }

                                        if (jsonObject.getString("error").equals("No errors")){
                                            Toast.makeText(getApplicationContext(),jsonObject.getJSONObject("success").getString("result"),Toast.LENGTH_LONG).show();
                                        }



                                    }
                                    else{

                                        Toast.makeText(getApplicationContext(),"Check your Internet connnection",Toast.LENGTH_LONG).show();

                                    }


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


                ){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap=new HashMap<String, String>();
                        hashMap.put("email",ed1.getText().toString());
                        hashMap.put("password",ed2.getText().toString());
                        hashMap.put("name",ed3.getText().toString());
                        return hashMap;

                    }
                };

                requestQueue.add(request);



            }
        });


    }
}
