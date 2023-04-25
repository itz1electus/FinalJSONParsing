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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final Gson gson = new Gson();
    private String json = null;
    private final List<String> roles = new ArrayList<>();
    private final List<Integer> positions = new ArrayList<>();
    private final ArrayList<List<String>> interpretationsNew = new ArrayList<>();
    private final List<String> interpretationsSeparated = new ArrayList<>();
    private final ArrayList<List<String>> suggestedTreatments = new ArrayList<>();
    private final List<String> suggestedTreatmentsSeparated = new ArrayList<>();
    private SpinAdapter adapter;
    static NewResponse[] newResponses;

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
        List<AssessmentOptions> interpretations = response.getAssessmentOptionsList();
        for (int i = 0; i < interpretations.size(); i++) {
            positions.add(interpretations.get(i).getPosition());
            roles.add(interpretations.get(i).getRole());
            interpretationsNew.add(interpretations.get(i).getInterpretations());
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
                public void onItemSelected(AdapterView<?> parent, View view, int positionRole, long id) {
                    roleView.setText(roles.get(positionRole));
                    interpretationsSeparated.addAll(interpretationsNew.get(positionRole));

                    newResponses = new NewResponse[interpretationsSeparated.size()];

                    newResponses[positionRole] = new NewResponse();
                    newResponses[positionRole].setAssessmentOptions(interpretationsNew.get(positionRole));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(MainActivity.this, "You have not selected anything", Toast.LENGTH_LONG).show();
                }
            });

            ArrayAdapter<Integer> adapterPositions = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, positions);
            adapterPositions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPositions.setAdapter(adapterPositions);
        }

        // Spinner for interpretations
        Spinner spinnerInterpretations = dialog.findViewById(R.id.spInterpretations);
        TextView interpretationsView = dialog.findViewById(R.id.tvInterpretations);
        if (spinnerInterpretations != null) {
            spinnerInterpretations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    interpretationsView.setText(interpretationsSeparated.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            adapter = new SpinAdapter(this, android.R.layout.simple_spinner_item, newResponses);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerInterpretations.setAdapter(adapter);
        }

        // Spinner for Suggested Treatments
        Spinner spinnerSuggestedTreatments = dialog.findViewById(R.id.spSuggestedTreatments);
        if (spinnerSuggestedTreatments != null && suggestedTreatments != null) {
            spinnerSuggestedTreatments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this, "You have selected the following: " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ArrayAdapter<List<String>> adapterSuggestedTreatments = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, suggestedTreatments);
            adapterSuggestedTreatments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSuggestedTreatments.setAdapter(adapterSuggestedTreatments);
        }

        // Show the dialog box
        dialog.show();
    }
}
