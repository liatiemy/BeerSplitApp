package tiemy.android.br.com.beersplitapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LigacaoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligacao);

        final Button btLigar = (Button) findViewById(R.id.btLigar);
        btLigar.setOnClickListener(this);
    }

    public void onClick(View view){

        EditText campoTelefone = (EditText) findViewById(R.id.etTelefone);
        String telefone = campoTelefone.getText().toString();

        Uri uri = Uri.parse("Tel: " + telefone);

        Intent intent = new Intent(Intent.ACTION_DIAL, uri);

        startActivity(intent);

    }
}
