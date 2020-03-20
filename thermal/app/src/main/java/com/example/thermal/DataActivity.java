package com.example.thermal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.MediaType.*;


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
                executeSendMessage(
                        tdry.getText().toString(),
                        twet.getText().toString(),
                        tcanopy.getText().toString(),
                        timeDay.getText().toString());
            }
        });
    }
        private void  executeSendMessage(String dry,String wet,String canopy, String time){
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://a79d79fa.ngrok.io")
                 // .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit =builder.build();

            UserClient userClient =retrofit.create(UserClient.class);


         RequestBody  = RequestBody.create(parse("text/plain"), dry,wet,canopy,time);

            Call<String> call = userClient.sendMessage(dry, wet, canopy, time);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    try {
                        String revive_result = response.body().string();
//
                        Toast.makeText(DataActivity.this, "Success "+revive_result, Toast.LENGTH_SHORT).show();
                        resultData.setText("Final Result: " + revive_result);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(DataActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();

                }
            });

        }

    }
