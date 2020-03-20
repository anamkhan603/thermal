package com.example.thermal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {
    @POST("/calculate")
    Call<String> sendMessage(@Body String dry, String wet, String canopy, String time);

    //@POST("/upload")
    //Call<ResponseBody> sendMessage(@Body RequestBody dry, RequestBody wet, RequestBody canopy, RequestBody time );
}
