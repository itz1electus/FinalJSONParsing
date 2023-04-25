package com.mufasa.jsonparsing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<AssessmentOptions> {

    LayoutInflater layoutInflater;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<AssessmentOptions> assessmentOptions) {
        super(context, resource, assessmentOptions);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        @SuppressLint("ViewHolder") View rowView = layoutInflater.inflate(R.layout.custom_spinner_adapter, null, true);
        AssessmentOptions rowItem = getItem(position);
        TextView textView = (TextView)rowView.findViewById(R.id.nameTextView);
        textView.setText((CharSequence) rowItem.getInterpretations());
        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.custom_spinner_adapter, parent, false);

        AssessmentOptions rowItem = getItem(position);
        TextView textView = (TextView)convertView.findViewById(R.id.nameTextView);
        textView.setText((CharSequence) rowItem.getInterpretations());
        return convertView;
    }
}

