package com.mufasa.jsonparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final Gson gson = new Gson();
    private String json = null;
    private final List<String> roles = new ArrayList<>();
    private final List<Integer> positions = new ArrayList<>();
    private List<AssessmentOptions> interpretations = new ArrayList<>();
    private final List<String> interpretationsSeparated = new ArrayList<>();
    private final ArrayList<List<String>> suggestedTreatments = new ArrayList<>();
    private final List<String> suggestedTreatmentsSeparated = new ArrayList<>();
    private SpinnerAdapter adapter;
    private SpinAdapter adapter2;
    int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openDialogBoxBtn = (Button) findViewById(R.id.openDialogBox);

        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Log.e("TAG", "error: " + ex);
        }
        Response response = gson.fromJson(json, Response.class);
        interpretations = response.getAssessmentOptionsList();
        for (int i = 0; i < interpretations.size(); i++) {
            positions.add(interpretations.get(i).getPosition());
            roles.add(interpretations.get(i).getRole());
            suggestedTreatments.add(interpretations.get(i).getSuggestedTreatments());
        }

        openDialogBoxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
    }

    public void showCustomDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog_box);

        // Spinner for positions
        Spinner spinnerPositions = dialog.findViewById(R.id.spPositions);
        TextView roleView = dialog.findViewById(R.id.tvPositions);
        if (spinnerPositions != null && positions != null) {
            spinnerPositions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent1, View view, int positionRole, long id) {
                    roleView.setText(roles.get(positionRole));
                    role = positionRole;
                    // Spinner for interpretations
                    Spinner spinnerInterpretations = dialog.findViewById(R.id.spInterpretations);
                    TextView interpretationsView = dialog.findViewById(R.id.tvInterpretations);
                    for (int i = 0; i < interpretations.get((int) parent1.getSelectedItemId()).getInterpretations().size(); i++) {
                        interpretationsSeparated.add(interpretations.get((int) parent1.getSelectedItemId()).getInterpretations().get(i));
                    }
                    ArrayAdapter<List<String>> adapterInterpretations = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, interpretationsSeparated);
                    spinnerInterpretations.setAdapter(adapterInterpretations);
                    spinnerInterpretations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent2, View view, int position2, long id) {
                            interpretationsView.setText(parent2.getItemAtPosition(position2).toString());

                            if (!interpretations.get((int) parent1.getSelectedItemId()).getSuggestedTreatments().toString().equals("[]")) {
                                // Spinner for Suggested Treatments
                                Spinner spinnerSuggestedTreatments = dialog.findViewById(R.id.spSuggestedTreatments);
                                TextView suggestedTreatmentsView = dialog.findViewById(R.id.tvSuggestedTreatments);
                                spinnerSuggestedTreatments.setVisibility(View.VISIBLE);
                                for (int i = 0; i < interpretations.get((int) parent1.getSelectedItemId()).getSuggestedTreatments().size(); i++) {
                                    suggestedTreatmentsSeparated.add(interpretations.get((int) parent1.getSelectedItemId()).getSuggestedTreatments().get(i));
                                }
                                ArrayAdapter<List<String>> adapterSuggestedTreatments = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, suggestedTreatmentsSeparated);
                                spinnerSuggestedTreatments.setAdapter(adapterSuggestedTreatments);
                                spinnerSuggestedTreatments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        suggestedTreatmentsView.setText(parent.getItemAtPosition(position2).toString());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(MainActivity.this, "You have not selected anything", Toast.LENGTH_LONG).show();
                }
            });

            ArrayAdapter<String> adapterPositions = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
            adapterPositions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPositions.setAdapter(adapterPositions);
        }

        // Show the dialog box
        dialog.show();
    }
}
