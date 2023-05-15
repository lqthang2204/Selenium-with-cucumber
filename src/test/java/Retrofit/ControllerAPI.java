package Retrofit;

import Nada.MessageEmail;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ControllerAPI {
    public static Response<MessageEmail> getMessageID(ServiceAPI serviceAPI, String email) throws IOException, InterruptedException {
        Call<MessageEmail> messageID = serviceAPI.getMessageID(email);
        Response<MessageEmail> response = messageID.execute();
        if(response.isSuccessful()){
            return response;
        }else{
            throw new IOException(response.errorBody().toString());
        }
    }
    public static ServiceAPI getServiceFromURL(String url) {
        OkHttpClient.Builder httpCLient = new OkHttpClient().newBuilder();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).client(httpCLient.build()).build();
        ServiceAPI service = retrofit.create(ServiceAPI.class);
        return service;
    }
    public static ServiceAPI getServiceFromURLUnsafeHttpClient(String url) {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient.newBuilder().build()).build();
        ServiceAPI service = retrofit.create(ServiceAPI.class);
        return service;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
            ControllerAPI control = new ControllerAPI();
        ServiceAPI service = control.getServiceFromURL("https://getnada.com");
            control.getMessageID(service,"archie8501221@inboxbear.com" );

    }
}
