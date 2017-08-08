package tiemy.android.br.com.beersplitapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AmountOfPeopleActivity extends AppCompatActivity {

    private EditText etNumberPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_of_people);
    }

    public void btNumber(View view){
        int number =0;
        etNumberPeople = (EditText) findViewById(R.id.etNumberPeople);
        if(!etNumberPeople.getText().toString().equals(""))
             number = Integer.parseInt(etNumberPeople.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("numberPeople", String.valueOf(number));
        setResult(Activity.RESULT_OK, intent);
        finish();

    }
}
