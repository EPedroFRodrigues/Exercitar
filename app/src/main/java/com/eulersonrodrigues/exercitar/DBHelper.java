package com.eulersonrodrigues.exercitar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String EXERCICIOS_TABLE_NAME = "exercicios";
    public static final String EXERCICIOS_COLUMN_DATA = "data";
    public static final String EXERCICIOS_COLUMN_CARDIO = "cardio";
    public static final String EXERCICIOS_COLUMN_ESPORTES = "esportes";
    public static final String EXERCICIOS_COLUMN_MUSCULACAO = "musculacao";
    public static final String EXERCICIOS_COLUMN_TOTAL = "total";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table exercicios " +
                        "(data text primary key,cardio text,esportes text, musculacao text,total text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS exercicios");
        onCreate(db);
    }

    public boolean insertValue (String data, String cardio, String esportes, String musculacao,String total)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("data", data);
        contentValues.put("cardio", cardio);
        contentValues.put("esportes", esportes);
        contentValues.put("musculacao", musculacao);
        contentValues.put("total", total);
        db.insert("exercicios", null, contentValues);
        return true;
    }

    public Cursor getData(String data){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from exercicios where data = ?", new String[] { data });
        if (res != null)
            res.moveToFirst();
        return res;
    }

    public Cursor total(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT COUNT(*) FROM exercicios", null);
        res.moveToFirst();
        return res;
    }


    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, EXERCICIOS_TABLE_NAME);
        return numRows;
    }

    public boolean updateValue (String data, String cardio, String esportes, String musculacao,String total)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("data", data);
        contentValues.put("cardio", cardio);
        contentValues.put("esportes", esportes);
        contentValues.put("musculacao", musculacao);
        contentValues.put("total", total);
        db.update("exercicios", contentValues, "data = ? ", new String[] { data } );
        return true;
    }

    public Integer deleteValue (String data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("exercicios",
                "data = ? ",
                new String[] { data });
    }

    public List<HashMap<String, String>> getAllValues()
    {
        ArrayList<String> array_list = new ArrayList<String>();
        List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM exercicios ORDER BY cast(TOTAL as REAL) DESC", null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            String data = res.getString(res.getColumnIndex(EXERCICIOS_COLUMN_DATA));
            String cardio = res.getString(res.getColumnIndex(EXERCICIOS_COLUMN_CARDIO));
            String esportes = res.getString(res.getColumnIndex(EXERCICIOS_COLUMN_ESPORTES));
            String musculacao = res.getString(res.getColumnIndex(EXERCICIOS_COLUMN_MUSCULACAO));
            String total = res.getString(res.getColumnIndex(EXERCICIOS_COLUMN_TOTAL));
            HashMap<String,String> temp = new HashMap<String,String>();
            temp.put("data", data);
            temp.put("cardio", cardio);
            temp.put("esportes", esportes);
            temp.put("musculacao", musculacao);
            temp.put("total", total);
            list.add(temp);
            //array_list.add(res.getString(res.getColumnIndex(EXERCICIOS_COLUMN_DATA)));
            res.moveToNext();
        }
        return list;
    }
}