package tiemy.android.br.com.beersplitapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import tiemy.android.br.com.beersplitapp.model.Expense;

public class ExpenseDAO {
    private DBOpenHelper banco;

    public ExpenseDAO(Context context){

        banco = new DBOpenHelper(context);
    }

    public static final String TABLE_EXPENSE = "expense";
    public static final String COLUMN_ID_ROUND = "id_round";
    public static final String COLUMN_NAME = "nameExpense";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QUANTITY = "quantity";

    public String add(Expense expense){
        long result;
        SQLiteDatabase db = banco.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID_ROUND, expense.getId_round());
        cv.put(COLUMN_NAME, expense.getNameExpense());
        cv.put(COLUMN_PRICE, expense.getPrice());
        cv.put(COLUMN_QUANTITY, expense.getQuantity());

        result = db.insert(TABLE_EXPENSE, null, cv);

        db.close();

        if(result == -1)
            return "Erro ao inserir desepesa";
        else
            return "Despesa cadastrada com sucesso";
    }

    public List<Expense> getAll(){
        List<Expense> expenses = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_EXPENSE;
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
}
