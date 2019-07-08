package sg.edu.rp.c346.ordra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentList extends Fragment{

    ListView lv;
    ArrayAdapter<String> aa;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_list, container, false);
        lv = view.findViewById(R.id.lv);


        DBHelper dbh = new DBHelper(getContext());

        ArrayList<String> arrayList = dbh.getName();
        for(int i = 0 ; i < arrayList.size() ; i++){
            String cap = arrayList.get(i).substring(0, 1).toUpperCase() + arrayList.get(i).substring(1);
            arrayList.set(i, cap);
        }
        arrayList.remove(0);
        aa = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, arrayList);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OrdersActivity.class);
                String menuName = arrayList.get(position);
                intent.putExtra("menuName", menuName);
                startActivity(intent);
            }
        });

        return view;
    }

}
