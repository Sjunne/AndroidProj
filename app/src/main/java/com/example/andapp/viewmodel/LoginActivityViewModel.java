package com.example.andapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.andapp.CallBacks.CallBackInterfaceForLogout;
import com.example.andapp.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivityViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public LoginActivityViewModel(Application app){
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public void logout(CallBackInterfaceForLogout callBackInterfaceForLogout) {
       userRepository.logout(callBackInterfaceForLogout);
    }
}
