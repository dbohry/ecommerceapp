package ecommerce.qgdagraciela.com.ecommerceapp.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDTO {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("usuario")
    @Expose
    private UsuarioDTO usuario;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
