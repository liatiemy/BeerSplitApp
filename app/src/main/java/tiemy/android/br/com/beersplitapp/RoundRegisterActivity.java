package tiemy.android.br.com.beersplitapp;


import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tiemy.android.br.com.beersplitapp.adapter.LinhaAdapter;
import tiemy.android.br.com.beersplitapp.api.OnItemClickListenter;
import tiemy.android.br.com.beersplitapp.dao.RoundRegisterDAO;
import tiemy.android.br.com.beersplitapp.model.RoundRegister;

public class RoundRegisterActivity extends AppCompatActivity{
    RecyclerView recyclerView ;
    private LinhaAdapter linhaAdapter;
    private RoundRegister roundRegister;
    private RoundRegisterDAO roundRegisterDAO;

    List<String> itens = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_register);

        recyclerView = (RecyclerView) findViewById(R.id.list);

        linhaAdapter = new LinhaAdapter(new ArrayList<String>(), new OnItemClickListenter() {
            @Override
            public void onItemClick(String item) {
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                roundRegister = new RoundRegister();
                switch (item){
                    case "Local Name":
                        startActivity(new Intent(RoundRegisterActivity.this, LocalNameActivity.class));
                        Bundle bundle = getIntent().getExtras();
                        String localName = bundle.getString("localName");
                        break;
                    case "Amount of people":
                        startActivity(new Intent(RoundRegisterActivity.this, LocalNameActivity.class));
                        break;
                    case "Expenses":
                        startActivity(new Intent(RoundRegisterActivity.this, LocalNameActivity.class));
                        break;
                    case "Total":
                        startActivity(new Intent(RoundRegisterActivity.this, LocalNameActivity.class));
                        break;

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
    }

    private void carregaDados(){
        itens.add("Local Name");
        itens.add("Amount of people");
        itens.add("Expenses");
        itens.add("Total");
        linhaAdapter.update(itens);
    }
}

