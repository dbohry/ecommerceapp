package ecommerce.qgdagraciela.com.ecommerceapp.clients;

import ecommerce.qgdagraciela.com.ecommerceapp.dtos.UsuarioDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterClient {

    @POST("/ecommerce/api/register")
    Call<UsuarioDTO> register(@Body UsuarioDTO dto);

}
