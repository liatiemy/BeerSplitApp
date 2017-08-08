package tiemy.android.br.com.beersplitapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import tiemy.android.br.com.beersplitapp.model.RoundRegister;

public class RoundRegisterDAO {
    private DBOpenHelper banco;

    public RoundRegisterDAO(Context context){

        banco = new DBOpenHelper(context);
    }

    public static final String TABLE_ROUND = "rounds";
    public static final String COLUMN_ID_ROUND = "id_round";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PEOPLE = "people";
    public static final String COLUMN_TOTAL = "total";
    public static final String COLUMN_TOTALPERPERSON = "totalPerPerson";
    public static final String COLUMN_TIP = "tip";

    public String add(RoundRegister roundRegister){
        long result;
        SQLiteDatabase db = banco.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, roundRegister.getName());
        cv.put(COLUMN_PEOPLE, roundRegister.getPeople());
        cv.put(COLUMN_TOTAL, roundRegister.getTotal());
        cv.put(COLUMN_TOTALPERPERSON, roundRegister.getTotalPerPerson());
        cv.put(COLUMN_TIP, roundRegister.getTip());

        result = db.insert(TABLE_ROUND, null, cv);

        db.close();

        if(result == -1)
            return "Erro ao inserir rodada";
        else
            return "Rodada cadastrada com sucesso";
    }

    public List<RoundRegister> getAll(){
        List<RoundRegister> rounds = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_ROUND;
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        RoundRegister roundRegister = null;
        if(cursor.moveToFirst()){
            do{
                roundRegister = new RoundRegister();
                roundRegister.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                roundRegister.setPeople(cursor.getInt(cursor.getColumnIndex(COLUMN_PEOPLE)));
                roundRegister.setTotal(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL)));
                roundRegister.setTotalPerPerson(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTALPERPERSON)));
                roundRegister.setTip(cursor.getString(cursor.getColumnIndex(COLUMN_TIP)));
                rounds.add(roundRegister);
            }while(cursor.moveToNext());
        }
        return rounds;
    }

    public RoundRegister getByRound(String id_round){
        RoundRegister roundRegister = new RoundRegister();
        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[]= {COLUMN_ID_ROUND};

        Cursor cursor = db.query(TABLE_ROUND,
                colunas,
                COLUMN_ID_ROUND + "=?",
                new String[]{String.valueOf(roundRegister.getId_round())},
                null, null, null, null);

        if(cursor.moveToFirst()){
            roundRegister = new RoundRegister();
            roundRegister.setId_round(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ROUND)));
            roundRegister.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            roundRegister.setPeople(cursor.getInt(cursor.getColumnIndex(COLUMN_PEOPLE)));
            roundRegister.setTotal(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL)));
            roundRegister.setTotalPerPerson(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTALPERPERSON)));
            roundRegister.setTip(cursor.getString(cursor.getColumnIndex(COLUMN_TIP)));
        }
        return roundRegister;
    }

    public int getLastIdRound() {
        int lastId = 1;
        SQLiteDatabase db = banco.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db,TABLE_ROUND);
    }
}
