package com.example.andapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.andapp.model.Booking;
import com.example.andapp.CallBacks.CallBackForBookings;
import com.example.andapp.repository.BookingRepository;
import com.example.andapp.repository.UserRepository;

import java.util.List;

public class BookingViewModel extends AndroidViewModel {
    private BookingRepository bookingRepository;
    private UserRepository userRepository;

    public BookingViewModel(@NonNull Application application) {
        super(application);
        bookingRepository = BookingRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
    }

    public void init() {
        bookingRepository.init();
    }

    public void bookEquipment(String category, String time, String date) {



        bookingRepository.bookEquipment(category, time, date);
    }


    public void checkIfBookingAvailable(String category, String time, String date, CallBackForBookings callBackForBookings) {
        bookingRepository.checkIfBookingIsAvailble(category, time, date, callBackForBookings);
    }

    public LiveData<List<Booking>> getBookings() {
       return bookingRepository.getBookings();
    }

    public void cancelBooking(String category, String time, String date) {
        bookingRepository.cancelBooking(category, time, date);
    }
}
