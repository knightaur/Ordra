package sg.edu.rp.c346.ordra;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class NewOrder extends AppCompatActivity implements OrderAdapter.clickItemListener{

    RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> orderNames = new ArrayList<>();
    private ArrayList<Integer> quantities = new ArrayList<>();
    private ArrayList<Integer> newQuantity = new ArrayList<>();
    private ArrayList<String> order = new ArrayList<>();
    ArrayList<Integer> intArray;
    TextView tv;
    Button btnSubmit;
    EditText etTableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        tv = findViewById(R.id.tvMenuName2);
        btnSubmit = findViewById(R.id.btnSubmit);
        etTableName = findViewById(R.id.etTableName);

        Intent intent = getIntent();
        String tableName = intent.getStringExtra("menuName");
        String ttName = intent.getStringExtra("tableName");

        etTableName.setText(ttName);

        tv.setText(tableName);

        DBHelper db = new DBHelper(this);

        orderNames = new ArrayList<String>(Arrays.asList(db.getOrderNames(tableName.toLowerCase())));
        quantities = db.getEmptyQuantity(tableName.toLowerCase());
        orderNames.remove(0);
        orderNames.remove(0);

        order = orderNames;

        for(int i = 0 ; i < orderNames.size() ; i++){
            orderNames.set(i, orderNames.get(i).replaceAll("_"," "));
        }

        rv = (RecyclerView) findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        if(ttName.length() > 0){
            etTableName.setEnabled(false);
            etTableName.setFocusable(false);
            etTableName.setCursorVisible(false);
            etTableName.setKeyListener(null);
            etTableName.setBackgroundColor(Color.TRANSPARENT);
            intArray = db.getValues(ttName,tableName);
            intArray.remove(0);
            mAdapter = new OrderAdapter(this, orderNames, intArray, NewOrder.this);
        }else{
            mAdapter = new OrderAdapter(this, orderNames, quantities, NewOrder.this);
        }
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(layoutManager);

        btnSubmit.setOnClickListener((v)->{
            for(int i = 0 ; i < orderNames.size() ; i++){
                order.set(i, orderNames.get(i).replaceAll("\\s","_"));
            }
            boolean isEmpty = true;
            String tName = etTableName.getText().toString();


            for(int i : newQuantity){
                if(i != 0){
                    isEmpty = false;
                    break;
                }
            }


            if(ttName.length() > 0) {
                if(!isEmpty){
                    SQLiteDatabase dbh = db.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    for (int i = 0 ; i < newQuantity.size() ; i++){
                        values.put(order.get(i), newQuantity.get(i));
                    }
                    dbh.update(tableName, values, "name='"+ttName+"'", null);
                    dbh.close();
                    Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(this, "Empty Order!", Toast.LENGTH_SHORT).show();
                }
            }else{
                if(!isEmpty){
                    newQuantity.remove(newQuantity.size()-1);
                    SQLiteDatabase dbh = db.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("name", tName);
                    for (int i = 0 ; i < newQuantity.size() ; i++){
                        values.put(order.get(i), newQuantity.get(i));
                    }
                    long result = dbh.insert(tableName, null, values);
                    dbh.close();
                    Toast.makeText(this, "Inserted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(this, "Empty Order!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onItemClick(ArrayList<Integer> a) {
        newQuantity = a;
    }
}
