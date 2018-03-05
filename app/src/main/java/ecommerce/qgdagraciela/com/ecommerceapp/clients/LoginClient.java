package ecommerce.qgdagraciela.com.ecommerceapp.clients;

import retrofit2.Call;
import retrofit2.http.POST;

public interface LoginClient {

    @POST("/ecommerce/api/login")
    Call<String> login(String email, String password);

}
