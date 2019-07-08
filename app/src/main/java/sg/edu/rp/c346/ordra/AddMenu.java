package sg.edu.rp.c346.ordra;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AddMenu extends AppCompatActivity {

    EditText et;
    ImageButton btnAdd;
    Button btnDone;
    ListView lv;
    ArrayList<String> al = new ArrayList<String>();
    ArrayList<String> almenu = new ArrayList<String>();;
    ArrayAdapter<String> aa;
    ArrayList<String> orderNames;
    SQLiteDatabase newDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        et = findViewById(R.id.etItem);
        lv = findViewById(R.id.lv);
        btnAdd = findViewById(R.id.btnAdd2);
        btnDone = findViewById(R.id.btnDone);

        aa = new ArrayAdapter<String>(AddMenu.this, android.R.layout.simple_expandable_list_item_1, al);
        lv.setAdapter(aa);
        aa.notifyDataSetChanged();

        DBHelper db1 = new DBHelper(AddMenu.this);
        SQLiteDatabase dbhello = db1.getWritableDatabase();

        if(intent.getStringExtra("menuName").length() > 0){
            orderNames =  new ArrayList<String>(Arrays.asList(db1.getOrderNames(intent.getStringExtra("menuName"))));
            DBHelper db = new DBHelper(this);
            al = new ArrayList<>(Arrays.asList(db.getOrderNames(intent.getStringExtra("menuName").toLowerCase())));
            al.remove(0);
            al.remove(0);
            for(int i = 0 ; i < al.size() ; i++){
                al.set(i, al.get(i).replaceAll("_"," "));
            }
            aa = new ArrayAdapter<String>(AddMenu.this, android.R.layout.simple_expandable_list_item_1, al);
            lv.setAdapter(aa);
            aa.notifyDataSetChanged();
        }
        else{
            DBHelper.menu = "CREATE TABLE " + name.replaceAll("\\s+","") + "( "
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT";

            DBHelper.name = name.replaceAll("\\s+","");
        }

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub

                AlertDialog.Builder builder = new AlertDialog.Builder(AddMenu.this);

                builder.setTitle(al.get(position));
                builder.setMessage("Are you sure you want to delete?");

                builder.setPositiveButton("YES", (dialog, which) -> {

                    String whichOne = al.get(position);

                    aa.notifyDataSetChanged();

                    for (int i = 0 ; i < almenu.size() ; i++){
                         if(almenu.get(i).contains(al.get(position))){
                            almenu.remove(i);
                            whichOne = al.get(position);
                        }
                    }

                    if(intent.getStringExtra("menuName").length() > 0) {
                        DBHelper db1 = new DBHelper(AddMenu.this);
                        SQLiteDatabase dbhello = db1.getWritableDatabase();
                        orderNames.remove(0);
                        orderNames.remove(0);
                        String columnNames = "";
                        orderNames.remove(al.get(position));
                        for (int i = 0 ; i < orderNames.size() ; i++){
                            if(i != orderNames.size()-1){
                                columnNames += orderNames.get(i) + ", ";
                            } else{
                                columnNames += orderNames.get(i);
                            }
                        }
                        Log.e("removed?", columnNames);
                        String s = "CREATE TEMPORARY TABLE t1_backup(" + columnNames + "); " +
                                "INSERT INTO t1_backup SELECT " + columnNames + " FROM " + intent.getStringExtra("menuName") + "; " +
                                "DROP TABLE " + intent.getStringExtra("menuName") + "; " +
                                "CREATE TABLE " + intent.getStringExtra("menuName") + "(" + columnNames + "); " +
                                "INSERT INTO " + intent.getStringExtra("menuName") + " SELECT " + columnNames + " FROM t1_backup; " +
                                "DROP TABLE t1_backup; ";
                        dbhello.execSQL(s);
                    }

                    al.remove(position);

                    Toast.makeText(AddMenu.this, "Item Deleted", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                });

                builder.setNegativeButton("NO", (dialog, which) -> {

                    dialog.dismiss();
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }

        });

        btnAdd.setOnClickListener((v) -> {
            String text = et.getText().toString();
            et.setText("");
            if(intent.getStringExtra("menuName").length() > 0){
                al.add(text);
                DBHelper db = new DBHelper(AddMenu.this);
                newDb = db.getWritableDatabase();
                String s = "ALTER TABLE " + intent.getStringExtra("menuName") + " ADD COLUMN ";
                s += (text + " INTEGER DEFAULT 0");
                newDb.execSQL(s);
                aa = new ArrayAdapter<String>(AddMenu.this, android.R.layout.simple_expandable_list_item_1, al);
                lv.setAdapter(aa);
                aa.notifyDataSetChanged();
            }
            else{
                if(text.length() != 0){
                    al.add(text);
                    aa = new ArrayAdapter<String>(AddMenu.this, android.R.layout.simple_expandable_list_item_1, al);
                    lv.setAdapter(aa);
                    aa.notifyDataSetChanged();

                    text = text.replaceAll("\\s+","_");

                    almenu.add(", " + text + " INTEGER");
                }
            }
        });

        btnDone.setOnClickListener((v)->{
            if (!(intent.getStringExtra("menuName").length() > 0)){
                for (int i = 0 ; i < almenu.size() ; i++){
                    DBHelper.menu += almenu.get(i);
                }
                DBHelper.menu += " )";

                DBHelper db = new DBHelper(AddMenu.this);
                newDb = db.getWritableDatabase();
                db.onCreate(newDb);
                long row_affected = db.insertMenu(name.replaceAll("\\s+",""), name.replaceAll("\\s+",""));

                if (row_affected != -1) {
                    Toast.makeText(getBaseContext(), "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        });

    }
}
