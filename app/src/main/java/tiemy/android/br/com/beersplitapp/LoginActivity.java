package tiemy.android.br.com.beersplitapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Fragment;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import tiemy.android.br.com.beersplitapp.dao.UsuarioDAO;
import tiemy.android.br.com.beersplitapp.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    UsuarioDAO usuarioDAO = new UsuarioDAO(this);
    Usuario usuario = new Usuario();

    private EditText etLogin;
    private EditText etSenha;
    private CheckBox cbConectado;
    private LoginButton login_facebook;

    private CallbackManager callbackManager;

    private static final String KEY_APP_PREFERENCES = "login";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";

    private SharedPreferences.OnSharedPreferenceChangeListener callback = new SharedPreferences.OnSharedPreferenceChangeListener(){
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.i("Script", key + " updated");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etSenha = (EditText) findViewById(R.id.etSenha);
        cbConectado = (CheckBox) findViewById(R.id.cbConectado);
        login_facebook = (LoginButton) findViewById(R.id.login_facebook);

        FacebookSdk.sdkInitialize(this);

        callbackManager = CallbackManager.Factory.create();

        login_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (cbConectado.isChecked()) {
                    String login = "userFB";
                    String password = "passFB";
                    keepConected(login, password);
                }
                iniciarApp();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "login cancelado FB", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "login com erro FB", Toast.LENGTH_SHORT).show();
            }
        });

        if(isConected()){
            iniciarApp();
        }
    }

    public void logar(View view) {
        String login = etLogin.getText().toString();
        String password = etSenha.getText().toString();
        if (isValidLogin(login, password)) {
            if (cbConectado.isChecked()) {
                keepConected(login, password);
            }
            iniciarApp();
        }
    }

    public boolean isValidLogin(String login, String password){
        usuario.setUsuario(login);
        usuario.setSenha(password);
        if(usuarioDAO.getByUsername(usuario)){
            return true;
        }else{
            Toast.makeText(this, getResources().getString(R.string.login_invalido), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void keepConected(String login, String password){

        SharedPreferences sp = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        sp.registerOnSharedPreferenceChangeListener(callback);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_LOGIN, login);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    public boolean isConected(){
        SharedPreferences sp = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        String login = sp.getString(KEY_LOGIN, "");
        if(login.equals(""))
            return false;
        else
            return true;
    }

    public void iniciarApp() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        SharedPreferences sp = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        sp.unregisterOnSharedPreferenceChangeListener(callback);
        if(!isConected())
            LoginManager.getInstance().logOut();
    }

    @Override
    public void onStop(){
        super.onStop();

        String login = etLogin.getText().toString();
        SharedPreferences sp = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_LOGIN, login);

        Log.i("Script", "onStop");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
