package tiemy.android.br.com.beersplitapp.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import tiemy.android.br.com.beersplitapp.model.Usuario;

public class UsuarioDAO {
    private DBOpenHelper banco;

    public UsuarioDAO(Context context){

        banco = new DBOpenHelper(context);
    }

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public List<Usuario> getAll(){
        List<Usuario> usuarios = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Usuario user = null;
        if(cursor.moveToFirst()){
            do{
                user = new Usuario();
                user.setUsuario(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
                user.setSenha(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                usuarios.add(user);
            }while(cursor.moveToNext());
        }
        db.close();
        return usuarios;
    }

    public boolean getByUsername(Usuario usuario){

        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[]= {COLUMN_USERNAME, COLUMN_PASSWORD};

        Cursor cursor = db.query(TABLE_USERS,
                colunas,
                COLUMN_USERNAME + "=? and " + COLUMN_PASSWORD + "=?",
                new String[]{usuario.getUsuario(), usuario.getSenha()},
                null, null, null, null);

        if(cursor.moveToFirst()){
            return true;
        }else
            return false;
    }

    public boolean naoExisteUsuarioCadastrado(){

        SQLiteDatabase db = banco.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db,TABLE_USERS)==0;
    }

    public String add(Usuario user){
        long result;
        SQLiteDatabase db = banco.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, user.getUsuario());
        cv.put(COLUMN_PASSWORD, user.getSenha());

        result = db.insert(TABLE_USERS, null, cv);

        db.close();

        if(result == -1)
            return "Erro ao inserir usuário";
        else
            return "Usuário cadastrado com sucesso";

    }
}
