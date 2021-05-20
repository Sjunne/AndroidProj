package com.example.andapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.andapp.model.Booking;
import com.example.andapp.CallBacks.CallBackForBookings;
import com.example.andapp.model.Equipment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookingRepository {
    private DatabaseReference myRef;
    private static BookingRepository instance;
    private final MutableLiveData<List<Booking>> bookings = new MutableLiveData<>();
    private final List<Booking> bookingsList = new ArrayList<>();

    private BookingRepository() {
    }

    public static synchronized BookingRepository getInstance() {
        if (instance == null)
            instance = new BookingRepository();
        return instance;
    }

    public void init() {
        myRef = FirebaseDatabase.getInstance().getReference().child("bookings");
        updateListListener();
    }


    private void updateListListener()
    {
        myRef.orderByChild("userId").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                bookingsList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Booking booking = postSnapshot.getValue(Booking.class);


                    bookingsList.add(booking);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        bookings.setValue(bookingsList);
    }


    public void bookEquipment(String category, String time, String date) {
        String s = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        System.out.println(s);
        Booking booking = new Booking(s, time, date, category);
        booking.setStringForFirebaseQuery(category + time + date);
        //myRef.child(category + " " + time + " " + date).push().setValue(booking);
        myRef.push().setValue(booking);
    }

    public void checkIfBookingIsAvailble(String category, String time, String date, CallBackForBookings callBackForBookings) {

        FirebaseDatabase.getInstance().getReference().child("equipment").child(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Equipment value = snapshot.getValue(Equipment.class);
                int numberOfEquipment = value.getNumberOfEquipment();
                boolean socialDistancingBool = value.isSocialDistancingBool();
                myRef.orderByChild("stringForFirebaseQuery").equalTo(category + time + date).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!socialDistancingBool && numberOfEquipment > snapshot.getChildrenCount())
                        {
                            callBackForBookings.BookEquipment(true);
                        }
                        else if (socialDistancingBool && ((numberOfEquipment / 2) >= snapshot.getChildrenCount()))
                        {
                            callBackForBookings.BookEquipment(true);
                        }
                        else
                        {
                            callBackForBookings.BookEquipment(false);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
            }

    public LiveData<List<Booking>> getBookings() {
        return bookings;
    }

    public void cancelBooking(String category, String time, String date) {
        System.out.println(category+date+time);

        myRef.orderByChild("stringForFirebaseQuery").equalTo(category+date+time).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    updateListListener();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
