package tiemy.android.br.com.beersplitapp.dao;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;

import tiemy.android.br.com.beersplitapp.R;

public class DBOpenHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "beersplit.db";
    private static final int DB_VERSION = 4;

    private Context ctx;

    public DBOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //cria o BD
        lerEExecutarSQLScript(db, ctx, R.raw.db_criar);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS users");
        sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS expenses");
        sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS rounds");
        onCreate(sqLiteDatabase);
    }

    private void lerEExecutarSQLScript(SQLiteDatabase db, Context ctx, Integer sqlScriptResId){

        Resources res = ctx.getResources();

        try{
            InputStream in = res.openRawResource(sqlScriptResId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            executarSQLScript (db, reader);

            reader.close();
            in.close();

        } catch (IOException e){
            throw new RuntimeException("Não foi possível ler o arquivo  SQLite", e);
        }
    }

    private void executarSQLScript(SQLiteDatabase db, BufferedReader reader) throws IOException{
        String line;
        StringBuilder statement =  new StringBuilder();
        while((line = reader.readLine())!=null){
            statement.append(line);
            statement.append("\n");
            if(line.endsWith(";")){
                String toExec = statement.toString();
                log("Executing script: " + toExec);
                db.execSQL(toExec);
                statement = new StringBuilder();
            }
        }

    }

    private void log(String msg){
        Log.d(DBOpenHelper.class.getSimpleName(), msg);
    }


}
