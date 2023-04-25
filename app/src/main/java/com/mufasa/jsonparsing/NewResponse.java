package com.mufasa.jsonparsing;

import java.util.List;

public class NewResponse{

    private List<String> assessmentOptions;
    private List<String> suggestedTreatments;

    public void setAssessmentOptions(List<String> assessmentOptions1){
        this.assessmentOptions = assessmentOptions1;
    }

    public List<String> getAssessmentOptions(){
        return this.assessmentOptions;
    }

    public void setSuggestedTreatments(List<String> suggestedTreatments1){
        this.suggestedTreatments = suggestedTreatments1;
    }

    public List<String> getSuggestedTreatments(){
        return this.suggestedTreatments;
    }
}
