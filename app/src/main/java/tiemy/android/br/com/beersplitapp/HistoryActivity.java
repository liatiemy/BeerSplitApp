package tiemy.android.br.com.beersplitapp;

import android.app.Activity;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tiemy.android.br.com.beersplitapp.adapter.LinhaAdapter;
import tiemy.android.br.com.beersplitapp.adapter.RoundAdapter;
import tiemy.android.br.com.beersplitapp.api.OnItemClickListenter;
import tiemy.android.br.com.beersplitapp.api.OnRoundClickListener;
import tiemy.android.br.com.beersplitapp.dao.ExpenseDAO;
import tiemy.android.br.com.beersplitapp.dao.RoundRegisterDAO;
import tiemy.android.br.com.beersplitapp.model.Expense;
import tiemy.android.br.com.beersplitapp.model.RoundRegister;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    private RoundAdapter roundAdapter;
    private RoundRegisterDAO roundRegisterDAO = new RoundRegisterDAO(this);
    private ExpenseDAO expenseDAO = new ExpenseDAO(this);
    private List<RoundRegister> rounds;
    static final int REQUEST_DELETE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = (RecyclerView) findViewById(R.id.list);

        roundAdapter = new RoundAdapter(new ArrayList<RoundRegister>(), new OnRoundClickListener() {
            @Override
            public void onItemClick(RoundRegister roundRegister) {
                roundRegister = roundRegisterDAO.getByName(String.valueOf(roundRegister.getName()));
                if(roundRegister.getId_round()!=0) {
                    Intent intent = new Intent(getApplicationContext(), RoundActivity.class);
                    intent.putExtra("id_round", roundRegister.getId_round());
                    startActivityForResult(intent, REQUEST_DELETE);
                }
            }

            @Override
            public void onLongClick(RoundRegister roundRegister) {
                roundRegister = roundRegisterDAO.getByName(String.valueOf(roundRegister.getName()));
                if(roundRegister.getId_round()!=0) {
                    confirmaDelecao(roundRegister);
                }
            }

        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(roundAdapter);
        recyclerView.setHasFixedSize(true);

        //Para inserir as linhas
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        carregaDados();

    }

    private void carregaDados() {
        rounds = roundRegisterDAO.getAll();
        if(rounds.size() == 0) {
            setContentView(R.layout.activity_empty_history);
        }else {
            roundAdapter.update(rounds);
        }
    }

    public void registry(View view){
        Intent intent = new Intent(this, RoundRegisterActivity.class);
        startActivity(intent);
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

    private void confirmaDelecao(final RoundRegister roundRegister){
        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
        builder.setMessage(getString(R.string.confirma_delecao) + "\n" + "\n" + roundRegister.getName())
                .setPositiveButton(R.string.yes_delete, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRoundAndExpenses(roundRegister);
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

    private void deleteRoundAndExpenses(RoundRegister roundRegister) {
        List<Expense> expenses = expenseDAO.getByIdRound(roundRegister.getId_round());
        for (Expense expense : expenses) {
            String result = expenseDAO.delete(expense);
            if (!result.contains("erro")) {
                Intent intent = new Intent();
                intent.putExtra("result", "OK");
                setResult(Activity.RESULT_OK, intent);
            }
        }

        String result = roundRegisterDAO.delete(roundRegister.getId_round());
        if (!result.contains("erro")) {
            Intent intent = new Intent();
            intent.putExtra("result", "OK");
            setResult(Activity.RESULT_OK, intent);
        }
    }

}
