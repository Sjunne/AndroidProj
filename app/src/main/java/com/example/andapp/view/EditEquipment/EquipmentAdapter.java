package com.example.andapp.view.EditEquipment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.andapp.CallBacks.CallBackForEditing;
import com.example.andapp.R;
import com.example.andapp.model.Equipment;

import java.util.ArrayList;
import java.util.List;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {
    private List<Equipment> equipmentList = new ArrayList<>();
    //bruger parent fragment til at kalde metode efter min OnClick.
    //Det er for at slippe for at have en ViewModel herinde også
    private EditEquipmentFragment fragment;

    public EquipmentAdapter(EditEquipmentFragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_item, parent, false);
        //ViewModel initializing
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentAdapter.ViewHolder holder, int position) {
        holder.name.setText(equipmentList.get(position).getCategory());
        holder.socialDistanceCheckBox.setChecked(equipmentList.get(position).isSocialDistancingBool());
        holder.numberOfEquipment.setText(Integer.toString(equipmentList.get(position).getNumberOfEquipment()));
        Glide.with(holder.itemView).load(equipmentList.get(position).getImageURL()).into(holder.image);

        //OnClick i mit fragment Item, ændre på det specifikke child element i equipment.
        //Behøver ikke fejlhåndtering, da hvis man ikke skrive noget, ændre den ikke noget
        holder.button.setOnClickListener(v -> {
            String equipmentName = holder.name.getText().toString();
            boolean socialDistance = holder.socialDistanceCheckBox.isChecked();
            int amount = Integer.parseInt(holder.numberOfEquipment.getText().toString());
            ((EditEquipmentFragment) fragment).editEquipment(equipmentName, socialDistance, amount);
            Toast.makeText(fragment.getContext(), "Success", Toast.LENGTH_LONG).show();
        });

    }

    public void updateList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return equipmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView image;
        private final Button button;
        private final CheckBox socialDistanceCheckBox;
        private final EditText numberOfEquipment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.equipment_name_equipmentItem);
            image = itemView.findViewById(R.id.equipment_image_equipmentItem);
            button = itemView.findViewById(R.id.equipment_button_equipmentItem);
            socialDistanceCheckBox = itemView.findViewById(R.id.equipment_checkbox_equipmentItem);
            numberOfEquipment = itemView.findViewById(R.id.equipment_numberOfEquipment_equipmentItem);
        }
    }
}
