package com.example.the_companion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Chart extends AppCompatActivity {
    private PieChart pieChart;
    FirebaseFirestore db;
    Integer compulsionCount;
    int compulsion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chart);
        db = FirebaseFirestore.getInstance();

        final CollectionReference itemCollectionReference = db.collection("Tasks");
        pieChart = findViewById(R.id.activity_main_piechart);
        setupPieChart();
        loadPieChartData(itemCollectionReference);


    }
    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Weekly Compulsion Check");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }
    private void loadPieChartData(CollectionReference itemCollectionReference) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Task> tasksList = new ArrayList<>();
        entries.add(new PieEntry(0.2f, "Lock the door"));
        entries.add(new PieEntry(0.15f, "car door"));
        entries.add(new PieEntry(0.10f, "turn off faucet"));
        entries.add(new PieEntry(0.25f, "turn off the oven"));
        entries.add(new PieEntry(0.3f, "Beating Farshed"));

        itemCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                entries.clear();
                if(error != null){
                    Log.d("Sample","Error"+error.getMessage());
                }
                else{
                    for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                        Log.d("Sample",String.valueOf(doc.getData().get("task_id")));
                        String taskCompulsion = (String) doc.getData().get("task_compulsion");
                        String description = (String) doc.getData().get("task_description");
                        if (taskCompulsion != null){
                            compulsion += Integer.parseInt(taskCompulsion);
                        }
                        Task task = new Task();
                        task.setTaskDescription(description);
                        task.setCompulsioncheck(taskCompulsion);
                        tasksList.add(task);
                    }
                }
            }
        });


//        for (int i = 0; i < tasksList.size(); i++) {
//            entries.add(new PieEntry((float) Integer.valueOf(tasksList.get(i).getCompulsioncheck())
//                    / this.compulsionCount, tasksList.get(i).getTaskDescription()));
//        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

}