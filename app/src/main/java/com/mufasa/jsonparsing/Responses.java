package com.mufasa.jsonparsing;

import java.util.ArrayList;

class Responses {

    private String interpretation;
    private final ArrayList<Response> allInterpretations = new ArrayList<Response>();

    public void Interpretation(Response interpretation){
        this.interpretation = String.valueOf(interpretation);
        allInterpretations.add(interpretation);
    }

    public String getInterpretation() {
        return interpretation;
    }

    public ArrayList<Response> getAllInterpretations() {
        return allInterpretations;
    }

}
