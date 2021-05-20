package com.example.andapp.view.CancelBooking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andapp.R;
import com.example.andapp.model.Booking;

import java.util.ArrayList;
import java.util.List;

public class CancelBookingAdapter extends RecyclerView.Adapter<CancelBookingAdapter.ViewHolder> {
    private List<Booking> bookings = new ArrayList<>();
    private CancelBookingFragment cancelBookingFragment;

    public CancelBookingAdapter(CancelBookingFragment cancelBookingFragment) {
        this.cancelBookingFragment = cancelBookingFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cancel_booking_item, parent, false);
        //ViewModel initializing
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date.setText(bookings.get(position).getDate());
        holder.time.setText(bookings.get(position).getTimeBooked().replaceAll(":", "/"));
        holder.textViewEquipmentName.setText(bookings.get(position).getCategory());
        holder.cancelButton.setOnClickListener(v -> {
            cancelBookingFragment.cancelBooking(holder.textViewEquipmentName.getText().toString(), holder.time.getText().toString(), holder.date.getText().toString());
            Toast.makeText(cancelBookingFragment.getContext(), "Successfully canceled Booking, please reload page", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public void updateList(List<Booking> bookings) {
        this.bookings = bookings;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEquipmentName;
        Button cancelButton;
        TextView date;
        TextView time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEquipmentName = itemView.findViewById(R.id.equipment_name_cancel_booking);
            cancelButton = itemView.findViewById(R.id.cancel_button);
            date = itemView.findViewById(R.id.equipment_date_cancel_equipment);
            time = itemView.findViewById(R.id.equipment_time_cancel_equipment);
        }
    }
}
