package com.example.andapp.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.andapp.CallBacks.CallBackForEditing;
import com.example.andapp.CallBacks.CallBackForRegisterEquipment;
import com.example.andapp.model.Equipment;
import com.example.andapp.model.EquipmentLiveData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EquipmentRepository {
    private static EquipmentRepository instance;
    private DatabaseReference myRef;
    private final MutableLiveData<List<Equipment>> searchedEquipment = new MutableLiveData<>();
    private final List<Equipment> equipmentList = new ArrayList<>();

    private EquipmentRepository() {
    }

    public static synchronized EquipmentRepository getInstance() {
        if(instance == null)
            instance = new EquipmentRepository();
        return instance;
    }

    public void init() {
        myRef = FirebaseDatabase.getInstance().getReference().child("equipment");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                equipmentList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Equipment equipment = postSnapshot.getValue(Equipment.class);
                    StorageReference referenceForImages = createReferenceForImages(equipment.getCategory());
                    referenceForImages.getDownloadUrl().addOnSuccessListener(uri -> equipment.setImageUrlLol(uri));
                    equipmentList.add(equipment);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        searchedEquipment.setValue(equipmentList);
    }


    private StorageReference createReferenceForImages(String category) {
        // Create a storage reference from our app
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference();


        // Create a reference to 'images/equipments.jpg'
        return reference.child("/category/" + category + ".jpg");
    }

    private void uploadImageToEquipmentsReference(byte[] imageData, String category, CallBackForRegisterEquipment callBackForRegisterEquipment)
    {
        StorageReference storageReferenceForImages = createReferenceForImages(category);
        UploadTask uploadTask = storageReferenceForImages.putBytes(imageData);
        uploadTask.addOnFailureListener(exception -> {
            callBackForRegisterEquipment.RegisterEquipment(false);
        }).addOnSuccessListener(taskSnapshot -> {
            callBackForRegisterEquipment.RegisterEquipment(true);
        });
    }


    public void registerEquipment(String category, byte[] imageBytes, boolean socialDistancing, int numberOfEquipment, CallBackForRegisterEquipment callBackForRegisterEquipment) {
        Equipment newEquipment = new Equipment();
        newEquipment.setCategory(category);
        newEquipment.setNumberOfEquipment(numberOfEquipment);
        newEquipment.setSocialDistancingBool(socialDistancing);
        myRef.child(category).setValue(newEquipment);
        uploadImageToEquipmentsReference(imageBytes, category, callBackForRegisterEquipment);
    }

    public LiveData<List<Equipment>> getSearchedEquipment() {
        return searchedEquipment;
    }

    public void editEquipment(String equipmentName, boolean socialDistance, int amount) {
        Equipment equipment = new Equipment();
        equipment.setCategory(equipmentName);
        equipment.setSocialDistancingBool(socialDistance);
        equipment.setNumberOfEquipment(amount);
        FirebaseDatabase.getInstance().getReference().child("equipment").child(equipmentName).setValue(equipment);
    }
}
