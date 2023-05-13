package Retrofit;

import Nada.MessageEmail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceAPI {
    @GET("api/v1/inboxes/{email}")
    public Call<MessageEmail> getMessageID(@Path("email") String personEmail);


}
