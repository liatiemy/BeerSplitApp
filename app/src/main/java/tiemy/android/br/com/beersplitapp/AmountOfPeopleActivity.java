package tiemy.android.br.com.beersplitapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AmountOfPeopleActivity extends AppCompatActivity {

    private EditText etNumberPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_of_people);

        etNumberPeople = (EditText) findViewById(R.id.etNumberPeople);

        Bundle params = getIntent().getExtras();
        if(params.get("numberPeople")!= null)
            etNumberPeople.setText(params.get("numberPeople").toString());
    }

    public void btNumber(View view){
        etNumberPeople = (EditText) findViewById(R.id.etNumberPeople);

        if(!etNumberPeople.getText().toString().equals("")) {
            Intent intent = new Intent();
            intent.putExtra("numberPeople", etNumberPeople.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else
            Toast.makeText(this, getResources().getString(R.string.invalid_number_people), Toast.LENGTH_SHORT).show();

    }
}
