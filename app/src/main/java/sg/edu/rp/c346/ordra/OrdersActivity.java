package sg.edu.rp.c346.ordra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrdersActivity extends AppCompatActivity {

    TextView tvName;
    Button btnNew, btnView, btnEdit;
    Intent go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        tvName = findViewById(R.id.tvMenuName);
        btnNew = findViewById(R.id.btnNew);
        btnView = findViewById(R.id.btnOrder);
        btnEdit = findViewById(R.id.btnEdit);

        Intent intent = getIntent();
        String menuName = intent.getStringExtra("menuName");

        tvName.setText(menuName);

        btnNew.setOnClickListener((v)->{
            go = new Intent(OrdersActivity.this, NewOrder.class);
            go.putExtra("menuName", menuName);
            go.putExtra("tableName", "");
            startActivity(go);
        });

        btnView.setOnClickListener((v)->{
            go = new Intent(OrdersActivity.this, ViewOrder.class);
            go.putExtra("menuName", menuName);
            startActivity(go);
        });

        btnEdit.setOnClickListener((v)->{
            go = new Intent(OrdersActivity.this, AddMenu.class);
            go.putExtra("menuName", menuName);
            startActivity(go);
        });

    }
}
