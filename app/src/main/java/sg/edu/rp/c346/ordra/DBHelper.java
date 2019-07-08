package sg.edu.rp.c346.ordra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;


public class DBHelper extends SQLiteOpenHelper {

    public static String menu = "";
    public static String name = "name";

    private static final String DATABASE_NAME = "order.db";
    private static final int DATABASE_VERSION = 3;

    public static String TABLE_ORDER = "'order'";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_ORDER + "( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT ";
        if(menu.length() == 0){
            createNoteTableSql += ")";
            db.execSQL(createNoteTableSql);
        }else{
            Log.e("IDIOTMENU1", menu);
            db.execSQL(menu);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(menu.length() == 0){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER );
        }
        else{
            db.execSQL("DROP TABLE IF EXISTS " + name );
        }
        onCreate(db);
    }

    public long insertMenu(String name, String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        long result = db.insert(table, null, values);
        db.close();
        return result;
    }

    public ArrayList<String> getTableNames(String table) {
        ArrayList<String> tables = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_NAME};
        Cursor cursor1 = db.query(table, columns, null, null,
                null, null, null, null);

        if (cursor1.moveToFirst()) {
            do {
                String name = cursor1.getString(0);
                tables.add(name);
            } while (cursor1.moveToNext());
        }
        cursor1.close();
        db.close();
        return tables;
    }

    public String[] getOrderNames(String table) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = db.query(table, null, null, null,
                null, null, null, null);

        String[] columnNames = cursor1.getColumnNames();
        cursor1.close();
        db.close();
        return columnNames;
    }

    public ArrayList<Integer> getEmptyQuantity(String table){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(table, null, null, null,
                null, null, null, null);

        c.moveToFirst();

        ArrayList<Integer> quantities = new ArrayList<>();
        for(int i = 1 ; i < c.getColumnCount(); i++) {
            int quantity = c.getInt(i);
            quantities.add(quantity);
        }
        c.close();
        db.close();
        return quantities;
    }

    public ArrayList<String> getName() {
        ArrayList<String> tables = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                tables.add(c.getString(0));
                c.moveToNext();
            }
        }

        tables.remove("android_metadata");
        tables.remove("sqlite_sequence");

        c.close();
        db.close();
        return tables;
    }

    public ArrayList<Integer> getValues(String tableName, String menuName) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + menuName + " WHERE name LIKE '%" + tableName + "%'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                for(int i = 1 ;  i < c.getColumnCount(); i++) {
                    int quantity = c.getInt(i);
                    array.add(quantity);
                }
                c.moveToNext();
            }
        }

        c.close();
        db.close();
        return array;
    }

}