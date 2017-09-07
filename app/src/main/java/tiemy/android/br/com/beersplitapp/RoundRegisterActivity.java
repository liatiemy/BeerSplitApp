package tiemy.android.br.com.beersplitapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import tiemy.android.br.com.beersplitapp.adapter.LinhaAdapter;
import tiemy.android.br.com.beersplitapp.api.OnItemClickListenter;
import tiemy.android.br.com.beersplitapp.dao.ExpenseDAO;
import tiemy.android.br.com.beersplitapp.dao.RoundRegisterDAO;
import tiemy.android.br.com.beersplitapp.model.RoundRegister;

public class RoundRegisterActivity extends AppCompatActivity{
    RecyclerView recyclerView ;
    private LinhaAdapter linhaAdapter;
    private RoundRegister roundRegister;
    private RoundRegisterDAO roundRegisterDAO = new RoundRegisterDAO(this);
    private ExpenseDAO expenseDAO = new ExpenseDAO(this);
    int ultimoRoundCadastrado;

    static final int LOCAL_NAME_REQUEST = 1;
    static final int AMOUNT_PEOPLE_REQUEST = 2;
    static final int EXPENSE_REQUEST = 3;
    static final int TOTAL_REQUEST = 4;

    List<String> itens = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_register);

        recyclerView = (RecyclerView) findViewById(R.id.list);

        ultimoRoundCadastrado = roundRegisterDAO.getLastIdRound()+1;
        roundRegister = new RoundRegister();
        roundRegister.setId_round(ultimoRoundCadastrado);

        linhaAdapter = new LinhaAdapter(new ArrayList<String>(), new OnItemClickListenter() {
            @Override
            public void onItemClick(String item) {
                //Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();

            if(item.contains(getResources().getString(R.string.local_activity))) {
                Intent intent = new Intent(getApplicationContext(), LocalNameActivity.class);
                intent.putExtra("localName", roundRegister.getName());
                startActivityForResult(intent, LOCAL_NAME_REQUEST);
            } else if (item.contains(getResources().getString(R.string.amount_of_people))) {
                Intent intent2 = new Intent(getApplicationContext(), AmountOfPeopleActivity.class);
                intent2.putExtra("numberPeople", roundRegister.getPeople());
                startActivityForResult(intent2, AMOUNT_PEOPLE_REQUEST);
            } else if (item.contains(getResources().getString(R.string.expenses))) {
                Intent intent3 = new Intent(RoundRegisterActivity.this, ExpensesActivity.class);
                intent3.putExtra("id_round", String.valueOf(roundRegister.getId_round()));
                //startActivity(intent3);
                startActivityForResult(intent3, EXPENSE_REQUEST);
            } else {
                if (validaRoundRegister()) {
                    Intent intent4 = new Intent(RoundRegisterActivity.this, TotalActivity.class);
                    intent4.putExtra("id_round", String.valueOf(roundRegister.getId_round()));
                    intent4.putExtra("localName", roundRegister.getName());
                    intent4.putExtra("numberPeople", String.valueOf(roundRegister.getPeople()));
                    startActivity(intent4);

                }
            }

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(linhaAdapter);
        recyclerView.setHasFixedSize(true);

        //Para inserir as linhas
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        carregaDados();


        FloatingActionButton ligacao = (FloatingActionButton) findViewById(R.id.ligacao);
        ligacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoundRegisterActivity.this, LigacaoActivity.class));
                finish();

            }
        });
    }

    private boolean validaRoundRegister() {
        if(roundRegister.getName()==null || roundRegister.getPeople()==null ||
                roundRegister.getExpenses()==null) {
            Toast.makeText(this, getResources().getString(R.string.invalid_round),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void carregaDados(){
        String[] menu = getResources().getStringArray(R.array.menu);
        itens.add(menu[0]);
        itens.add(menu[1]);
        itens.add(menu[2]);
        itens.add(menu[3]);
        itens.set(0, menu[0]);
        itens.set(1, menu[1]);
        itens.set(2, menu[2]);
        itens.set(3, menu[3]);
        linhaAdapter.update(itens);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOCAL_NAME_REQUEST) {
            if (resultCode == RESULT_OK) {
                roundRegister.setName(data.getStringExtra("localName").toString());
                //Toast.makeText(this, "localName: " + roundRegister.getName(), Toast.LENGTH_SHORT).show();
                itens.set(0, getResources().getString(R.string.local_activity)+": "+data.getStringExtra("localName").toString());
                linhaAdapter.update(itens);
            }
        }
        if (requestCode == AMOUNT_PEOPLE_REQUEST) {
            if (resultCode == RESULT_OK) {
                roundRegister.setPeople(new BigDecimal(data.getStringExtra("numberPeople")));
                //Toast.makeText(this, "numberPeople: " + String.valueOf(roundRegister.getPeople()), Toast.LENGTH_SHORT).show();
                itens.set(1, getResources().getString(R.string.amount_of_people)+": "+data.getStringExtra("numberPeople").toString());
                linhaAdapter.update(itens);
            }
        }
        if (requestCode == EXPENSE_REQUEST) {
            //if (resultCode == RESULT_OK) {
                roundRegister.setExpenses(expenseDAO.getAll(roundRegister.getId_round()));
                //Toast.makeText(this, "numberPeople: " + String.valueOf(roundRegister.getPeople()), Toast.LENGTH_SHORT).show();
            //}
        }
    }
}

