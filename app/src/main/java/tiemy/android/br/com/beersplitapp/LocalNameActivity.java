package tiemy.android.br.com.beersplitapp;

import android.app.Activity;
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

    public void btOk(View view){
        etLocalName = (EditText) findViewById(R.id.etLocalName);

        Intent intent = new Intent();
        intent.putExtra("localName", etLocalName.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();

    }
}
