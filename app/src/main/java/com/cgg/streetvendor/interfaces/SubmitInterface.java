package com.cgg.streetvendor.interfaces;

import android.content.Context;

import com.cgg.streetvendor.source.reposnse.SubmitResponse;
import com.cgg.streetvendor.source.reposnse.ValidateAadharResponse;

public interface SubmitInterface {
    void getData(SubmitResponse submitResponse);
    void getAadharData(ValidateAadharResponse validateAadharResponse);
}
