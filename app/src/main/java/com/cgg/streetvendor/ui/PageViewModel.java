package com.cgg.streetvendor.ui;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            String urlvalue = null;
            if(input == 1){
                urlvalue = "https://www.cgg.gov.in/mgov-privacy-policy/?depot_name=Mission for Elimination of Poverty in Municipal Areas (MEPMA), Govt. of Telangana"; //Privacy Policy
            }else if(input == 2){
                urlvalue = "https://www.cgg.gov.in/mgov-terms-conditions/?depot_name=Mission for Elimination of Poverty in Municipal Areas (MEPMA), Govt. of Telangana&capital=Hyderabad, Telangana";//Terms & Conditions
            }else if(input == 3){
                urlvalue = "https://www.cgg.gov.in/mgov-copyright-policy/?depot_name=Mission for Elimination of Poverty in Municipal Areas (MEPMA), Govt. of Telangana&depot_email=smc_susv_mepma@telangana.gov.in";//Copyright Policy
            }else{
                urlvalue = "https://www.cgg.gov.in/mgov-privacy-policy/?depot_name=Mission for Elimination of Poverty in Municipal Areas (MEPMA), Govt. of Telangana"; //Privacy Policy
            }
            return urlvalue;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}