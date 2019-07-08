package sg.edu.rp.c346.ordra;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment f1 = new FragmentList();
                    ft.replace(R.id.frame, f1);
                    ft.commit();
                    return true;
                case R.id.navigation_add:
                    fm = getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    Fragment f2 = new FragmentAdd();
                    ft.replace(R.id.frame, f2);
                    ft.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getWindow().setBackgroundDrawableResource(R.drawable.listpage);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f1 = new FragmentList();
        ft.replace(R.id.frame, f1);
        ft.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled},
                new int[] {-android.R.attr.state_enabled}
        };

        int[] colors = new int[] {
                Color.WHITE,
                Color.GRAY
        };

        ColorStateList myList = new ColorStateList(states, colors);

        navigation.setItemIconTintList(myList);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
