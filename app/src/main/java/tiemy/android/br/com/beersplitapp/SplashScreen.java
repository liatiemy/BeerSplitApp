package tiemy.android.br.com.beersplitapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils; import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tiemy.android.br.com.beersplitapp.api.APIUtils;
import tiemy.android.br.com.beersplitapp.api.UsuarioAPI;
import tiemy.android.br.com.beersplitapp.dao.UsuarioDAO;
import tiemy.android.br.com.beersplitapp.model.Usuario;

public class SplashScreen extends AppCompatActivity {

    //Tempo que nosso splashscreen ficará visivel
    private final int SPLASH_DISPLAY_LENGTH = 3500;

    UsuarioDAO usuarioDAO = new UsuarioDAO(this);
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        FacebookSdk.sdkInitialize(getApplicationContext());

        stetho();

        final UsuarioAPI usuarioAPI = APIUtils.getUsuarioAPIService();
        //enqueue é do retrofit2 para não deixar na thread principal
        usuarioAPI.getUsuario().enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()) {
                    //Log.i("Usuario: ", response.body().getUsuario());
                    usuario = response.body();
                    if(usuarioDAO.naoExisteUsuarioCadastrado())
                        usuarioDAO.add(usuario);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Failed to get service", t.toString());
            }
        });

        carregar();
    }

    private void stetho() {
        Context context = getBaseContext();
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
        // Enable command line interface
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(context));
        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();
        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }

    private void carregar() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash);
        anim.reset();

        ImageView iv = (ImageView) findViewById(R.id.splash);
        if (iv != null) {
            iv.clearAnimation();
            iv.startAnimation(anim);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Após o tempo definido irá executar a próxima tela
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}