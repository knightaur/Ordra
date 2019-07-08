package sg.edu.rp.c346.ordra;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> orderNames = new ArrayList<>();
    private ArrayList<Integer> quantities = new ArrayList<>();
    private Context mContext;
    private ArrayList<Integer> newQuantity = new ArrayList<>();
    private clickItemListener clickItemListeners;

    public OrderAdapter(Context mContext, ArrayList<String> orderNames, ArrayList<Integer> quantities, clickItemListener clickItemListeners) {
        this.orderNames = orderNames;
        this.quantities = quantities;
        this.mContext = mContext;
        this.clickItemListeners = clickItemListeners;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_orderlist, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvName.setText(orderNames.get(i));
        viewHolder.tvQuantity.setText(quantities.get(i) + "");

        newQuantity = quantities;

        viewHolder.btnAddUp.setOnClickListener((v)->{
            viewHolder.tvQuantity.setText((quantities.get(i)+1) + "");
            newQuantity.set(i,quantities.get(i)+1);
            clickItemListeners.onItemClick(newQuantity);

        });

        viewHolder.btnMinusDown.setOnClickListener((v)->{
            if(!(Integer.parseInt(viewHolder.tvQuantity.getText().toString()) < 1)){
                viewHolder.tvQuantity.setText((quantities.get(i)-1) + "");
                newQuantity.set(i,quantities.get(i)-1);
                clickItemListeners.onItemClick(newQuantity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvQuantity;
        Button btnAddUp, btnMinusDown;
        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvOrder);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnAddUp = itemView.findViewById(R.id.btnAddUp);
            btnMinusDown =  itemView.findViewById(R.id.btnMinusDown);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }

    interface clickItemListener {
        void onItemClick(ArrayList<Integer> a);
    }

}
