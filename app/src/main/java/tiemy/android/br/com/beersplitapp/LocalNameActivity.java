package tiemy.android.br.com.beersplitapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LocalNameActivity extends AppCompatActivity {

    private EditText etLocalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_name);

        etLocalName = (EditText) findViewById(R.id.etLocalName);

        Bundle params = getIntent().getExtras();
        if(params.get("localName")!= null)
            etLocalName.setText(params.get("localName").toString());


    }

    public void btOk(View view){

        etLocalName = (EditText) findViewById(R.id.etLocalName);


        if(!etLocalName.getText().toString().equals("")) {

            Intent intent = new Intent();
            intent.putExtra("localName", etLocalName.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else
            Toast.makeText(this, getResources().getString(R.string.invalid_local_name), Toast.LENGTH_SHORT).show();

    }
}
