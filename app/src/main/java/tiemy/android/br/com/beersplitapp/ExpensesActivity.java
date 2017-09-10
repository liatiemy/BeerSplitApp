package tiemy.android.br.com.beersplitapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import tiemy.android.br.com.beersplitapp.adapter.ExpenseAdapter;
import tiemy.android.br.com.beersplitapp.api.OnExpenseClickListener;
import tiemy.android.br.com.beersplitapp.dao.ExpenseDAO;
import tiemy.android.br.com.beersplitapp.model.Expense;

public class ExpensesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    static final int REQUEST = 1;
    static final int REQUEST_UPDATE = 2;


    private ExpenseDAO expenseDAO = new ExpenseDAO(this);

    List<Expense> itens = new ArrayList<Expense>();

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        Bundle param = getIntent().getExtras();
        final String id_round = param.getString("id_round");
        id = Integer.parseInt(id_round);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), ExpenseActivity.class);
                intent.putExtra("id_round", id_round);
                startActivityForResult(intent, REQUEST);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.list);

        expenseAdapter = new ExpenseAdapter(new ArrayList<Expense>(), new OnExpenseClickListener(){
            @Override
            public void onItemClick(Expense expense) {
                expense = expenseDAO.getByExpense(expense.getNameExpense());
                Intent intent = new Intent(getApplicationContext(), ExpenseUpdateActivity.class);
                intent.putExtra("id", expense.getId_round());
                intent.putExtra("expense", expense.getNameExpense());
                intent.putExtra("quantity", expense.getQuantity());
                intent.putExtra("price", expense.getPrice());
                startActivityForResult(intent, REQUEST_UPDATE);
            }

            @Override
            public void onLongClick(Expense expense) {
                confirmaDelecao(expense);
            }


        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        carregaDados();
    }

    private void carregaDados(){
        itens = expenseDAO.getAll(id);
        expenseAdapter.update(itens);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //if (requestCode == REQUEST) {
            if (resultCode == RESULT_OK) {
                //roundRegister.setName(data.getStringExtra("localName").toString());
                //Toast.makeText(this, "localName: " + roundRegister.getName(), Toast.LENGTH_SHORT).show();
                carregaDados();
            }
        //}
    }

    private void confirmaDelecao(final Expense expense){
        AlertDialog.Builder builder = new AlertDialog.Builder(ExpensesActivity.this);
        builder.setMessage(getString(R.string.confirma_delecao) + "\n" + "\n" + expense.getNameExpense())
                .setPositiveButton(R.string.yes_delete, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        expenseDAO.delete(expense);
                        carregaDados();
                    }
                })
                .setNegativeButton(R.string.dont_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }
}
