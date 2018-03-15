package ecommerce.qgdagraciela.com.ecommerceapp.clients;

import ecommerce.qgdagraciela.com.ecommerceapp.dtos.UsuarioDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioClient {

    @PUT("/ecommerce/api/v1/usuarios/{id}")
    Call<UsuarioDTO> update(@Header("authorization") String token,
                            @Path("id") Long id,
                            @Body UsuarioDTO dto);

    @DELETE("/ecommerce/api/v1/usuarios/{id}")
    Call<ResponseBody> delete(@Header("authorization") String token,
                              @Path("id") Long id);

}
