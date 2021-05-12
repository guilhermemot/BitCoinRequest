package com.example.trackbitcoin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
public class MainActivity extends AppCompatActivity {

    // Constants:
// TODO: Create the base URL
    private final String BASE_URL = "https://api.bitcointrade.com.br/v3/public/";
    // Member Variables:
    TextView mTextViewLast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewLast = (TextView) findViewById(R.id.textViewLast);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCoinChange);
        String[] items = new String[] {
                "BRL","CAD","CNY","EUR","GBP","HKD","JPY","PLN","RUB","SEKE","USD","ZAR"
        };
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //letsDoSomeNetworking("https://api.bitcointrade.com.br/v3/public/BRLBTC/ticker");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i,
                                       long l) {
                Log.d("Bitcoin", "" + adapterView.getItemAtPosition(i));
                letsDoSomeNetworking(BASE_URL+adapterView.getItemAtPosition(i)+"BTC/ticker");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("Bitcoin", "Nothing selected");
            }
        });
        letsDoSomeNetworking(BASE_URL+"BRL");
    }
    private void letsDoSomeNetworking(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Bitcoin", "JSON: " + response.toString());
                try{
                    //JSONObject json = (JSONObject) JSONSerializer.toJSON(response);
                    //https://stackoverflow.com/questions/7332611/how-do-i-extract-value-from-json/52950235
                    mTextViewLast.setText(response.getString("last"));
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject
                    response) {
                Log.d("Bitcoin", "Request fail! Status code: " + statusCode);
                Log.d("Bitcoin", "Fail response: " + response);
                Log.e("ERROR", e.toString());
            }
        });
    }
}