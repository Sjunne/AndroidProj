package com.example.andapp.view.BookEquipment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andapp.R;
import com.example.andapp.viewmodel.BookingViewModel;
import com.example.andapp.viewmodel.EquipmentViewModel;

import java.util.Calendar;

public class BookEquipmentFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private EquipmentViewModel equipmentViewModel;
    private BookingViewModel bookingViewModel;
    private Spinner spinner;
    private EditText date;
    private DatePickerDialog datePickerDialog;
    private String time;




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.equipment_booking_fragment, container, false);


        // initiate my views
        date = root.findViewById(R.id.date);


        // perform click event on edit text
        date.setOnClickListener(v -> {
            // calender class's instance and get current date , month and year from calender
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            datePickerDialog = new DatePickerDialog(getContext(),
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // set day of month , month and year value in the edit text
                        date.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        //initializing my recycler view
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_book_equipment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //initializing my adapter to fill up my recycler view
        BookEquipmentAdapter adapter = new BookEquipmentAdapter(this);

        //binding
        recyclerView.setAdapter(adapter);

        //ViewModel initializing
        equipmentViewModel = new ViewModelProvider(this).get(EquipmentViewModel.class);
        equipmentViewModel.getSearchedEquipment().observe(getViewLifecycleOwner(), adapter::updateList);
        bookingViewModel = new ViewModelProvider(this).get(BookingViewModel.class);
        adapter.notifyDataSetChanged();

        //setup min Spinner -> er sat til pre-determined tidsspunker alt efter åbningstider i mit styrkecenter
        spinner = root.findViewById(R.id.book_equipment_timeSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);

        spinner.setOnItemSelectedListener(this);

        return root;
    }

    public void BookEquipment(String category)
    {
        int year = datePickerDialog.getDatePicker().getYear();
        int month = datePickerDialog.getDatePicker().getMonth();
        int dayOfMonth = datePickerDialog.getDatePicker().getDayOfMonth();
        String date = year + ":" +  month + ":" + dayOfMonth;

        bookingViewModel.checkIfBookingAvailable(category, time, date, bookingAvailable -> {
            if(bookingAvailable)
            {
                bookingViewModel.bookEquipment(category, time, date);
                Toast.makeText(getContext(), "Boooking Success", Toast.LENGTH_LONG).show();
                clearFields();
            }
            else
                Toast.makeText(getContext(), "Please try again", Toast.LENGTH_LONG).show();

        });
    }

    private void clearFields() {
        date.setText("");
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        time = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
