package com.assignment.restaurantreservation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignment.restaurantreservation.R;
import com.assignment.restaurantreservation.models.Reservation;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class reservationAdapter extends FirestoreRecyclerAdapter<Reservation, reservationAdapter.reservationHolder> {
    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public reservationAdapter(@NonNull FirestoreRecyclerOptions<Reservation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull reservationHolder holder, int position, @NonNull Reservation model) {
        holder.textViewDate.setText(String.valueOf(model.getReserve_date()));
        holder.textViewTime.setText(String.valueOf(model.getReserve_time()));
        holder.textViewCust.setText("Somebody");
    }

    @NonNull
    @Override
    public reservationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,
                parent, false);
        return new reservationHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().update("removed", true);
    }

    class reservationHolder extends RecyclerView.ViewHolder{
        TextView textViewDate;
        TextView textViewTime;
        TextView textViewCust;


        public reservationHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.text_date);
            textViewTime = itemView.findViewById(R.id.text_time);
            textViewCust = itemView.findViewById(R.id.text_account);

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

    public void setOnItemClickedListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
