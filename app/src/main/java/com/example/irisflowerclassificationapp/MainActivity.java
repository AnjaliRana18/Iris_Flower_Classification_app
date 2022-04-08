package com.example.irisflowerclassificationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    EditText sl,sw,pl,pw;
    Button predict;

    String url1 = "https://predict-iris-flower-app.herokuapp.com/predict";
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sl = findViewById(R.id.sepal_length);
        sw = findViewById(R.id.sepal_width);
        pl = findViewById(R.id.petal_length);
        pw = findViewById(R.id.petal_width);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hit the API -> Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("species");
                            result.setText(data);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "DATA NOT CORRECT", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "Internet Not Working", Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("sepal_length",sl.getText().toString());
                        params.put("sepal_width",sw.getText().toString());
                        params.put("petal_length",pl.getText().toString());
                        params.put("petal_width",pw.getText().toString());
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
            }
        });
    }
}