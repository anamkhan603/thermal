package com.example.thermal;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {
    @POST("/calculate")
    Call<ResponseBody> sendMessage(@Body String dry);

    //@POST("/upload")
    //Call<ResponseBody> sendMessage(@Body RequestBody dry, RequestBody wet, RequestBody canopy, RequestBody time );
}
