package com.example.andapp.view.CheckStatistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.andapp.R;
import com.example.andapp.model.Booking;
import com.example.andapp.viewmodel.BookingViewModel;


import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
    private BookingViewModel bookingViewModel;
    private List<Booking> bookings = new ArrayList<>();
    Pie pie;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.statistics_fragment, container, false);

        //ViewModel initializing
        bookingViewModel = new ViewModelProvider(this).get(BookingViewModel.class);
        bookingViewModel.getBookings().observe(getViewLifecycleOwner(), this::updateList);

        pie = AnyChart.pie();

        AnyChartView anyChartView = root.findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);

        return root;
    }

    private void updateList(List<Booking> bookings) {
        this.bookings = bookings;
        fillUpPieChart();
    }

    private void fillUpPieChart() {
        List<DataEntry> data = new ArrayList<>();
        StringBuilder categoriesUsed = new StringBuilder();
        for (int i = 0; i < bookings.size(); i++) {
            if (!categoriesUsed.toString().contains(bookings.get(i).getCategory())) {
                int numberOfCategory = countNumberOfCategory(bookings.get(i).getCategory());
                data.add(new ValueDataEntry(bookings.get(i).getCategory(), numberOfCategory));
                categoriesUsed.append(bookings.get(i).getCategory());
            }
        }
        pie.data(data);
    }

    private int countNumberOfCategory(String category) {
        int number = 0;
        for (int i = 0; i < bookings.size(); i++) {
            if(bookings.get(i).getCategory().equals(category))
                number++;
        }
        return number;
    }

}




