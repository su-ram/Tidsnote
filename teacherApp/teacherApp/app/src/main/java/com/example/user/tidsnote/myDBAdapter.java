package com.example.user.tidsnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static com.example.user.tidsnote.Message2.message;


public class myDBAdapter {
    myDbHelper myhelper;
    public myDBAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String name, String classes, String age, String pnum, String memo)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.CLASS, classes);
        contentValues.put(myDbHelper.AGE, age);
        contentValues.put(myDbHelper.PNUM, pnum);
        contentValues.put(myDbHelper.MEMO, memo);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.NAME, myDbHelper.CLASS, myDbHelper.AGE, myDbHelper.PNUM, myDbHelper.MEMO};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String  classes =cursor.getString(cursor.getColumnIndex(myDbHelper.CLASS));
            String  age =cursor.getString(cursor.getColumnIndex(myDbHelper.AGE));
            String  pnum =cursor.getString(cursor.getColumnIndex(myDbHelper.PNUM));
            String  memo =cursor.getString(cursor.getColumnIndex(myDbHelper.MEMO));
            buffer.append(cid+ "   " + name + "   " + classes+"   " +age+"   " +pnum+"   " +memo +" \n");
        }

        return buffer.toString();
    }
    public ArrayList<String> getName(){
        SQLiteDatabase db=myhelper.getWritableDatabase();
        String[] columns = {myhelper.UID, myhelper.NAME,myhelper.CLASS};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);

        ArrayList<String> name_bf=new ArrayList<>();

        int i=0;
        while (cursor.moveToNext())
        {
            String name =cursor.getString(cursor.getColumnIndex(myhelper.NAME));
            name_bf.add(name);
        }

        return name_bf;
    }


    public ArrayList<String> getClasses(){
        SQLiteDatabase db=myhelper.getWritableDatabase();
        String[] columns = {myhelper.UID, myhelper.NAME,myhelper.CLASS};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);

        ArrayList<String> class_bf=new ArrayList<>();

        int i=0;
        while (cursor.moveToNext())
        {
            String name =cursor.getString(cursor.getColumnIndex(myhelper.CLASS));
            class_bf.add(name);
        }

        return class_bf;
    }



    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "stdTbl";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String CLASS= "Classes";
        private static final String AGE= "Age";
        private static final String PNUM= "Pnum";
        private static final String MEMO= "Memo";// Column III
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+CLASS+" VARCHAR(225),"+AGE+" VARCHAR(225),"+PNUM+" VARCHAR(255),"+MEMO+" VARCHAR(255));";
        private static final String INSERT_DATA="INSERT INTO "+TABLE_NAME+" VALUES("+UID+","+NAME+","+CLASS+","+AGE+","+PNUM+","+MEMO+")";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);

            } catch (Exception e) {
                message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                message(context,""+e);
            }
        }
    }
}
