package com.example.andapp.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.andapp.CallBacks.CallBackForEditing;
import com.example.andapp.CallBacks.CallBackForRegisterEquipment;
import com.example.andapp.model.Equipment;
import com.example.andapp.repository.EquipmentRepository;
import com.example.andapp.repository.UserRepository;

import java.util.List;

public class EquipmentViewModel extends AndroidViewModel {
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    public EquipmentViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
        equipmentRepository = EquipmentRepository.getInstance();
    }

    public void init() {
        equipmentRepository.init();
    }

    public void registerEquipment(String category, byte[] imageBytes, boolean socialDistancing, int numberOfEquipment, CallBackForRegisterEquipment callBackForRegisterEquipment) {
        equipmentRepository.registerEquipment(category, imageBytes, socialDistancing, numberOfEquipment, callBackForRegisterEquipment);
    }

    public LiveData<List<Equipment>> getSearchedEquipment() {
        return equipmentRepository.getSearchedEquipment();
    }

    public void editEquipment(String equipmentName, boolean socialDistance, int amount) {
        equipmentRepository.editEquipment(equipmentName, socialDistance, amount);
    }
}
