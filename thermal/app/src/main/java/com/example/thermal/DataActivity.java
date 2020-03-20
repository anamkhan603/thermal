package com.example.thermal;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thermal.model.Data;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DataActivity extends AppCompatActivity {
    EditText tdry;
    EditText twet;
    EditText tcanopy;
    EditText timeDay;
    TextView resultData;
    Button mReviveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        tdry = findViewById(R.id.t_dry);
        twet = findViewById(R.id.t_wet);
        tcanopy = findViewById(R.id.t_canopy);
        timeDay = findViewById(R.id.time_day);
        mReviveBtn = findViewById(R.id.revive_btn);
        resultData =findViewById(R.id.resultData);

        mReviveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Data data= new Data(
                        Integer.parseInt(tdry.getText().toString()),
                        Integer.parseInt(twet.getText().toString()),
                        Integer.parseInt(tcanopy.getText().toString()),
                        Integer.parseInt(timeDay.getText().toString())
                );

                reviveData(data);
            }
        });
    }
        private void  reviveData(Data data){
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS).build();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://0def17d1.ngrok.io")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit =builder.build();

            UserClient userClient =retrofit.create(UserClient.class);
            Call<Data> call = userClient.reviveData(data);

            call.enqueue(new Callback <Data>() {
                @Override
                public void onResponse(Call<Data> call, Response <Data> response) {
                    Log.d("Main", "done: " + response);
                    Toast.makeText(DataActivity.this, "Success"+ response.body(), Toast.LENGTH_SHORT).show();
                    //Data revive_result = response.body();
                    //Toast.makeText(DataActivity.this, "Success "+revive_result, Toast.LENGTH_SHORT).show();
                    //resultData.setText("Final Result: " + revive_result);
                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    Log.d("Main", "onFailure: " + t);
                    Toast.makeText(DataActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    String test = "anam abcded";
                    resultData.setText(test);
                    //Toast.makeText(DataActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();

                }
            });

        }

    }
