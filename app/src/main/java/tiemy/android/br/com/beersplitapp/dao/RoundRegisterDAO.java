package tiemy.android.br.com.beersplitapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import tiemy.android.br.com.beersplitapp.model.Expense;
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
    public static final String COLUMN_TOTAL_TIP = "totalTip";
    public static final String COLUMN_TOTALPERPERSON = "totalPerPerson";
    public static final String COLUMN_TOTALPERPERSONTIP = "totalPerPersonTips";

    public String add(RoundRegister roundRegister){
        long result;
        SQLiteDatabase db = banco.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID_ROUND, roundRegister.getId_round());
        cv.put(COLUMN_NAME, roundRegister.getName());
        cv.put(COLUMN_PEOPLE, roundRegister.getPeople().intValue());
        cv.put(COLUMN_TOTAL, roundRegister.getTotal().doubleValue());
        cv.put(COLUMN_TOTAL_TIP, roundRegister.getTotalTip().doubleValue());
        cv.put(COLUMN_TOTALPERPERSON, roundRegister.getTotalPerPerson().doubleValue());
        cv.put(COLUMN_TOTALPERPERSONTIP, roundRegister.getTotalPerPerson().doubleValue());

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
                roundRegister.setPeople(new BigDecimal(cursor.getInt(cursor.getColumnIndex(COLUMN_PEOPLE))));
                roundRegister.setTotal(new BigDecimal(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL))));
                roundRegister.setTotalTip(new BigDecimal(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL_TIP))));
                roundRegister.setTotalPerPerson(new BigDecimal(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTALPERPERSON))));
                roundRegister.setTotalPerPersonTips(new BigDecimal(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTALPERPERSONTIP))));
                rounds.add(roundRegister);
            }while(cursor.moveToNext());
        }
        return rounds;
    }

    public RoundRegister getByRound(String id_round){
        RoundRegister roundRegister = new RoundRegister();
        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[]= {COLUMN_ID_ROUND, COLUMN_NAME, COLUMN_PEOPLE, COLUMN_TOTAL,
                COLUMN_TOTAL_TIP, COLUMN_TOTALPERPERSON, COLUMN_TOTALPERPERSONTIP};

        Cursor cursor = db.query(TABLE_ROUND,
                colunas,
                COLUMN_ID_ROUND + "=?",
                new String[]{id_round},
                null, null, null, null);

        if(cursor.moveToFirst()){
            roundRegister = new RoundRegister();
            roundRegister.setId_round(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ROUND)));
            roundRegister.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            roundRegister.setPeople(new BigDecimal(cursor.getInt(cursor.getColumnIndex(COLUMN_PEOPLE))));
            roundRegister.setTotal(new BigDecimal(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL))));
            roundRegister.setTotalTip(new BigDecimal(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL_TIP))));
            roundRegister.setTotalPerPerson(new BigDecimal(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTALPERPERSON))));
            roundRegister.setTotalPerPersonTips(new BigDecimal(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTALPERPERSONTIP))));
        }
        return roundRegister;
    }

    public int getLastIdRound() {
//        int lastId = 1;
//        SQLiteDatabase db = banco.getReadableDatabase();
//        return (int) DatabaseUtils.queryNumEntries(db,TABLE_ROUND);

        int lastId = 0;
        String query = "SELECT max(" + COLUMN_ID_ROUND + ") FROM " + TABLE_ROUND;
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            lastId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ROUND));
        }
        return lastId;
    }

    public String delete(int id_round){
        long result;
        SQLiteDatabase db = banco.getReadableDatabase();

        String where;

        where = COLUMN_ID_ROUND + " = " + id_round;
        result = db.delete(TABLE_ROUND, where, null);

        db.close();

        if(result == -1)
            return "Erro ao apagar rodada";
        else
            return "Rodada apagada com sucesso";
    }
}
