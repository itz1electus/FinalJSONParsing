package com.mufasa.jsonparsing;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Response {

	@SerializedName("assessmentOptionsList")
	@Expose
	private List<AssessmentOptions> assessmentOptionsList;
	@SerializedName("code")
	@Expose
	private int code;
	@SerializedName("success")
	@Expose
	private boolean success;

	public List<AssessmentOptions> getAssessmentOptionsList() {
		return assessmentOptionsList;
	}

	public void setAssessmentOptionsList(List<AssessmentOptions> assessmentOptionsList) {
		this.assessmentOptionsList = assessmentOptionsList;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
