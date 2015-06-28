package com.disaintworking.konnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Swaggiott on 3/3/2015.
 */
public class DatabaseOps extends SQLiteOpenHelper{

    public static final int database_version = 1;


    public DatabaseOps(Context context) {
        super(context, PeopleData.DataInfo.TABLE_NAME, null, database_version);
        Log.d("DATA", "DATADONE");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + PeopleData.DataInfo.TABLE_NAME+ " ( "+
                PeopleData.DataInfo.NAME + " TEXT , " +
                PeopleData.DataInfo.NUMBER + " TEXT , " +
                PeopleData.DataInfo.URI + " TEXT  " +
                ");";

        db.execSQL(CREATE_QUERY);
        Log.d("DATA", "DATADONE, TABLE CREATED");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PeopleData.DataInfo.TABLE_NAME);
        onCreate(db);
    }

    public void add (String name, String number, String uri) {
        SQLiteDatabase SQ = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(PeopleData.DataInfo.NAME, name);
        cv.put(PeopleData.DataInfo.NUMBER, number);
        cv.put(PeopleData.DataInfo.URI, uri);

        SQ.insert(PeopleData.DataInfo.TABLE_NAME, null, cv);
        SQ.close();
        Log.d("DATA", "DATADONE, DATA INPUTED PEOPLE = " + name);
    }

    public People getPeople(String name){
        SQLiteDatabase SQ = this.getReadableDatabase();
        String [] columns = {PeopleData.DataInfo.NAME, PeopleData.DataInfo.NUMBER};

        Cursor CR = SQ.query(PeopleData.DataInfo.TABLE_NAME, columns, " name = ", null, null, null, null);

        if (CR != null)
            CR.moveToFirst();

        People guy = new People(CR.getString(0), CR.getString(1), CR.getString(2));

        return guy;
    }

    public List <People> getALlPeople(){
        List<People> guyList= new LinkedList<People>();

        String query= "SELECT  * FROM " + PeopleData.DataInfo.TABLE_NAME;

        SQLiteDatabase SQ = this.getWritableDatabase();
        Cursor CR = SQ.rawQuery(query, null);

        People guy = null;
        if (CR.moveToFirst()){
            do{
                guy = new People(CR.getString(0), CR.getString(1), CR.getString(2));

                guyList.add(guy);
            } while (CR.moveToNext());
        }
    return guyList;
    }

    public int updatePeople(People guy){
        SQLiteDatabase SQ = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(PeopleData.DataInfo.NUMBER, guy.getNumber());

        int i = SQ.update(PeopleData.DataInfo.TABLE_NAME,
                cv,
                PeopleData.DataInfo.NAME+" = ?",
                new String[] { String.valueOf(guy.getName())}
                );

        SQ.close();

        return i;
    }

    public void deletePeople (String guy){

        SQLiteDatabase SQ = this.getWritableDatabase();

        SQ.delete(PeopleData.DataInfo.TABLE_NAME,
                PeopleData.DataInfo.NAME+" = ?",
                new String[] { String.valueOf(guy) });

        SQ.close();
    }




    }


