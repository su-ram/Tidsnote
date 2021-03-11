package com.example.user.tidsnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static com.example.user.tidsnote.Message2.message;


public class postDBAdapter {
    myDbHelper posthelper;
    public postDBAdapter(Context context)
    {
        posthelper = new myDbHelper(context);
    }

    public long insertData(String title, String notice,String date)
    {
        SQLiteDatabase dbb = posthelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(posthelper.TITLE, title);
        contentValues.put(posthelper.NOTICE, notice);

        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getData()
    {
        SQLiteDatabase db = posthelper.getWritableDatabase();
        String[] columns = {posthelper.UID, posthelper.TITLE,posthelper.NOTICE,posthelper.DATE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            String title =cursor.getString(cursor.getColumnIndex(myDbHelper.TITLE));
            buffer.append(title +" \n");
        }
        return buffer.toString();
    }
    public ArrayList<String> getTitle(){
        SQLiteDatabase db=posthelper.getWritableDatabase();
        String[] columns = {posthelper.UID, posthelper.TITLE,posthelper.DATE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);

        ArrayList<String> title_bf=new ArrayList<>();

        int i=0;
        while (cursor.moveToNext())
        {
            String title =cursor.getString(cursor.getColumnIndex(posthelper.TITLE));
            title_bf.add(title);
        }

        return title_bf;
    }

    public ArrayList<String> getNotice(){
        SQLiteDatabase db=posthelper.getWritableDatabase();
        String[] columns = {posthelper.UID, posthelper.TITLE,posthelper.NOTICE,posthelper.DATE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);

        ArrayList<String> notice_bf=new ArrayList<>();

        int i=0;
        while (cursor.moveToNext())
        {
            String title =cursor.getString(cursor.getColumnIndex(posthelper.NOTICE));
            notice_bf.add(title);
        }

        return notice_bf;
    }

    public ArrayList<String> getDate(){
        SQLiteDatabase db=posthelper.getWritableDatabase();
        String[] columns = {posthelper.UID, posthelper.TITLE,posthelper.NOTICE,posthelper.DATE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);

        ArrayList<String> date_bf=new ArrayList<>();

        int i=0;
        while (cursor.moveToNext())
        {
            String title =cursor.getString(cursor.getColumnIndex(posthelper.DATE));
            date_bf.add(title);
        }

        return date_bf;
    }






    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "postDB3";    // Database Name
        private static final String TABLE_NAME = "noticeTbl";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String TITLE = "Title";    //Column II
        private static final String NOTICE= "Notice";
        private static final String DATE= "Date";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TITLE+" VARCHAR(255) ,"+NOTICE+" VARCHAR(225),"+DATE+" VARCAHR(225));";
        private static final String INSERT_DATA="INSERT INTO "+TABLE_NAME+" VALUES("+UID+","+TITLE+","+NOTICE+","+DATE+")";
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
