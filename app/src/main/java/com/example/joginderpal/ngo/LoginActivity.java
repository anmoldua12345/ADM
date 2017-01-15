package com.example.joginderpal.ngo;

import android.app.Activity;
import android.content.Intent;
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
public class LoginActivity extends Activity {

    EditText ed1,ed2;
    FrameLayout f1,f2;
    RequestQueue requestQueue;
    StringRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ed1= (EditText) findViewById(R.id.ed1);
        ed2= (EditText) findViewById(R.id.ed2);
        f1= (FrameLayout) findViewById(R.id.frame);
        f2= (FrameLayout) findViewById(R.id.frametwo);
        requestQueue= Volley.newRequestQueue(this);
        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                request=new StringRequest(Request.Method.POST, "http://akshathash.comxa.com/login.php"
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")){
                                Toast.makeText(getApplicationContext(),jsonObject.getJSONObject("success").getString("connection"),Toast.LENGTH_LONG).show();

                                if (jsonObject.getString("error").equals("You must fill in both fields")){
                                    Toast.makeText(getApplicationContext(),"One or two fields are empty",Toast.LENGTH_LONG).show();
                                }
                               else if (jsonObject.getString("error").equals("No errors")){

                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                               else if (jsonObject.getString("error").equals("Wrong Credentials")){
                                    Toast.makeText(getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                                }


                            }

                            else{
                                Toast.makeText(getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_LONG).show();
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
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("email",ed1.getText().toString());
                        hashMap.put("password",ed2.getText().toString());

                        return hashMap;
                    }

                };

                requestQueue.add(request);
            }
        });




        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);






            }
        });




    }
}
