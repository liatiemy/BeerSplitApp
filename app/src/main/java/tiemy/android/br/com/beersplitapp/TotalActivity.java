package tiemy.android.br.com.beersplitapp;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import tiemy.android.br.com.beersplitapp.adapter.ExpenseAdapter;
import tiemy.android.br.com.beersplitapp.adapter.TotalAdapter;
import tiemy.android.br.com.beersplitapp.api.OnExpenseClickListener;
import tiemy.android.br.com.beersplitapp.api.OnItemClickListenter;
import tiemy.android.br.com.beersplitapp.dao.ExpenseDAO;
import tiemy.android.br.com.beersplitapp.dao.RoundRegisterDAO;
import tiemy.android.br.com.beersplitapp.model.Expense;
import tiemy.android.br.com.beersplitapp.model.RoundRegister;

public class TotalActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private TotalAdapter totalAdapter;
    private ExpenseDAO expenseDAO = new ExpenseDAO(this);
    private RoundRegisterDAO roundRegisterDAO = new RoundRegisterDAO(this);
    private RoundRegister roundRegister;
    List<Expense> itens = new ArrayList<Expense>();

    TextView tvPlace;
    TextView tvNumberOfPeople;
    TextView tvTotal;
    TextView tvTotalTips;
    TextView tvTotalPerPerson;
    TextView tvTotalPerPersonTips;

    int id_round;
    String localName = "";
    BigDecimal numberOfPeople = new BigDecimal(0);
    BigDecimal total = new BigDecimal(0);
    BigDecimal tip = new BigDecimal(0);
    BigDecimal totalPerPerson = new BigDecimal(0);
    BigDecimal totalPerPersonTips = new BigDecimal(0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        Bundle param = getIntent().getExtras();
        id_round = Integer.parseInt(param.getString("id_round"));
        localName = param.getString("localName");
        String number = param.getString("numberPeople");
        if(!number.isEmpty())
            numberOfPeople = new BigDecimal(number);

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

        tvPlace.setText(localName);
        tvNumberOfPeople.setText(String.valueOf(numberOfPeople));
        calcularValores();

    }

    private void carregaDados(){
        itens = expenseDAO.getAll(id_round);
    }

    public void calcularValores(){
        itens = expenseDAO.getAll(id_round);
        if(itens.size()>0){
            for (Expense expense: itens){
                BigDecimal soma =
                        new BigDecimal(expense.getPrice()).multiply(new BigDecimal(expense.getQuantity()));
                total = total.add(soma);
            }
            total = total.setScale(2, RoundingMode.HALF_EVEN);
            tip = total.multiply(new BigDecimal(1.1)).setScale(2, RoundingMode.HALF_EVEN);
            totalPerPerson = total.divide(numberOfPeople, 2, RoundingMode.HALF_EVEN);
            totalPerPersonTips = tip.divide(numberOfPeople, 2, RoundingMode.HALF_EVEN);
            tvTotal.setText(String.valueOf(total));
            tvTotalTips.setText(String.valueOf(tip));
            tvTotalPerPerson.setText(String.valueOf(totalPerPerson));
            tvTotalPerPersonTips.setText(String.valueOf(totalPerPersonTips));
        }

    }

    public void save(View view){
        roundRegister = new RoundRegister();
        roundRegister.setId_round(id_round);
        roundRegister.setName(localName);
        roundRegister.setPeople(numberOfPeople);
        roundRegister.setTotal(total);
        roundRegister.setTotalTip(tip);
        roundRegister.setTotalPerPerson(totalPerPerson);
        roundRegister.setTotalPerPersonTips(totalPerPersonTips);

        String result = roundRegisterDAO.add(roundRegister);
        //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        if(!result.contains("erro")){
            Intent intent = new Intent();
            intent.putExtra("result", "OK");
            setResult(Activity.RESULT_OK, intent);
            startActivity(new Intent(TotalActivity.this, MenuActivity.class));
        }
    }
}
