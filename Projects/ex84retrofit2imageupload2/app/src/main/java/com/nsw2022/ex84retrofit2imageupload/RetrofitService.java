package com.nsw2022.ex84retrofit2imageupload;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitService {

    @Multipart
    @POST("04Retrofit/fileUpload.php")
    Call<String> sendImg(@Part MultipartBody.Part filePart);
}
