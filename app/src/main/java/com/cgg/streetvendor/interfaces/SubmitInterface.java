package com.cgg.streetvendor.interfaces;

import com.cgg.streetvendor.source.reposnse.submit.SubmitResponse;
import com.cgg.streetvendor.source.reposnse.submit.ValidateAadharResponse;

public interface SubmitInterface {
    void getData(SubmitResponse submitResponse);
    void getAadharData(ValidateAadharResponse validateAadharResponse);
}
