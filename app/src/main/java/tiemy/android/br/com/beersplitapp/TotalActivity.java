package tiemy.android.br.com.beersplitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tiemy.android.br.com.beersplitapp.adapter.ExpenseAdapter;
import tiemy.android.br.com.beersplitapp.adapter.TotalAdapter;
import tiemy.android.br.com.beersplitapp.api.OnExpenseClickListener;
import tiemy.android.br.com.beersplitapp.api.OnItemClickListenter;
import tiemy.android.br.com.beersplitapp.dao.ExpenseDAO;
import tiemy.android.br.com.beersplitapp.model.Expense;

public class TotalActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private TotalAdapter totalAdapter;
    private ExpenseDAO expenseDAO = new ExpenseDAO(this);
    private Expense expense;
    List<Expense> itens = new ArrayList<Expense>();
    int id_round;
    String localName = "";

    TextView tvPlace;
    TextView tvNumberOfPeople;
    TextView tvTotal;
    TextView tvTotalTips;
    TextView tvTotalPerPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        int numberOfPeople = 1;

        Bundle param = getIntent().getExtras();
        id_round = Integer.parseInt(param.getString("id_round"));
        localName = param.getString("localName");
        String number = param.getString("numberPeople");
        if(!number.isEmpty())
            numberOfPeople = Integer.parseInt(number);

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

        tvPlace.setText(localName);
        tvNumberOfPeople.setText(String.valueOf(numberOfPeople));
        calcularValores();
    }

    private void carregaDados(){
        itens = expenseDAO.getAll(id_round);
    }

    public void calcularValores(){
        itens = expenseDAO.getAll(id_round);
        //if(itens.size()>0)

    }

    public void save(View view){


    }
}
