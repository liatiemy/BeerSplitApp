package tiemy.android.br.com.beersplitapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import tiemy.android.br.com.beersplitapp.adapter.TotalAdapter;
import tiemy.android.br.com.beersplitapp.dao.ExpenseDAO;
import tiemy.android.br.com.beersplitapp.dao.RoundRegisterDAO;
import tiemy.android.br.com.beersplitapp.model.Expense;
import tiemy.android.br.com.beersplitapp.model.RoundRegister;

public class RoundActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private TotalAdapter totalAdapter;
    private ExpenseDAO expenseDAO = new ExpenseDAO(this);
    private RoundRegisterDAO roundRegisterDAO = new RoundRegisterDAO(this);
    private RoundRegister roundRegister;
    private Expense expense;
    List<Expense> itens = new ArrayList<Expense>();

    TextView tvPlace;
    TextView tvNumberOfPeople;
    TextView tvTotal;
    TextView tvTotalTips;
    TextView tvTotalPerPerson;
    TextView tvTotalPerPersonTips;
    int idRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        Bundle param = getIntent().getExtras();
        String id_round = param.get("id_round").toString();
        idRound = Integer.parseInt(id_round);

        roundRegister = roundRegisterDAO.getByRound(id_round);

        carregaDados();

        recyclerView = (RecyclerView) findViewById(R.id.list);
        totalAdapter = new TotalAdapter(itens);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(totalAdapter);
        recyclerView.setHasFixedSize(true);

        //Para inserir as linhas
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);


        tvPlace = (TextView) findViewById(R.id.tvPlace);
        tvNumberOfPeople = (TextView) findViewById(R.id.tvNumberOfPeople);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvTotalTips = (TextView) findViewById(R.id.tvTotalTips);
        tvTotalPerPerson = (TextView) findViewById(R.id.tvTotalPerPerson);
        tvTotalPerPersonTips = (TextView) findViewById(R.id.tvTotalPerPersonTips);

        tvPlace.setText(roundRegister.getName());
        tvNumberOfPeople.setText(String.valueOf(roundRegister.getPeople()));
        tvTotal.setText(String.valueOf(roundRegister.getTotal().setScale(2, RoundingMode.HALF_EVEN)));
        tvTotalTips.setText(String.valueOf(roundRegister.getTotalTip().setScale(2, RoundingMode.HALF_EVEN)));
        tvTotalPerPerson.setText(String.valueOf(roundRegister.getTotalPerPerson().setScale(2, RoundingMode.HALF_EVEN)));
        tvTotalPerPersonTips.setText(String.valueOf(roundRegister.getTotalPerPersonTips().setScale(2, RoundingMode.HALF_EVEN)));
    }

    private void carregaDados(){
        itens = expenseDAO.getAll(idRound);
    }

    public void delete(View view){
        List<Expense> expenses = expenseDAO.getByIdRound(roundRegister.getId_round());
        for (Expense expense: expenses) {
            String result = expenseDAO.delete(expense);
            if(!result.contains("erro")){
                Intent intent = new Intent();
                intent.putExtra("result", "OK");
                setResult(Activity.RESULT_OK, intent);
                //finish();
            }
        }

        String result = roundRegisterDAO.delete(roundRegister.getId_round());
        //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        if(!result.contains("erro")){
            Intent intent = new Intent();
            intent.putExtra("result", "OK");
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

}
