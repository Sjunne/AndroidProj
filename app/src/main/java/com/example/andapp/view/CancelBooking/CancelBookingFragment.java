package com.example.andapp.view.CancelBooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andapp.R;
import com.example.andapp.view.CancelBooking.CancelBookingAdapter;
import com.example.andapp.viewmodel.BookingViewModel;

public class CancelBookingFragment extends Fragment {
    private BookingViewModel bookingViewModel;
    private CancelBookingAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.cancel_bookings_fragment, container, false);

        //initializing my recycler view
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_cancel_booking);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        //initializing my adapter to fill up my recycler view
        adapter = new CancelBookingAdapter(this);


        //binding
        recyclerView.setAdapter(adapter);

        //viewmodels
        bookingViewModel = new ViewModelProvider(this).get(BookingViewModel.class);
        bookingViewModel.getBookings().observe(getViewLifecycleOwner(), adapter::updateList);
        adapter.notifyDataSetChanged();

        return root;
    }


    public void cancelBooking(String category, String time, String date) {
        bookingViewModel.cancelBooking(category, time, date);
    }
}
