package com.example.andapp.view.EditEquipment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andapp.CallBacks.CallBackForEditing;
import com.example.andapp.R;
import com.example.andapp.viewmodel.EquipmentViewModel;

public class EditEquipmentFragment extends Fragment {
    private EquipmentViewModel equipmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.edit_equipment_fragment, container, false);

        //initializing my recycler view
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_edit_equipment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        //initializing my adapter to fill up my recycler view
        EquipmentAdapter adapter = new EquipmentAdapter(this);

        //binding
        recyclerView.setAdapter(adapter);

        //ViewModel initializing
        equipmentViewModel = new ViewModelProvider(this).get(EquipmentViewModel.class);
        equipmentViewModel.getSearchedEquipment().observe(getViewLifecycleOwner(), adapter::updateList);
        adapter.notifyDataSetChanged();


        return root;
    }

    public void editEquipment(String equipmentName, boolean socialDistance, int amount) {
        equipmentViewModel.editEquipment(equipmentName, socialDistance, amount);
    }
}
