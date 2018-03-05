package ecommerce.qgdagraciela.com.ecommerceapp.clients;

import ecommerce.qgdagraciela.com.ecommerceapp.dtos.LoginDTO;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginClient {

    @POST("/ecommerce/api/login")
    Call<LoginDTO> login(@Query("email") String email,
                         @Query("senha") String password);

}
