package tiemy.android.br.com.beersplitapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import tiemy.android.br.com.beersplitapp.dao.RoundRegisterDAO;
import tiemy.android.br.com.beersplitapp.model.RoundRegister;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    private RoundAdapter roundAdapter;
    private RoundRegisterDAO roundRegisterDAO = new RoundRegisterDAO(this);
    private List<RoundRegister> rounds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = (RecyclerView) findViewById(R.id.list);

        roundAdapter = new RoundAdapter(new ArrayList<RoundRegister>(), new OnRoundClickListener() {
            @Override
            public void onItemClick(RoundRegister roundRegister) {
                //Toast.makeText(getApplicationContext(), roundRegister.getName(), Toast.LENGTH_SHORT).show();
                //roundRegister = new RoundRegister();
                //roundRegister = roundRegisterDAO.getByRound(String.valueOf(roundRegister.getId_round()+1));
                roundRegister = roundRegisterDAO.getByName(String.valueOf(roundRegister.getName()));
                if(roundRegister.getId_round()!=0) {
                    //Toast.makeText(getApplicationContext(), roundRegister.getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), RoundActivity.class);
                    intent.putExtra("id_round", roundRegister.getId_round());
                    startActivity(intent);
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
            Toast.makeText(this, "Não há rodadas cadastradas",
                    Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_empty_history);

        }else {
           // Toast.makeText(this, "qtde de rodas: " + rounds.size(),
             //       Toast.LENGTH_SHORT).show();
            roundAdapter.update(rounds);
        }
    }

    public void registry(View view){
        Intent intent = new Intent(this, RoundRegisterActivity.class);
        startActivity(intent);
    }


}
