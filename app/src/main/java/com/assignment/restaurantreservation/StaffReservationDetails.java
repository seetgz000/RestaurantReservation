package com.assignment.restaurantreservation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.assignment.restaurantreservation.adapter.reservationAdapter;
import com.assignment.restaurantreservation.models.Reservation;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class StaffReservationDetails extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("reservations");

    private reservationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_allreservationdetails);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = notebookRef
                .whereEqualTo("removed", false)
                .orderBy("reserve_date", Query.Direction.DESCENDING)
                .orderBy("reserve_time", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Reservation> Options = new FirestoreRecyclerOptions.Builder<Reservation>()
                .setQuery(query, Reservation.class)
                .build();

        adapter = new reservationAdapter(Options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickedListener(new reservationAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
                //String path = documentSnapshot.getReference().getPath();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}





















//        findView();
//        initView();
        //gestureDetector = new GestureDetector(this);



//    private void findView() {
//        scrollView= findViewById(R.id.scrollView2);
//    }
//
//    private void initView() {
//        scrollView.setOnTouchListener(onTouchListener);
//    }
//
//    float startY;
//    float endY;
//
//    View.OnTouchListener onTouchListener =new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            switch (motionEvent.getAction()){
//                case MotionEvent.ACTION_DOWN:
//                    startY=motionEvent.getY();
//                    break;
//                case MotionEvent.ACTION_UP:
//                    endY=motionEvent.getY();
//                    break;
//                default:
//                    if (endY-startY>50){
//
//                    }else {
//
//                    }
//            }
//            return false;
//        }
//    };

