package com.example.andapp.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class EquipmentLiveData extends LiveData<Equipment> {
    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Equipment equipment = snapshot.getValue(Equipment.class);
            setValue(equipment);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
    DatabaseReference databaseReference;

    public EquipmentLiveData(DatabaseReference ref) {
        databaseReference = ref;
    }

    @Override
    protected void onActive() {
        super.onActive();
        databaseReference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        databaseReference.removeEventListener(listener);
    }
}
