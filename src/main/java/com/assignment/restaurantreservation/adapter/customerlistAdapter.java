package com.assignment.restaurantreservation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignment.restaurantreservation.R;
import com.assignment.restaurantreservation.models.Account;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class customerlistAdapter extends FirestoreRecyclerAdapter<Account, customerlistAdapter.customerHolder> {

    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public customerlistAdapter(@NonNull FirestoreRecyclerOptions<Account> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull customerlistAdapter.customerHolder holder, int position, @NonNull Account model) {
       // holder.textViewName.setText(model.()));
       // holder.textViewEmail.setText(String.valueOf(model.getCreated_time().toDate()));
       // holder.textViewMobile.setText("Somebody");
    }

    @NonNull
    @Override
    public customerlistAdapter.customerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customerlist_item,
                parent, false);
        return new customerHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class customerHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewEmail;
        TextView textViewMobile;


        public customerHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_custName);
            textViewEmail = itemView.findViewById(R.id.text_custEmail);
            textViewMobile = itemView.findViewById(R.id.text_cust_mobileNo);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION && listener != null){
                        listener.OnItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickedListener(customerlistAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
