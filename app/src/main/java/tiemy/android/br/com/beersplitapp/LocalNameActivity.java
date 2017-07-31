package tiemy.android.br.com.beersplitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LocalNameActivity extends AppCompatActivity {

    private EditText etLocalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_name);


    }

    public void ok(View view){
        etLocalName = (EditText) findViewById(R.id.etLocalName);

        Intent intent = new Intent(this, RoundRegisterActivity.class);
        intent.putExtra("localName", etLocalName.toString());
        startActivity(intent);
        finish();

    }
}
