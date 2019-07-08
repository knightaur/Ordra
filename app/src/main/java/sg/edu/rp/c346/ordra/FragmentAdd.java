package sg.edu.rp.c346.ordra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class FragmentAdd extends Fragment{

    ImageButton btnAdd;
    EditText et;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_add, container, false);

        et = view.findViewById(R.id.etMenuName);
        btnAdd = view.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener((v) -> {
            if(et.getText().length() != 0){
                Intent intent = new Intent(getActivity(), AddMenu.class);
                intent.putExtra("name", et.getText().toString());
                intent.putExtra("menuName", "");
                et.setText("");
                startActivity(intent);
            }
        });

        return (view);
    }

}
