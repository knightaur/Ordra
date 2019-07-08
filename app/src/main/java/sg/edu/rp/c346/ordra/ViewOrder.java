package sg.edu.rp.c346.ordra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewOrder extends AppCompatActivity {

    ListView lv;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        tv = findViewById(R.id.tvMenuName3);
        lv = findViewById(R.id.lv);
        Intent intent = getIntent();

        String menuName = intent.getStringExtra("menuName");
        tv.setText(menuName);

        DBHelper db = new DBHelper(this);
        ArrayList<String> tableNames = db.getTableNames(menuName);
        tableNames.remove(0);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, tableNames);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ViewOrder.this, NewOrder.class);
                String tableName = tableNames.get(position);
                i.putExtra("tableName", tableName);
                i.putExtra("menuName", menuName);
                startActivity(i);
            }
        });


    }
}
