package tiemy.android.br.com.beersplitapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import tiemy.android.br.com.beersplitapp.dao.ExpenseDAO;
import tiemy.android.br.com.beersplitapp.model.Expense;

public class ExpenseUpdateActivity  extends AppCompatActivity {

    private ExpenseDAO expenseDAO = new ExpenseDAO(this);
    private Expense expense;

    EditText etExpenseName;
    EditText etUnitPrice;
    EditText etQuantityOfProduct;

    int id_round;
    int quantity;
    String expenseName;
    double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        Bundle param = getIntent().getExtras();
        id_round = param.getInt("id");
        quantity = param.getInt("quantity");
        expenseName = param.getString("expense");
        price = param.getDouble("price");

        etExpenseName = (EditText) findViewById(R.id.etExpenseName);
        etUnitPrice = (EditText) findViewById(R.id.etUnitPrice);
        etQuantityOfProduct = (EditText) findViewById(R.id.etQuantityOfProduct);

        etExpenseName.setText(expenseName);
        etQuantityOfProduct.setText(String.valueOf(quantity));
        etUnitPrice.setText(String.valueOf(price));



    }
    public void save(View view) {
        String expenseName = etExpenseName.getText().toString();
        double unitPrice = Double.parseDouble(etUnitPrice.getText().toString());
        int quantityOfProduct = Integer.parseInt(etQuantityOfProduct.getText().toString());

        expense = expenseDAO.getByExpense(expenseName);

        expense.setNameExpense(expenseName);
        expense.setPrice(unitPrice);
        expense.setQuantity(quantityOfProduct);

        String result = expenseDAO.update(expenseName, expense);
        if(!result.contains("erro")){
            Intent intent = new Intent();
            intent.putExtra("result", "OK");
            setResult(Activity.RESULT_OK, intent);
            finish();
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
