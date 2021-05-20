package com.example.andapp.view.RegisterEquipment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.andapp.CallBacks.CallBackForRegisterEquipment;
import com.example.andapp.R;
import com.example.andapp.viewmodel.EquipmentViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.app.Activity.RESULT_OK;

public class RegisterEquipmentFragment extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 123;

    private ImageView imageViewEquipmentPhoto;
    private EquipmentViewModel equipmentViewModel;
    private EditText editTextCategory;
    private EditText editTextEquipmentNumber;
    private CheckBox checkBoxSocialDistancing;
    byte[] imageBytes;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.register_equipment, container, false);

        Activity activity = getActivity();

        //Initialize ViewModel
        equipmentViewModel = new ViewModelProvider(this).get(EquipmentViewModel.class);


        //Initializing Views with private variables
        imageViewEquipmentPhoto = root.findViewById(R.id.equipmentPhoto);
        editTextCategory = root.findViewById(R.id.categoryRegisterEquipment);
        editTextEquipmentNumber = root.findViewById(R.id.equipmentNumber_Register_Equipement);
        checkBoxSocialDistancing = root.findViewById(R.id.social_distance_Check_Box);

        //initializing Buttons for local use
        Button buttonImageChoose = root.findViewById(R.id.onChooseImage);
        Button buttonSubmit = root.findViewById(R.id.onSubmit);

        //set OnClicks for Buttons
        buttonImageChoose.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "pick an image"), GALLERY_REQUEST_CODE);
        });


        /**
         * Check om felter er tomme.
         * Lav imageView til byte[] for "uploadBytes()" til Firebase.
         * Evt. ville være uploadFile() være bedre med URL.
         * Register equipment med Callback for Toast og "page clear"
         */
        buttonSubmit.setOnClickListener(v -> {
            if(editTextCategory.getText().toString().equals("") || editTextEquipmentNumber.getText().toString().equals("") || imageViewEquipmentPhoto.getDrawable() == null)
            {
                Toast.makeText(getContext(), "Please fill out all available fields", Toast.LENGTH_LONG).show();
            }
            else
            {
                imageViewEquipmentPhoto.setDrawingCacheEnabled(true);
                imageViewEquipmentPhoto.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imageViewEquipmentPhoto.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                imageBytes = baos.toByteArray();

                String category = this.editTextCategory.getText().toString();
                boolean socialDistancing = checkBoxSocialDistancing.isChecked();
                int numberOfEquipment = Integer.parseInt(editTextEquipmentNumber.getText().toString());

                equipmentViewModel.registerEquipment(category, imageBytes, socialDistancing, numberOfEquipment, new CallBackForRegisterEquipment() {
                    @Override
                    public void RegisterEquipment(boolean callback) {
                        if(callback)
                        {
                            System.out.println("VI KOM HERIND");
                            Toast.makeText(getActivity(), "Successfully uploaded equipment", Toast.LENGTH_LONG).show();
                            clearFields();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Something went wrong, try again", Toast.LENGTH_LONG).show();
                        }
                    }

                    private void clearFields() {
                        imageViewEquipmentPhoto.setImageDrawable(null);
                        editTextCategory.setText("");
                        editTextEquipmentNumber.setText("");
                        checkBoxSocialDistancing.setChecked(false);
                    }
                });
            }
        });

        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            imageViewEquipmentPhoto.setImageURI(data.getData());
        }
    }


}
