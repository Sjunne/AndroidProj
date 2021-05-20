package com.example.andapp.view.BookEquipment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.andapp.R;
import com.example.andapp.model.Equipment;

import java.util.ArrayList;
import java.util.List;

public class BookEquipmentAdapter extends RecyclerView.Adapter<BookEquipmentAdapter.ViewHolder> {
    private List<Equipment> equipmentList = new ArrayList<>();
    private BookEquipmentFragment bookEquipmentFragment;

    public BookEquipmentAdapter(Fragment fragment)
    {
            bookEquipmentFragment = (BookEquipmentFragment) fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false);
        //ViewModel initializing
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView).load(equipmentList.get(position).getImageURL()).into(holder.equipmentPhoto);
        holder.textView.setText(equipmentList.get(position).getCategory());
        holder.bookingButton.setOnClickListener(v -> {
            bookEquipmentFragment.BookEquipment(holder.textView.getText().toString());

        });
    }

    @Override
    public int getItemCount() {
        return equipmentList.size();
    }

    public void updateList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView equipmentPhoto;
        TextView textView;
        Button bookingButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            equipmentPhoto = itemView.findViewById(R.id.equipmentPhoto_book_equipment);
            textView = itemView.findViewById(R.id.equipment_name_book_equipment);
            bookingButton = itemView.findViewById(R.id.booking_button_book_equipment);
        }
    }
}

