package tiemy.android.br.com.beersplitapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LigacaoActivity extends AppCompatActivity implements View.OnClickListener {

    String PERMISSIONS_CALL[] = {Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligacao);

        final Button btLigar = (Button) findViewById(R.id.btLigar);
        btLigar.setOnClickListener(this);
    }

    public void onClick(View view){
        ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_CALL,
                1
        );

        EditText campoTelefone = (EditText) findViewById(R.id.etTelefone);
        String telefone = campoTelefone.getText().toString();

        Uri uri = Uri.parse("tel: " + telefone);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            startActivity(intent);
        }
    }
}
