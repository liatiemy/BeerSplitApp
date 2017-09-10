package tiemy.android.br.com.beersplitapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import android.Manifest;

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
    List<Expense> itens = new ArrayList<Expense>();

    TextView tvPlaceTitle;
    TextView tvPlace;
    TextView tvNumberTitle;
    TextView tvNumberOfPeople;
    TextView tvTotalTitle;
    TextView tvTotal;
    TextView tvTotalTipsTitle;
    TextView tvTotalTips;
    TextView tvTotalPerPersonTitle;
    TextView tvTotalPerPerson;
    TextView tvTotalPerPersonTipsTitle;
    TextView tvTotalPerPersonTips;
    int idRound;

    private String message;
    private String subject;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,

    };

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

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);


        tvPlaceTitle = (TextView) findViewById(R.id.tvPlaceTitle);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        tvNumberTitle = (TextView) findViewById(R.id.tvNumberTitle);
        tvNumberOfPeople = (TextView) findViewById(R.id.tvNumberOfPeople);
        tvTotalTitle = (TextView) findViewById(R.id.tvTotalTitle);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvTotalTipsTitle = (TextView) findViewById(R.id.tvTotalTipsTitle);
        tvTotalTips = (TextView) findViewById(R.id.tvTotalTips);
        tvTotalPerPersonTitle = (TextView) findViewById(R.id.tvTotalPerPersonTitle);
        tvTotalPerPerson = (TextView) findViewById(R.id.tvTotalPerPerson);
        tvTotalPerPersonTipsTitle = (TextView) findViewById(R.id.tvTotalPerPersonTipsTitle);
        tvTotalPerPersonTips = (TextView) findViewById(R.id.tvTotalPerPersonTips);

        tvPlace.setText(roundRegister.getName());
        tvNumberOfPeople.setText(String.valueOf(roundRegister.getPeople()));
        tvTotal.setText(String.valueOf(roundRegister.getTotal().setScale(2, RoundingMode.HALF_EVEN)));
        tvTotalTips.setText(String.valueOf(roundRegister.getTotalTip().setScale(2, RoundingMode.HALF_EVEN)));
        tvTotalPerPerson.setText(String.valueOf(roundRegister.getTotalPerPerson().setScale(2, RoundingMode.HALF_EVEN)));
        tvTotalPerPersonTips.setText(String.valueOf(roundRegister.getTotalPerPersonTips().setScale(2, RoundingMode.HALF_EVEN)));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = montaCorpoEmail();
                subject = roundRegister.getName().toString();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("*/*");


                File f = new File(Environment.getExternalStorageDirectory() + File.separator + roundRegister.getName().toString()+".txt");
                Log.d("Arquivo f:", "" + f);
                try {

                    checkPermission();
                    f.createNewFile();

                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(message.getBytes());

                } catch (IOException e) {
                    e.printStackTrace();
                }

                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/" + roundRegister.getName().toString() +".txt"));

                startActivity(sendIntent);


            }
        });
    }

    public void checkPermission(){
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void carregaDados() {
        itens = expenseDAO.getAll(idRound);
    }

    public void delete(View view) {
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
            finish();
        }
    }

    private String montaCorpoEmail() {
        StringBuilder messagem = new StringBuilder();
        messagem.append(tvPlaceTitle.getText().toString());
        messagem.append(" ");
        messagem.append(roundRegister.getName().toString());
        messagem.append("\n");
        messagem.append(tvNumberTitle.getText().toString());
        messagem.append(" ");
        messagem.append(roundRegister.getPeople().toString());
        messagem.append("\n");
        messagem.append(tvTotalTitle.getText().toString());
        messagem.append(" ");
        messagem.append(roundRegister.getTotal().setScale(2, RoundingMode.HALF_EVEN).toString());
        messagem.append("\n");
        messagem.append(tvTotalTipsTitle.getText().toString());
        messagem.append(" ");
        messagem.append(roundRegister.getTotalTip().setScale(2, RoundingMode.HALF_EVEN).toString());
        messagem.append("\n");
        messagem.append(tvTotalPerPersonTitle.getText().toString());
        messagem.append(" ");
        messagem.append(roundRegister.getTotalPerPerson().setScale(2, RoundingMode.HALF_EVEN).toString());
        messagem.append("\n");
        messagem.append(tvTotalPerPersonTipsTitle.getText().toString());
        messagem.append(" ");
        messagem.append(roundRegister.getTotalPerPersonTips().setScale(2, RoundingMode.HALF_EVEN).toString());
        messagem.append("\n\n");
        messagem.append("Beer Split");
        return messagem.toString();
    }
}