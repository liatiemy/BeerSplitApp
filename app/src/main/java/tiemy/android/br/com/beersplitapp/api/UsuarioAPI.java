package tiemy.android.br.com.beersplitapp.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

import tiemy.android.br.com.beersplitapp.model.Usuario;

public interface UsuarioAPI {
    @GET("v2/58b9b1740f0000b614f09d2f")
    Call<Usuario> getUsuario();

}