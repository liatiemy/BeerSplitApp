package tiemy.android.br.com.beersplitapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import tiemy.android.br.com.beersplitapp.dao.ExpenseDAO;
import tiemy.android.br.com.beersplitapp.model.Expense;

public class ExpenseActivity extends AppCompatActivity {

    private ExpenseDAO expenseDAO = new ExpenseDAO(this);
    private Expense expense;

    EditText etExpenseName;
    EditText etUnitPrice;
    EditText etQuantityOfProduct;

    int id_round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        Bundle param = getIntent().getExtras();
        id_round = Integer.parseInt(param.getString("id_round"));

        etExpenseName = (EditText) findViewById(R.id.etExpenseName);
        etUnitPrice = (EditText) findViewById(R.id.etUnitPrice);
        etQuantityOfProduct = (EditText) findViewById(R.id.etQuantityOfProduct);



    }
    public void save(View view) {
        String expenseName = "";
        double unitPrice = 0.0;
        int quantityOfProduct = 0;

        if(etExpenseName.getText().toString().equals(""))
            Toast.makeText(this, getResources().getString(R.string.nome_despesa_invalido), Toast.LENGTH_SHORT).show();
        else
            expenseName = etExpenseName.getText().toString();

        if(etUnitPrice.getText().toString().equals(""))
            Toast.makeText(this, getResources().getString(R.string.valor_unitario_invalido), Toast.LENGTH_SHORT).show();
        else
            unitPrice = Double.parseDouble(etUnitPrice.getText().toString());

        if(etQuantityOfProduct.getText().toString().equals("") || etQuantityOfProduct.getText().toString().equals("0"))
            Toast.makeText(this, getResources().getString(R.string.qtde_produto_invalido), Toast.LENGTH_SHORT).show();
        else
         quantityOfProduct = Integer.parseInt(etQuantityOfProduct.getText().toString());

        if(!etExpenseName.getText().toString().equals("") && !etUnitPrice.getText().toString().equals("") && !(etQuantityOfProduct.getText().toString().equals("") || etQuantityOfProduct.getText().toString().equals("0"))) {
            expense = new Expense();
            expense.setId_round(id_round);
            expense.setNameExpense(expenseName);
            expense.setPrice(unitPrice);
            expense.setQuantity(quantityOfProduct);

            String result = expenseDAO.add(expense);
            if (!result.contains("erro")) {
                Intent intent = new Intent();
                intent.putExtra("result", "OK");
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    public void discart(View view)
    {
        String expenseName = etExpenseName.getText().toString();
        double unitPrice = Double.parseDouble(etUnitPrice.getText().toString());
        int quantityOfProduct = Integer.parseInt(etQuantityOfProduct.getText().toString());

        expense = new Expense();
        expense.setId_round(id_round);
        expense.setNameExpense(expenseName);
        expense.setPrice(unitPrice);
        expense.setQuantity(quantityOfProduct);

        String result = expenseDAO.delete(expense);
        if(!result.contains("erro")){
            Intent intent = new Intent();
            intent.putExtra("result", "OK");
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
