package tiemy.android.br.com.beersplitapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tiemy.android.br.com.beersplitapp.model.Expense;

public class ExpenseDAO {
    private DBOpenHelper banco;

    public ExpenseDAO(Context context){

        banco = new DBOpenHelper(context);
    }

    public static final String TABLE_EXPENSE = "expenses";
    public static final String COLUMN_ID_ROUND = "id_round";
    public static final String COLUMN_NAME = "nameExpense";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QUANTITY = "quantity";

    public String add(Expense expense){
        long result;
        SQLiteDatabase db = banco.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID_ROUND, expense.getId_round());
        cv.put(COLUMN_NAME, expense.getNameExpense().toUpperCase());
        cv.put(COLUMN_PRICE, expense.getPrice());
        cv.put(COLUMN_QUANTITY, expense.getQuantity());

        result = db.insert(TABLE_EXPENSE, null, cv);

        db.close();

        if(result == -1)
            return "Erro ao inserir desepesa";
        else
            return "Despesa cadastrada com sucesso";
    }

    public String update(String expenseName, Expense expense){
        long result;
        SQLiteDatabase db = banco.getReadableDatabase();
        ContentValues cv = new ContentValues();
        String where;

        //cv.put(COLUMN_ID_ROUND, expense.getId_round());
        cv.put(COLUMN_NAME, expense.getNameExpense());
        cv.put(COLUMN_PRICE, expense.getPrice());
        cv.put(COLUMN_QUANTITY, expense.getQuantity());

        where = "UPPER(" + COLUMN_NAME + ") = UPPER('" + expenseName + "')";
        result = db.update(TABLE_EXPENSE, cv, where, null);

        db.close();

        if(result == -1)
            return "Erro ao atualizar desepesa";
        else
            return "Despesa atualizada com sucesso";
    }

    public String delete(Expense expense){
        long result;
        SQLiteDatabase db = banco.getReadableDatabase();

        String where;

        where = "UPPER(" + COLUMN_NAME + ") = UPPER('" + expense.getNameExpense() + "') and "
                + COLUMN_ID_ROUND + " = " + expense.getId_round();
        result = db.delete(TABLE_EXPENSE, where, null);

        db.close();

        if(result == -1)
            return "Erro ao apagar desepesa";
        else
            return "Despesa apagada com sucesso";
    }

    public List<Expense> getAll(int id_round){
        List<Expense> expenses = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_EXPENSE + " where " + COLUMN_ID_ROUND + "="+id_round;
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Expense expense = null;
        if(cursor.moveToFirst()){
            do{
                expense = new Expense();
                expense.setId_round(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ROUND)));
                expense.setNameExpense(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                expense.setPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)));
                expense.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)));
                expenses.add(expense);
            }while(cursor.moveToNext());
        }
        return expenses;
    }

    public Expense getByExpense(String expenseName){
        Expense expense = new Expense();

        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[]= {COLUMN_ID_ROUND, COLUMN_NAME, COLUMN_PRICE, COLUMN_QUANTITY};

        Cursor cursor = db.query(TABLE_EXPENSE,
                colunas,
                "UPPER(" + COLUMN_NAME + ") =? ",
                new String[]{expenseName.toUpperCase()},
                null, null, null, null);

        if(cursor.moveToFirst()){
            expense = new Expense();
            expense.setId_round(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ROUND)));
            expense.setNameExpense(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            expense.setPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)));
            expense.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)));

        }
            return expense;
    }

}
