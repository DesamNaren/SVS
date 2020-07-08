package com.cgg.streetvendor.ui;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.application.AppConstants;
import com.cgg.streetvendor.application.SVSApplication;
import com.cgg.streetvendor.databinding.ActivitySurveyBinding;
import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.interfaces.SubmitInterface;
import com.cgg.streetvendor.room.repository.SVSSyncPlacesRepository;
import com.cgg.streetvendor.source.reposnse.SubmitResponse;
import com.cgg.streetvendor.source.reposnse.ValidateAadharResponse;
import com.cgg.streetvendor.source.reposnse.bankbranch.BankEntity;
import com.cgg.streetvendor.source.reposnse.bankbranch.BranchEntity;
import com.cgg.streetvendor.source.reposnse.kyc.BusinessEntity;
import com.cgg.streetvendor.source.reposnse.kyc.CasteEntity;
import com.cgg.streetvendor.source.reposnse.kyc.GenderEntity;
import com.cgg.streetvendor.source.reposnse.kyc.PWDEntity;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.ReligionEntity;
import com.cgg.streetvendor.source.reposnse.kyc.VendingAddressEntity;
import com.cgg.streetvendor.source.reposnse.kyc.VendingTypeEntity;
import com.cgg.streetvendor.source.reposnse.places.DistrictEntity;
import com.cgg.streetvendor.source.reposnse.places.MandalEntity;
import com.cgg.streetvendor.source.reposnse.places.StateEntity;
import com.cgg.streetvendor.source.reposnse.places.VillageEntity;
import com.cgg.streetvendor.source.reposnse.ulb.UlbEntity;
import com.cgg.streetvendor.source.reposnse.ulb.WardEntity;
import com.cgg.streetvendor.source.request.SubmitRequest;
import com.cgg.streetvendor.util.CustomProgressDialog;
import com.cgg.streetvendor.util.ErrorHandler;
import com.cgg.streetvendor.util.LocaleHelper;
import com.cgg.streetvendor.util.Utils;
import com.cgg.streetvendor.viewmodel.SVSSyncBBViewModel;
import com.cgg.streetvendor.viewmodel.SVSSyncKYCViewModel;
import com.cgg.streetvendor.viewmodel.SVSSyncPlaceViewModel;
import com.cgg.streetvendor.viewmodel.SVSSyncULBViewModel;
import com.cgg.streetvendor.viewmodel.SVSSyncVendingViewModel;
import com.cgg.streetvendor.viewmodel.SubmitViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ErrorHandlerInterface, SubmitInterface,
        AdapterView.OnItemSelectedListener {

    private static final int PERMISSION_REQUEST_CODE = 1001;
    private List<FamilyInfo> familyInfoArrayList;
    private Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private ActivitySurveyBinding binding;
    private SubmitViewModel submitViewModel;
    private SVSSyncPlacesRepository svsSyncPlacesRepository;
    SVSSyncPlaceViewModel svsSyncPlaceViewModel;
    SVSSyncBBViewModel svsSyncBBViewModel;
    SVSSyncKYCViewModel svsSyncKYCViewModel;
    SVSSyncVendingViewModel svsSyncVendingViewModel;
    SVSSyncULBViewModel svsSyncULBViewModel;
    private ArrayList<String> states, districts, mandals, villages;
    private ArrayList<String> banks, branches;
    private ArrayList<String> ULBs, wards;
    private ArrayList<String> genders, castes, PWDs, qualifications, religions;
    private ArrayList<String> businesses, venTypes, venAddresses;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String PICTYPE;
    private String ulb_str, ward_str, aadharNo, nameOfVendor, fatherHusbName, dob, gender_str, age_str,
            religion_str, caste_str, physicalVal, physChall, highQual, bank_str, branch_str, ifscCode, accntNo, confAccntNo, mobNo, ICENo,
            businessType, vendingType, vendingArea, resAddress, state, district, mandal, village, HNo, street, policeStation, vendingDOB;
    private int flag_applicant = 0, flag_vendorAct = 0;
    private String other_caste, other_business, other_religion;
    private String casteId, pwdId, quaId, religionId, genCode;
    private String vAddressId, vTypeId, businessId;
    private CustomProgressDialog customProgressDialog;

    private String ipAddress, deviceId;
    private String locale;
    private String select_empty;
    private ArrayList<String> emptyList = new ArrayList<>();


    final android.os.Handler handler = new android.os.Handler();
    Runnable runnable;


    private List<BankEntity> bankEntities;
    private List<BranchEntity> branchEntities;
    private List<ReligionEntity> religionEntities;
    private List<GenderEntity> genderEntities;
    private List<CasteEntity> casteEntities;
    private List<PWDEntity> pwdEntities;
    private List<QualificationEntity> qualificationEntities;
    private List<BusinessEntity> businessEntities;
    private List<VendingTypeEntity> vendingTypeEntities;
    private List<StateEntity> stateEntities;
    private List<DistrictEntity> districtEntities;
    private List<MandalEntity> mandalEntities;
    private List<VillageEntity> villageEntities;
    private List<VendingAddressEntity> vendingAddressEntities;
    private List<WardEntity> wardEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_survey);

        select_empty = getString(R.string.select_empty);
        emptyList.add(select_empty);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int startColor = getWindow().getStatusBarColor();
            int endColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
            ObjectAnimator.ofArgb(getWindow(), "statusBarColor", startColor, endColor).start();
        }

        locale = LocaleHelper.getLanguage(MainActivity.this);
        ipAddress = Utils.getLocalIpAddress();
        deviceId = Utils.getDeviceID(MainActivity.this);


        try {
            sharedPreferences = SVSApplication.get(Objects.requireNonNull(this)).getPreferences();
            editor = sharedPreferences.edit();
            loggedULBId = sharedPreferences.getString(AppConstants.ULB_ID, "");
            loggedDistId = sharedPreferences.getString(AppConstants.DISTRICT_ID, "");
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        customProgressDialog = new CustomProgressDialog(this);
        states = new ArrayList<>();
        genders = new ArrayList<>();
        castes = new ArrayList<>();
        PWDs = new ArrayList<>();
        qualifications = new ArrayList<>();
        religions = new ArrayList<>();
        districts = new ArrayList<>();
        banks = new ArrayList<>();
        branches = new ArrayList<>();
        mandals = new ArrayList<>();
        villages = new ArrayList<>();
        ULBs = new ArrayList<>();
        wards = new ArrayList<>();
        businesses = new ArrayList<>();
        venTypes = new ArrayList<>();
        venAddresses = new ArrayList<>();


        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editor.putString(AppConstants.MEMBER_DATA, "");
                editor.commit();

                if (binding.btnVerify.getText().equals(getString(R.string.verify))) {
                    if (validateData()) {
                        if (Utils.checkInternetConnection(MainActivity.this)) {

                            aadharNo = binding.etAadhaarNum.getText().toString();
                            long aadhar = Long.valueOf(aadharNo);
                            customProgressDialog.show();
                            submitViewModel.validateAadhar(aadhar);
                        } else {
                            Utils.customErrorAlert(MainActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                        }

                        if (!(venAddresses != null && venAddresses.size() > 0)) {
                            Utils.customErrorAlert(MainActivity.this, getString(R.string.app_name),
                                    getString(R.string.no_ven_area));
                        }

                    }
                } else {
                    flag_applicant = 0;
                    flag_vendorAct = 0;
                    editor.putString(AppConstants.MEMBER_DATA, "");
                    editor.commit();
                    Intent intent = getIntent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        binding.header.headerTitle.setText(getString(R.string.vendor_registration));
        svsSyncPlaceViewModel = new SVSSyncPlaceViewModel(this, getApplication());
        svsSyncBBViewModel = new SVSSyncBBViewModel(this, getApplication());
        svsSyncKYCViewModel = new SVSSyncKYCViewModel(this, getApplication());
        svsSyncVendingViewModel = new SVSSyncVendingViewModel(this, getApplication());
        submitViewModel = new SubmitViewModel(this, getApplication());
        svsSyncULBViewModel = new SVSSyncULBViewModel(this, getApplication());


        svsSyncPlacesRepository = new SVSSyncPlacesRepository(getApplication());


        LiveData<List<StateEntity>> distListLiveData = svsSyncPlaceViewModel.getAllStates();
        distListLiveData.observe(MainActivity.this, new Observer<List<StateEntity>>() {
            @Override
            public void onChanged(List<StateEntity> stateEntities) {
                distListLiveData.removeObservers(MainActivity.this);
                MainActivity.this.stateEntities = stateEntities;
                if (stateEntities == null || stateEntities.size() <= 0) {

                    Utils.loadSpinnerData(MainActivity.this, emptyList, binding.stateSpinner);

                    Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                            getString(R.string.state_message));
                } else {
                    //load based on language type

                    if (locale.equals("te")) {
                        for (int x = 0; x < stateEntities.size(); x++) {
                            if (!TextUtils.isEmpty(stateEntities.get(x).getStateTypeTel())) {
                                states.add(stateEntities.get(x).getStateTypeTel());
                            }
                        }
                    } else {
                        for (int x = 0; x < stateEntities.size(); x++) {
                            if (!TextUtils.isEmpty(stateEntities.get(x).getStateType())) {
                                states.add(stateEntities.get(x).getStateType());
                            }
                        }
                    }
                    binding.stateSpinner.setEnabled(true);
                    Utils.loadSpinnerData(MainActivity.this, states, binding.stateSpinner);

                    LiveData<List<DistrictEntity>> distListLiveData = svsSyncPlaceViewModel.getAllDistricts();
                    distListLiveData.observe(MainActivity.this, new Observer<List<DistrictEntity>>() {
                        @Override
                        public void onChanged(List<DistrictEntity> districtEntities) {
                            distListLiveData.removeObservers(MainActivity.this);
                            MainActivity.this.districtEntities = districtEntities;
                            if (districtEntities == null || districtEntities.size() <= 0) {

                                Utils.loadSpinnerData(MainActivity.this, emptyList, binding.districtSpinner);

                                Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                                        getString(R.string.dist_message));
                            } else {

                                //load based on language type
                                if (locale.equals("te")) {
                                    for (int x = 0; x < districtEntities.size(); x++) {
                                        if (!TextUtils.isEmpty(districtEntities.get(x).getDistrictNameTel())) {
                                            districts.add(districtEntities.get(x).getDistrictNameTel());
                                        }
                                    }
                                } else {
                                    for (int x = 0; x < districtEntities.size(); x++) {
                                        if (!TextUtils.isEmpty(districtEntities.get(x).getDistrictName())) {
                                            districts.add(districtEntities.get(x).getDistrictName());
                                        }
                                    }
                                }

                                Utils.loadSpinnerData(MainActivity.this, districts, binding.districtSpinner);


                                LiveData<List<MandalEntity>> manListLiveData = svsSyncPlaceViewModel.getAllMandals();
                                manListLiveData.observe(MainActivity.this, new Observer<List<MandalEntity>>() {
                                    @Override
                                    public void onChanged(List<MandalEntity> mandalEntities) {
                                        manListLiveData.removeObservers(MainActivity.this);

                                        if (mandalEntities == null || mandalEntities.size() <= 0) {

                                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.mandalSpinner);

                                            Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                                                    getString(R.string.mandal_message));
                                        } else {

                                            //load based on language type
                                            if (locale.equals("te")) {
                                                for (int x = 0; x < mandalEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(mandalEntities.get(x).getMandalNameTel())) {
                                                        mandals.add(mandalEntities.get(x).getMandalNameTel());
                                                    }
                                                }
                                            } else {
                                                for (int x = 0; x < mandalEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(mandalEntities.get(x).getMandalName())) {
                                                        mandals.add(mandalEntities.get(x).getMandalName());
                                                    }
                                                }
                                            }

                                            Utils.loadSpinnerData(MainActivity.this, mandals, binding.mandalSpinner);


                                            LiveData<List<VillageEntity>> vilListLiveData = svsSyncPlaceViewModel.getAllVillages();
                                            vilListLiveData.observe(MainActivity.this, new Observer<List<VillageEntity>>() {
                                                @Override
                                                public void onChanged(List<VillageEntity> villageEntities) {
                                                    vilListLiveData.removeObservers(MainActivity.this);

                                                    if (villageEntities == null || villageEntities.size() <= 0) {


                                                        Utils.loadSpinnerData(MainActivity.this, emptyList, binding.villageSpinner);
                                                        Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                                                                getString(R.string.village_message));
                                                    } else {
                                                        //load based on language type
                                                        if (locale.equals("te")) {
                                                            for (int x = 0; x < villageEntities.size(); x++) {
                                                                if (!TextUtils.isEmpty(villageEntities.get(x).getVillageNameTel())) {
                                                                    villages.add(villageEntities.get(x).getVillageNameTel());
                                                                }
                                                            }
                                                        } else {
                                                            for (int x = 0; x < villageEntities.size(); x++) {
                                                                if (!TextUtils.isEmpty(villageEntities.get(x).getVillageName())) {
                                                                    villages.add(villageEntities.get(x).getVillageName());
                                                                }
                                                            }
                                                        }

                                                        Utils.loadSpinnerData(MainActivity.this, villages, binding.villageSpinner);
                                                    }

                                                }
                                            });
                                        }

                                    }
                                });
                            }

                        }
                    });
                }

            }
        });

        LiveData<List<BankEntity>> bankListLiveData = svsSyncBBViewModel.getAllBanks();
        bankListLiveData.observe(MainActivity.this, new Observer<List<BankEntity>>() {
            @Override
            public void onChanged(List<BankEntity> bankEntities) {
                bankListLiveData.removeObservers(MainActivity.this);
                MainActivity.this.bankEntities = bankEntities;

                if (bankEntities == null || bankEntities.size() <= 0) {
                    Utils.loadSpinnerData(MainActivity.this, emptyList, binding.bankSpinner);
                    Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                            getString(R.string.bank_message));
                } else {

                    //load based on language type

                    if (locale.equals("te")) {
                        for (int x = 0; x < bankEntities.size(); x++) {
                            if (!TextUtils.isEmpty(bankEntities.get(x).getBankNameTel())) {
                                banks.add(bankEntities.get(x).getBankNameTel());
                            }
                        }
                    } else {

                        for (int x = 0; x < bankEntities.size(); x++) {
                            if (!TextUtils.isEmpty(bankEntities.get(x).getBankName())) {
                                banks.add(bankEntities.get(x).getBankName());
                            }
                        }
                    }

                    Utils.loadSpinnerData(MainActivity.this, banks, binding.bankSpinner);


                    LiveData<List<BranchEntity>> branchListLiveData = svsSyncBBViewModel.getAllBranches();
                    branchListLiveData.observe(MainActivity.this, new Observer<List<BranchEntity>>() {
                        @Override
                        public void onChanged(List<BranchEntity> branchEntities) {
                            branchListLiveData.removeObservers(MainActivity.this);

                            if (branchEntities == null || branchEntities.size() <= 0) {
                                Utils.loadSpinnerData(MainActivity.this, emptyList, binding.branchSpinner);
                                Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                                        getString(R.string.branch_message));
                            } else {
                                //load based on language type

                                if (locale.equals("te")) {
                                    for (int x = 0; x < branchEntities.size(); x++) {
                                        if (!TextUtils.isEmpty(branchEntities.get(x).getBranchNameTel())) {
                                            branches.add(branchEntities.get(x).getBranchNameTel());
                                        }
                                    }
                                } else {

                                    for (int x = 0; x < branchEntities.size(); x++) {
                                        if (!TextUtils.isEmpty(branchEntities.get(x).getBranchName())) {
                                            branches.add(branchEntities.get(x).getBranchName());
                                        }
                                    }
                                }
                                Utils.loadSpinnerData(MainActivity.this, branches, binding.branchSpinner);
                            }

                        }
                    });
                }

            }
        });

        LiveData<List<UlbEntity>> ulbListLiveData = svsSyncULBViewModel.getLoggedULBs(loggedULBId);
        ulbListLiveData.observe(MainActivity.this, new Observer<List<UlbEntity>>() {
            @Override
            public void onChanged(List<UlbEntity> ulbEntities) {
                ulbListLiveData.removeObservers(MainActivity.this);

                if (ulbEntities == null || ulbEntities.size() <= 0) {
                    Utils.loadSpinnerData(MainActivity.this, emptyList, binding.ulbSpinner);
                    Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                            getString(R.string.ulb_message));
                } else {

                    //load based on language type
                    if (locale.equals("te")) {
                        for (int x = 0; x < ulbEntities.size(); x++) {
                            if (!TextUtils.isEmpty(ulbEntities.get(x).getUlbNameTel())) {
                                ULBs.add(ulbEntities.get(x).getUlbNameTel());
                            }
                        }
                    } else {
                        for (int x = 0; x < ulbEntities.size(); x++) {
                            if (!TextUtils.isEmpty(ulbEntities.get(x).getUlbName())) {
                                ULBs.add(ulbEntities.get(x).getUlbName());
                            }
                        }
                    }

                    Utils.loadSpinnerData(MainActivity.this, ULBs, binding.ulbSpinner);
                }

            }
        });

        LiveData<List<GenderEntity>> genderListLiveData = svsSyncKYCViewModel.getAllGender();
        genderListLiveData.observe(this, new Observer<List<GenderEntity>>() {
            @Override
            public void onChanged(List<GenderEntity> genderEntities) {
                genderListLiveData.removeObservers(MainActivity.this);
                MainActivity.this.genderEntities = genderEntities;

                if (genderEntities == null || genderEntities.size() <= 0) {
                    Utils.loadSpinnerData(MainActivity.this, emptyList, binding.genderSpinner);
                    Utils.loadSpinnerData(MainActivity.this, emptyList, binding.casteSpinner);
                    Utils.loadSpinnerData(MainActivity.this, emptyList, binding.pwdSpinner);
                    Utils.loadSpinnerData(MainActivity.this, emptyList, binding.qualificationSpinner);
                    Utils.loadSpinnerData(MainActivity.this, emptyList, binding.religionSpinner);

                    Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                            getString(R.string.gender_message));
                } else {

                    //load based on language type
                    if (locale.equals("te")) {
                        for (int x = 0; x < genderEntities.size(); x++) {
                            if (!TextUtils.isEmpty(genderEntities.get(x).getGenderNameTel())) {
                                genders.add(genderEntities.get(x).getGenderNameTel());
                            }
                        }
                    } else {
                        for (int x = 0; x < genderEntities.size(); x++) {
                            if (!TextUtils.isEmpty(genderEntities.get(x).getGenderName())) {
                                genders.add(genderEntities.get(x).getGenderName());
                            }
                        }
                    }

                    Utils.loadSpinnerData(MainActivity.this, genders, binding.genderSpinner);


                    LiveData<List<CasteEntity>> castListLiveData = svsSyncKYCViewModel.getAllCaste();
                    castListLiveData.observe(MainActivity.this, new Observer<List<CasteEntity>>() {
                        @Override
                        public void onChanged(List<CasteEntity> casteEntities) {
                            castListLiveData.removeObservers(MainActivity.this);

                            MainActivity.this.casteEntities = casteEntities;
                            if (casteEntities == null || casteEntities.size() <= 0) {


                                Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                                        getString(R.string.caste_message));
                            } else {

                                //load based on language type
                                if (locale.equals("te")) {
                                    for (int x = 0; x < casteEntities.size(); x++) {
                                        if (!TextUtils.isEmpty(casteEntities.get(x).getCasteTel())) {
                                            castes.add(casteEntities.get(x).getCasteTel());
                                        }
                                    }
                                } else {
                                    for (int x = 0; x < casteEntities.size(); x++) {
                                        if (!TextUtils.isEmpty(casteEntities.get(x).getCaste())) {
                                            castes.add(casteEntities.get(x).getCaste());
                                        }
                                    }
                                }

                                Utils.loadSpinnerData(MainActivity.this, castes, binding.casteSpinner);


                                LiveData<List<PWDEntity>> pwdListLiveData = svsSyncKYCViewModel.getAllPWDs();
                                pwdListLiveData.observe(MainActivity.this, new Observer<List<PWDEntity>>() {
                                    @Override
                                    public void onChanged(List<PWDEntity> pwdEntities) {
                                        pwdListLiveData.removeObservers(MainActivity.this);
                                        MainActivity.this.pwdEntities = pwdEntities;
                                        if (pwdEntities == null || pwdEntities.size() <= 0) {
                                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.pwdSpinner);

                                            Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                                                    getString(R.string.pwd_message));
                                        } else {

                                            //load based on language type
                                            if (locale.equals("te")) {
                                                for (int x = 0; x < pwdEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(pwdEntities.get(x).getPwdTypeTel())) {
                                                        PWDs.add(pwdEntities.get(x).getPwdTypeTel());
                                                    }
                                                }
                                            } else {
                                                for (int x = 0; x < pwdEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(pwdEntities.get(x).getPwdType())) {
                                                        PWDs.add(pwdEntities.get(x).getPwdType());
                                                    }
                                                }
                                            }

                                            Utils.loadSpinnerData(MainActivity.this, PWDs, binding.pwdSpinner);

                                            LiveData<List<QualificationEntity>> qListLiveData = svsSyncKYCViewModel.getAllQual();
                                            qListLiveData.observe(MainActivity.this, new Observer<List<QualificationEntity>>() {
                                                @Override
                                                public void onChanged(List<QualificationEntity> qualificationEntities) {
                                                    qListLiveData.removeObservers(MainActivity.this);
                                                    MainActivity.this.qualificationEntities = qualificationEntities;
                                                    if (qualificationEntities == null || qualificationEntities.size() <= 0) {

                                                        Utils.loadSpinnerData(MainActivity.this, emptyList, binding.qualificationSpinner);

                                                        Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                                                                getString(R.string.qual_message));
                                                    } else {

                                                        //load based on language type
                                                        if (locale.equals("te")) {
                                                            for (int x = 0; x < qualificationEntities.size(); x++) {
                                                                if (!TextUtils.isEmpty(qualificationEntities.get(x).getQualificationTypeTel())) {
                                                                    qualifications.add(qualificationEntities.get(x).getQualificationTypeTel());
                                                                }
                                                            }
                                                        } else {
                                                            for (int x = 0; x < qualificationEntities.size(); x++) {
                                                                if (!TextUtils.isEmpty(qualificationEntities.get(x).getQualificationType())) {
                                                                    qualifications.add(qualificationEntities.get(x).getQualificationType());
                                                                }
                                                            }
                                                        }

                                                        Utils.loadSpinnerData(MainActivity.this, qualifications, binding.qualificationSpinner);

                                                        LiveData<List<ReligionEntity>> rListLiveData = svsSyncKYCViewModel.getAllReligion();
                                                        rListLiveData.observe(MainActivity.this, new Observer<List<ReligionEntity>>() {
                                                            @Override
                                                            public void onChanged(List<ReligionEntity> religionEntities) {
                                                                rListLiveData.removeObservers(MainActivity.this);

                                                                MainActivity.this.religionEntities = religionEntities;

                                                                if (religionEntities == null || religionEntities.size() <= 0) {
                                                                    Utils.loadSpinnerData(MainActivity.this, emptyList, binding.religionSpinner);

                                                                    Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                                                                            getString(R.string.religion_message));
                                                                } else {
                                                                    //load based on language type
                                                                    if (locale.equals("te")) {
                                                                        for (int x = 0; x < religionEntities.size(); x++) {
                                                                            if (!TextUtils.isEmpty(religionEntities.get(x).getReligionNameTel())) {
                                                                                religions.add(religionEntities.get(x).getReligionNameTel());
                                                                            }
                                                                        }
                                                                    } else {
                                                                        for (int x = 0; x < religionEntities.size(); x++) {
                                                                            if (!TextUtils.isEmpty(religionEntities.get(x).getReligionName())) {
                                                                                religions.add(religionEntities.get(x).getReligionName());
                                                                            }
                                                                        }
                                                                    }

                                                                    Utils.loadSpinnerData(MainActivity.this, religions, binding.religionSpinner);
                                                                }
                                                            }
                                                        });
                                                    }

                                                }
                                            });
                                        }

                                    }
                                });
                            }

                        }
                    });
                }

            }
        });

        LiveData<List<BusinessEntity>> businessListLiveData = svsSyncVendingViewModel.getAllBusiness();
        businessListLiveData.observe(this, new Observer<List<BusinessEntity>>() {
            @Override
            public void onChanged(List<BusinessEntity> businessEntities) {
                businessListLiveData.removeObservers(MainActivity.this);
                MainActivity.this.businessEntities = businessEntities;
                if (businessEntities == null || businessEntities.size() <= 0) {
                    Utils.loadSpinnerData(MainActivity.this, emptyList, binding.businessSpinner);
                    Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                            getString(R.string.business_message));
                } else {
                    //load based on language type
                    if (locale.equals("te")) {
                        for (int x = 0; x < businessEntities.size(); x++) {
                            if (!TextUtils.isEmpty(businessEntities.get(x).getBusinessNameTel())) {
                                businesses.add(businessEntities.get(x).getBusinessNameTel());
                            }
                        }
                    } else {
                        for (int x = 0; x < businessEntities.size(); x++) {
                            if (!TextUtils.isEmpty(businessEntities.get(x).getBusinessName())) {
                                businesses.add(businessEntities.get(x).getBusinessName());
                            }
                        }
                    }

                    Utils.loadSpinnerData(MainActivity.this, businesses, binding.businessSpinner);

                    LiveData<List<VendingTypeEntity>> vListLiveData = svsSyncVendingViewModel.getAllVenTypes();
                    vListLiveData.observe(MainActivity.this, new Observer<List<VendingTypeEntity>>() {
                        @Override
                        public void onChanged(List<VendingTypeEntity> vendingTypeEntities) {
                            vListLiveData.removeObservers(MainActivity.this);
                            MainActivity.this.vendingTypeEntities = vendingTypeEntities;
                            if (vendingTypeEntities == null || vendingTypeEntities.size() <= 0) {

                                Utils.loadSpinnerData(MainActivity.this, emptyList, binding.vendingSpinner);

                                Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                                        getString(R.string.vending_type_message));
                            } else {

                                //load based on language type
                                if (locale.equals("te")) {
                                    for (int x = 0; x < vendingTypeEntities.size(); x++) {
                                        if (!TextUtils.isEmpty(vendingTypeEntities.get(x).getVendingTypeTel())) {
                                            venTypes.add(vendingTypeEntities.get(x).getVendingTypeTel());
                                        }
                                    }
                                } else {
                                    for (int x = 0; x < vendingTypeEntities.size(); x++) {
                                        if (!TextUtils.isEmpty(vendingTypeEntities.get(x).getVendingType())) {
                                            venTypes.add(vendingTypeEntities.get(x).getVendingType());
                                        }
                                    }
                                }

                                Utils.loadSpinnerData(MainActivity.this, venTypes, binding.vendingSpinner);

                                LiveData<List<VendingAddressEntity>> vaListLiveData = svsSyncVendingViewModel.getAllVenAddress(loggedULBId, loggedDistId);
                                vaListLiveData.observe(MainActivity.this, new Observer<List<VendingAddressEntity>>() {
                                    @Override
                                    public void onChanged(List<VendingAddressEntity> vendingAddressEntities) {
                                        vaListLiveData.removeObservers(MainActivity.this);
                                        MainActivity.this.vendingAddressEntities = vendingAddressEntities;
                                        if (vendingAddressEntities == null || vendingAddressEntities.size() <= 0) {
                                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.vendingAreaSpinner);

                                            Utils.customSyncAlertDownload(MainActivity.this, getString(R.string.app_name),
                                                    getString(R.string.vending_area_message));
                                        } else {
                                            //load based on language type
                                            venAddresses = new ArrayList<>();
                                            venAddresses.add(getString(R.string.select_vending_area));
                                            if (locale.equals("te")) {

                                                for (int x = 0; x < vendingAddressEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(vendingAddressEntities.get(x).getVendingAreaNameTel())) {
                                                        venAddresses.add(vendingAddressEntities.get(x).getVendingAreaNameTel());
                                                    }
                                                }
                                            } else {
                                                for (int x = 0; x < vendingAddressEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(vendingAddressEntities.get(x).getVendingAreaName())) {
                                                        venAddresses.add(vendingAddressEntities.get(x).getVendingAreaName());
                                                    }
                                                }
                                            }
                                            Utils.loadSpinnerData(MainActivity.this, venAddresses, binding.vendingAreaSpinner);
                                        }

                                    }
                                });
                            }

                        }
                    });
                }

            }
        });


        binding.ulbSpinner.setOnItemSelectedListener(this);
        binding.wardSpinner.setOnItemSelectedListener(this);
        binding.bankSpinner.setOnItemSelectedListener(this);
        binding.branchSpinner.setOnItemSelectedListener(this);
        binding.stateSpinner.setOnItemSelectedListener(this);
        binding.districtSpinner.setOnItemSelectedListener(this);
        binding.mandalSpinner.setOnItemSelectedListener(this);
        binding.villageSpinner.setOnItemSelectedListener(this);
        binding.businessSpinner.setOnItemSelectedListener(this);
        binding.vendingSpinner.setOnItemSelectedListener(this);
        binding.vendingAreaSpinner.setOnItemSelectedListener(this);
        binding.genderSpinner.setOnItemSelectedListener(this);
        binding.casteSpinner.setOnItemSelectedListener(this);
        binding.religionSpinner.setOnItemSelectedListener(this);
        binding.genderSpinner.setOnItemSelectedListener(this);
        binding.pwdSpinner.setOnItemSelectedListener(this);
        binding.qualificationSpinner.setOnItemSelectedListener(this);


        binding.familyLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MemberActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

            }
        });

        binding.etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dobDateSelection();
            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.dob_rb) {
                    binding.dobLl.setVisibility(View.VISIBLE);
                    binding.ageLl.setVisibility(View.GONE);
                } else {
                    binding.dobLl.setVisibility(View.GONE);
                    binding.ageLl.setVisibility(View.VISIBLE);
                }

                binding.etDob.setText("");
                binding.etAge.setText("");
                dob = "";
                age_str = "";
                binding.tvDob.setText(dob);
                binding.tvAge.setText(age_str);
            }
        });
        binding.radioGroupPwd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.pwd_rb_yes) {
                    physicalVal = getString(R.string.yes);
                    binding.pwdDdLl.setVisibility(View.VISIBLE);
                } else {
                    physicalVal = getString(R.string.no);
                    binding.pwdDdLl.setVisibility(View.GONE);
                    binding.pwdSpinner.setSelection(0);
                    pwdId = "0";
                }


            }
        });


        binding.etAge.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void afterTextChanged(Editable s) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (s.length() > 0) {
                            int age = Integer.valueOf(s.toString());
                            if (!(age > 18)) {
                                age_str = "";
                                dob = "";
                                binding.etAge.setText(age_str);
                                binding.tvDob.setText(dob);
                                Utils.customErrorAlert(MainActivity.this, getString(R.string.app_name),
                                        getString(R.string.age_val_less));
                            } else {
                                Calendar cal = Calendar.getInstance();
                                cal.add(Calendar.YEAR, -age);
                                int mYear = cal.get(Calendar.YEAR);
                                String mMonth = "01";
                                String mDay = "01";
                                dob = mDay + "/" + mMonth + "/" + mYear;
                                age_str = String.valueOf(age);
                                binding.tvDob.setText(getString(R.string.form_dob) + ": " + dob);
                            }
                        } else {
                            age_str = "";
                            binding.tvAge.setText(age_str);
                            binding.tvDob.setText("");
                        }
                    }
                };
                handler.postDelayed(runnable, 500);

            }
        });
        binding.etEvnDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                venDateSelction();
            }
        });

        binding.llApplicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PICTYPE = AppConstants.PIC_APPLICATION;
                if (checkPermissions()) {
                    takePhoto();

                } else {
                    requestPermission();
                }
            }
        });
        binding.llVending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PICTYPE = AppConstants.PIC_VENDOR;
                if (checkPermissions()) {
                    takePhoto();

                } else {
                    requestPermission();
                }
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (binding.ulbSpinner.getSelectedItem() != null) {
                    ulb_str = binding.ulbSpinner.getSelectedItem().toString().trim();
                }
                if (binding.wardSpinner.getSelectedItem() != null) {
                    ward_str = binding.wardSpinner.getSelectedItem().toString().trim();
                }
                if (binding.etAadhaarNum.getText() != null) {
                    aadharNo = binding.etAadhaarNum.getText().toString().trim();
                }
                if (binding.etVendorName.getText() != null) {
                    nameOfVendor = binding.etVendorName.getText().toString().trim();
                }
                if (binding.etFatherName.getText() != null) {
                    fatherHusbName = binding.etFatherName.getText().toString().trim();
                }


                if (binding.genderSpinner.getSelectedItem() != null) {
                    gender_str = binding.genderSpinner.getSelectedItem().toString().trim();
                }

                if (binding.religionSpinner.getSelectedItem() != null) {
                    religion_str = binding.religionSpinner.getSelectedItem().toString().trim();
                }


                if (binding.etOthReligion.getText() != null) {
                    other_religion = binding.etOthReligion.getText().toString().trim();
                }

                if (binding.casteSpinner.getSelectedItem() != null) {
                    caste_str = binding.casteSpinner.getSelectedItem().toString().trim();
                }

                if (binding.etOthCaste.getText() != null) {
                    other_caste = binding.etOthCaste.getText().toString().trim();
                }

                if (binding.pwdSpinner.getSelectedItem() != null) {
                    physChall = binding.pwdSpinner.getSelectedItem().toString().trim();
                }
                if (binding.qualificationSpinner.getSelectedItem() != null) {
                    highQual = binding.qualificationSpinner.getSelectedItem().toString().trim();
                }
                if (binding.bankSpinner.getSelectedItem() != null) {
                    bank_str = binding.bankSpinner.getSelectedItem().toString().trim();
                }
                if (binding.branchSpinner.getSelectedItem() != null) {
                    branch_str = binding.branchSpinner.getSelectedItem().toString().trim();
                }
                if (binding.etIfscCode.getText() != null) {
                    ifscCode = binding.etIfscCode.getText().toString().trim();
                }
                if (binding.etAccNum.getText() != null) {
                    accntNo = binding.etAccNum.getText().toString().trim();
                }
                if (binding.etConAccNum.getText() != null) {
                    confAccntNo = binding.etConAccNum.getText().toString().trim();
                }
                if (binding.etMobNum.getText() != null) {
                    mobNo = binding.etMobNum.getText().toString().trim();
                }
                if (binding.etIceNum.getText() != null) {
                    ICENo = binding.etIceNum.getText().toString().trim();
                }
                if (binding.businessSpinner.getSelectedItem() != null) {
                    businessType = binding.businessSpinner.getSelectedItem().toString().trim();
                }


                if (binding.etOthBusiness.getText() != null) {
                    other_business = binding.etOthBusiness.getText().toString().trim();
                }

                if (binding.vendingSpinner.getSelectedItem() != null) {
                    vendingType = binding.vendingSpinner.getSelectedItem().toString().trim();
                }
                if (binding.vendingAreaSpinner.getSelectedItem() != null) {
                    vendingArea = binding.vendingAreaSpinner.getSelectedItem().toString().trim();
                }

                if (binding.etResAddress.getText() != null) {
                    resAddress = binding.etResAddress.getText().toString().trim();
                }

                if (binding.stateSpinner.getSelectedItem() != null) {
                    state = binding.stateSpinner.getSelectedItem().toString().trim();
                }
                if (binding.etPerHNo.getText() != null) {
                    HNo = binding.etPerHNo.getText().toString().trim();
                }
                if (binding.etPerStreet.getText() != null) {
                    street = binding.etPerStreet.getText().toString().trim();
                }

                if (binding.etPolSta.getText() != null) {
                    policeStation = binding.etPolSta.getText().toString().trim();
                }

                if (binding.stateSpinner.getSelectedItem() != null) {
                    state = binding.stateSpinner.getSelectedItem().toString().trim();
                }

                if (binding.districtSpinner.getSelectedItem() != null) {
                    district = binding.districtSpinner.getSelectedItem().toString().trim();
                }

                if (binding.mandalSpinner.getSelectedItem() != null) {
                    mandal = binding.mandalSpinner.getSelectedItem().toString().trim();
                }

                if (binding.villageSpinner.getSelectedItem() != null) {
                    village = binding.villageSpinner.getSelectedItem().toString().trim();
                }


                if (binding.tvPerDist.getVisibility() == View.VISIBLE && binding.etPerDist.getText() != null) {
                    district = binding.etPerDist.getText().toString().trim();
                }

                if (binding.tvPerMan.getVisibility() == View.VISIBLE && binding.etPerMan.getText() != null) {
                    mandal = binding.etPerMan.getText().toString().trim();
                }

                if (binding.tvPerVil.getVisibility() == View.VISIBLE && binding.etPerVil.getText() != null) {
                    village = binding.etPerVil.getText().toString().trim();
                }

                if (validate()) {
                    SubmitRequest submitRequest = new SubmitRequest();
                    submitRequest.setUlbid(loggedULBId.trim());
                    submitRequest.setDistrictId(loggedDistId.trim());
                    submitRequest.setAge(age_str);
                    submitRequest.setBusinessWard(wardId.trim());
                    submitRequest.setSvName(nameOfVendor);
                    submitRequest.setSvFatherName(fatherHusbName);
                    submitRequest.setDob(dob);
                    submitRequest.setGender(genCode);
                    submitRequest.setCaste(casteId.trim());
                    if (caste_str.contains(getString(R.string.others))) {
                        submitRequest.setCasteOthers(other_caste);
                    } else {
                        submitRequest.setCasteOthers("");
                    }
                    submitRequest.setReligion(religionId.trim());
                    if (religion_str.contains(getString(R.string.others))) {
                        submitRequest.setReligionOthers(other_religion);
                    } else {
                        submitRequest.setReligionOthers("");
                    }
                    submitRequest.setPwdType(pwdId);
                    submitRequest.setQualification(quaId.trim());
                    submitRequest.setSvAdharCardNum(aadharNo);
                    submitRequest.setBankId(bankId.trim());
                    submitRequest.setBranchid(branchId.trim());
                    submitRequest.setBankAcNo(accntNo);
                    submitRequest.setIfscCode(ifscCode);
                    submitRequest.setMobileNo(mobNo);
                    submitRequest.setEmergencyContactNo(ICENo);
                    submitRequest.setBusinessType(businessId.trim());
                    if (businessType.contains(getString(R.string.others))) {
                        submitRequest.setBusinessTypeOthers(other_business);
                    } else {
                        submitRequest.setBusinessTypeOthers("");
                    }
                    submitRequest.setVendingType(vTypeId.trim());
                    submitRequest.setVendingArea(vAddressId.trim());
                    submitRequest.setAddress(resAddress);
                    submitRequest.setPermanentHno(HNo);
                    submitRequest.setPermanentStreet(street);
                    submitRequest.setPoliceStation(policeStation);
                    submitRequest.setStartVendingAct(vendingDOB);
                    submitRequest.setPermanentState(stateId.trim());
                    if (state.contains(getString(R.string.telangana)) || state.equalsIgnoreCase(AppConstants.TELANGANA)) {
                        submitRequest.setPermanentDist(distId.trim());
                        submitRequest.setPermanentMand(manId.trim());
                        submitRequest.setPermanentVillage(vilId.trim());
                        submitRequest.setPermanentDistOthers("");
                        submitRequest.setPermanentMandOthers("");
                        submitRequest.setPermanentVillageOthers("");
                    } else {
                        submitRequest.setPermanentDist("");
                        submitRequest.setPermanentMand("");
                        submitRequest.setPermanentVillage("");
                        submitRequest.setPermanentDistOthers(district);
                        submitRequest.setPermanentMandOthers(mandal);
                        submitRequest.setPermanentVillageOthers(village);
                    }


                    submitRequest.setNoFamilyMembers(String.valueOf(familyInfoArrayList.size()));
                    submitRequest.setSvPhotoPathB64(appBase64Str);
                    submitRequest.setSvActPhotoPathB64(vendingBase64Str);
                    submitRequest.setIpAddress(ipAddress + ";" + deviceId);
                    submitRequest.setFamilyInfo(familyInfoArrayList);

                    customSubmitAlert(submitRequest);


                }
            }
        });
    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !shouldShowRequestPermissionRationale(permissions[0])) {
                    Utils.openSettings(MainActivity.this);
                    Toast.makeText(MainActivity.this, getString(R.string.all_cam_per_setting), Toast.LENGTH_SHORT).show();
                    // User selected the Never Ask Again Option
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.cam_den), Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            customAlert();
                        }
                    }
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                onCaptureImageResult(data, PICTYPE);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(MainActivity.this,
                        getString(R.string.user_can), Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        getString(R.string.failed_cam), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    private String convertBitMap(Bitmap img_bitmap) {
        String encodedImage = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            img_bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encodedImage;
    }


    private Bitmap applicantBitmap, vendigBitmap;
    private String appBase64Str, vendingBase64Str;

    private void onCaptureImageResult(Intent data, String PICTYPE) {
        try {
            if (data != null && data.getExtras() != null) {
                if (PICTYPE.equals(AppConstants.PIC_APPLICATION)) {
                    applicantBitmap = (Bitmap) data.getExtras().get("data");
                    if (applicantBitmap != null) {
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        applicantBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                        binding.ivApplicant.setImageBitmap(applicantBitmap);
                        Bitmap bitmap = ((BitmapDrawable) binding.ivApplicant.getDrawable()).getBitmap();
                        if (bitmap != null) {
                            flag_applicant = 1;
                            appBase64Str = convertBitMap(bitmap);
                        } else
                            Toast.makeText(MainActivity.this, getString(R.string.bitmap_something), Toast.LENGTH_SHORT).show();
                    }
                } else if (PICTYPE.equals(AppConstants.PIC_VENDOR)) {
                    vendigBitmap = (Bitmap) data.getExtras().get("data");
                    if (vendigBitmap != null) {
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        vendigBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                        binding.ivVendingPhoto.setImageBitmap(vendigBitmap);
                        Bitmap bitmap = ((BitmapDrawable) binding.ivVendingPhoto.getDrawable()).getBitmap();
                        if (bitmap != null) {
                            flag_vendorAct = 1;
                            vendingBase64Str = convertBitMap(bitmap);
                        } else
                            Toast.makeText(MainActivity.this, getString(R.string.bitmap_something), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.something_photo), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, getString(R.string.something_photo), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void dobDateSelection() {
        // Get Current Date

        final Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -19);
        Calendar minCal = Calendar.getInstance();
        minCal.add(Calendar.YEAR, -99);
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String date = String.valueOf(dayOfMonth);
                        if (date.length() == 1) {
                            date = "0".concat(date);
                        } else {
                            date = String.valueOf(dayOfMonth);
                        }


                        String month = String.valueOf(monthOfYear + 1);
                        if (month.length() == 1) {
                            month = "0".concat(month);
                        } else {
                            month = String.valueOf(monthOfYear + 1);
                        }
                        dob = date + "/" + month + "/" + year;
                        binding.etDob.setText(dob);

                        age_str = Utils.getAge(year, monthOfYear, Integer.parseInt(date));
                        binding.tvAge.setText(getString(R.string.age) + ": " + age_str + " " + getString(R.string.year));

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(minCal.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    private void customSubmitAlert(SubmitRequest submitRequest) {
        try {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_information);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(getResources().getString(R.string.submit_data));
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(getResources().getString(R.string.submit_text));
                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setText(getString(R.string.yes));
                Button no = dialog.findViewById(R.id.btDialogNo);
                no.setText(getString(R.string.no));
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if (Utils.checkInternetConnection(MainActivity.this)) {
                            customProgressDialog.show();
                            submitViewModel.submitCall(submitRequest);
                        } else {
                            Utils.customErrorAlert(MainActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                        }

                    }
                });


                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    private void venDateSelction() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        Calendar minCal = Calendar.getInstance();
        minCal.add(Calendar.YEAR, -99);

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String date = String.valueOf(dayOfMonth);
                        if (date.length() == 1) {
                            date = "0".concat(date);
                        } else {
                            date = String.valueOf(dayOfMonth);
                        }

                        String month = String.valueOf(monthOfYear + 1);
                        if (month.length() == 1) {
                            month = "0".concat(month);
                        } else {
                            month = String.valueOf(monthOfYear + 1);
                        }

                        vendingDOB = date + "/" + month + "/" + year;
                        binding.etEvnDob.setText(vendingDOB);
                    }
                }, mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.getDatePicker().setMinDate(minCal.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }


    private void customAlert() {
        try {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_permission);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(getResources().getString(R.string.app_name));
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(getString(R.string.plz_grant));
                Button yes = dialog.findViewById(R.id.btDialogOk);
                Button no = dialog.findViewById(R.id.btDialogCancel);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermission();
                        }
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, getString(R.string.cam_den), Toast.LENGTH_SHORT).show();
                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        String string = sharedPreferences.getString(AppConstants.MEMBER_DATA, "");
        Type listType = new TypeToken<List<FamilyInfo>>() {
        }.getType();
        gson = SVSApplication.get(MainActivity.this).getGson();
        familyInfoArrayList = gson.fromJson(string, listType);
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        Utils.customErrorAlert(MainActivity.this, getString(R.string.app_name),
                errMsg);
    }

    private String loggedULBId, loggedDistId, wardId, bankId, branchId;
    private String stateId, distId, manId, vilId;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            switch (parent.getId()) {
                case R.id.ulb_spinner:
                    try {
                        ward_str = "";
                        wardId = "";
                        wardEntities = new ArrayList<>();

                        ulb_str = parent.getSelectedItem().toString();
                        if (!ulb_str.contains(getString(R.string.select))) {
                            if (!TextUtils.isEmpty(loggedULBId)) {
                                LiveData<List<WardEntity>> wardListLiveData = svsSyncULBViewModel.getULBWards(loggedULBId);
                                wardListLiveData.observe(MainActivity.this, new Observer<List<WardEntity>>() {
                                    @Override
                                    public void onChanged(List<WardEntity> wardEntities) {
                                        if (wardEntities != null && wardEntities.size() > 0) {
                                            MainActivity.this.wardEntities = wardEntities;
                                            //load based on language type
                                            wards = new ArrayList<>();
                                            wards.add(getString(R.string.select_ward));
                                            binding.wardSpinner.setEnabled(true);

                                            if (locale.equals("te")) {
                                                for (int x = 0; x < wardEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(wardEntities.get(x).getWardName())) {
                                                        wards.add(wardEntities.get(x).getWardName());
                                                    }
                                                }
                                            } else {
                                                for (int x = 0; x < wardEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(wardEntities.get(x).getWardName())) {
                                                        wards.add(wardEntities.get(x).getWardName());
                                                    }
                                                }
                                            }
                                            Utils.loadSpinnerData(MainActivity.this, wards, binding.wardSpinner);
                                        } else {
                                            Snackbar.make(binding.cl, getString(R.string.no_wards), BaseTransientBottomBar.LENGTH_SHORT).show();


                                            binding.wardSpinner.setEnabled(false);
                                            Utils.loadSpinnerData(MainActivity.this, wards, binding.wardSpinner);
                                        }
                                    }
                                });
                            }

                        } else {
                            binding.wardSpinner.setEnabled(false);
                            Utils.loadSpinnerData(MainActivity.this, wards, binding.wardSpinner);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case R.id.ward_spinner:
                    try {
                        wardId = "";

                        ward_str = parent.getSelectedItem().toString();

                        if (!ward_str.contains(getString(R.string.select))) {
                            MainActivity.this.wardId = wardEntities.get(position - 1).getWardId();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;

                case R.id.religion_spinner:
                    try {
                        religionId = "";
                        other_religion = "";
                        religion_str = parent.getSelectedItem().toString();
                        if (!religion_str.contains(getString(R.string.select))) {

                            MainActivity.this.religionId = religionEntities.get(position).getReligionId();

                            if (religion_str.equals(getString(R.string.others))) {
                                binding.tvOthReligion.setVisibility(View.VISIBLE);
                            } else {
                                binding.tvOthReligion.setVisibility(View.GONE);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case R.id.gender_spinner:
                    try {
                        genCode = "";
                        gender_str = parent.getSelectedItem().toString();
                        if (!gender_str.contains(getString(R.string.select))) {

                            MainActivity.this.genCode = genderEntities.get(position).getGenderCode();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;

                case R.id.caste_spinner:
                    try {
                        casteId = "";
                        other_caste = "";
                        binding.tvOthCaste.setVisibility(View.GONE);
                        caste_str = parent.getSelectedItem().toString();
                        if (!caste_str.contains(getString(R.string.select))) {

                            MainActivity.this.casteId = casteEntities.get(position).getCasteId();

                            if (caste_str.equals(getString(R.string.others))) {
                                binding.tvOthCaste.setVisibility(View.VISIBLE);
                            } else {
                                binding.tvOthCaste.setVisibility(View.GONE);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;

                case R.id.pwd_spinner:
                    try {
                        pwdId = "";
                        physChall = parent.getSelectedItem().toString();
                        if (!physChall.contains(getString(R.string.select))) {

                            MainActivity.this.pwdId = pwdEntities.get(position).getPwdId();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;

                case R.id.qualification_spinner:
                    try {
                        quaId = "";
                        highQual = parent.getSelectedItem().toString();
                        if (!highQual.contains(getString(R.string.select))) {

                            MainActivity.this.quaId = qualificationEntities.get(position).getQualificationId();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;
                case R.id.business_spinner:
                    try {
                        businessId = "";
                        other_business = "";
                        binding.tvOthBusiness.setVisibility(View.GONE);
                        businessType = parent.getSelectedItem().toString();
                        if (!businessType.contains(getString(R.string.select))) {

                            MainActivity.this.businessId = businessEntities.get(position).getBusinessId();

                            if (businessType.equals(getString(R.string.others))) {
                                binding.tvOthBusiness.setVisibility(View.VISIBLE);
                            } else {
                                binding.tvOthBusiness.setVisibility(View.GONE);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case R.id.vending_spinner:
                    try {
                        vTypeId = "";
                        vendingType = parent.getSelectedItem().toString();
                        if (!vendingType.contains(getString(R.string.select))) {

                            MainActivity.this.vTypeId = vendingTypeEntities.get(position).getVendingId();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;


                case R.id.vending_area_spinner:
                    try {
                        vAddressId = "";
                        vendingArea = parent.getSelectedItem().toString();
                        if (!vendingArea.contains(getString(R.string.select))) {
                            MainActivity.this.vAddressId = vendingAddressEntities.get(position - 1).getVendingAreaId();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;


                case R.id.bank_spinner:
                    try {
                        branchEntities = new ArrayList<>();
                        bankId = "";
                        branchId = "";
                        ifscCode = "";
                        branch_str = "";
                        binding.etIfscCode.setText(ifscCode);
                        bank_str = parent.getSelectedItem().toString();
                        if (!bank_str.contains(getString(R.string.select))) {


                            BankEntity bankEntity = bankEntities.get(position);
                            MainActivity.this.bankId = bankEntity.getBankId();
                            if (!TextUtils.isEmpty(bankId)) {

                                LiveData<List<BranchEntity>> branchListLiveData = svsSyncBBViewModel.getBankBranches(bankId);
                                branchListLiveData.observe(MainActivity.this, new Observer<List<BranchEntity>>() {
                                    @Override
                                    public void onChanged(List<BranchEntity> branchEntities) {
                                        if (branchEntities != null && branchEntities.size() > 0) {
                                            MainActivity.this.branchEntities = branchEntities;
                                            //load based on language type
                                            branches = new ArrayList<>();
                                            branches.add(getString(R.string.select_branch));
                                            binding.branchSpinner.setEnabled(true);
                                            if (locale.equals("te")) {
                                                for (int x = 0; x < branchEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(branchEntities.get(x).getBranchNameTel())) {
                                                        branches.add(branchEntities.get(x).getBranchNameTel());
                                                    }
                                                }
                                            } else {
                                                for (int x = 0; x < branchEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(branchEntities.get(x).getBranchName())) {
                                                        branches.add(branchEntities.get(x).getBranchName());
                                                    }
                                                }
                                            }
                                            Utils.loadSpinnerData(MainActivity.this, branches, binding.branchSpinner);
                                        } else {
                                            Snackbar.make(binding.cl, getString(R.string.no_branches), BaseTransientBottomBar.LENGTH_SHORT).show();
                                            binding.branchSpinner.setEnabled(false);
                                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.branchSpinner);

                                            ScrollToView(binding.branchSpinner);
                                            binding.branchSpinner.requestFocus();
                                        }
                                    }
                                });
                            }
                        } else {
                            binding.etIfscCode.setText("");
                            binding.branchSpinner.setEnabled(false);
                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.branchSpinner);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case R.id.branch_spinner:
                    try {
                        branchId = "";
                        ifscCode = "";
                        binding.etIfscCode.setText(ifscCode);
                        branch_str = parent.getSelectedItem().toString();
                        if (!branch_str.contains(getString(R.string.select))) {
                            MainActivity.this.branchId = branchEntities.get(position - 1).getBranchId();
                            if (!TextUtils.isEmpty(branchId)) {
                                LiveData<String> ifscCodeLiveData = svsSyncBBViewModel.getIFSCCode(bankId, branchId);
                                ifscCodeLiveData.observe(MainActivity.this, new Observer<String>() {
                                    @Override
                                    public void onChanged(String ifscCode) {
                                        if (!TextUtils.isEmpty(ifscCode)) {
                                            binding.etIfscCode.setText(ifscCode);
                                        } else {
                                            binding.etIfscCode.setText("");
                                        }
                                    }
                                });
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case R.id.state_spinner:
                    try {
                        district = "";
                        mandal = "";
                        village = "";
                        binding.etPerDist.setText(district);
                        binding.etPerMan.setText(mandal);
                        binding.etPerVil.setText(village);
                        state = parent.getSelectedItem().toString();
                        if (!state.contains(getString(R.string.select))) {

                            MainActivity.this.stateId = stateEntities.get(position).getStateId();
                            if (!TextUtils.isEmpty(stateId)) {
                                if (state.contains(getString(R.string.telangana)) || state.equalsIgnoreCase(AppConstants.TELANGANA)) {

                                    binding.distLl.setVisibility(View.VISIBLE);
                                    binding.mandalLl.setVisibility(View.VISIBLE);
                                    binding.villageLl.setVisibility(View.VISIBLE);

                                    binding.tvPerDist.setVisibility(View.GONE);
                                    binding.tvPerMan.setVisibility(View.GONE);
                                    binding.tvPerVil.setVisibility(View.GONE);

                                    LiveData<List<DistrictEntity>> branchListLiveData = svsSyncPlaceViewModel.getAllDistricts();
                                    branchListLiveData.observe(MainActivity.this, new Observer<List<DistrictEntity>>() {
                                        @Override
                                        public void onChanged(List<DistrictEntity> districtEntities) {

                                            if (districtEntities != null && districtEntities.size() > 0) {
                                                //load based on language type
                                                districts = new ArrayList<>();
                                                binding.districtSpinner.setEnabled(true);
                                                if (locale.equals("te")) {
                                                    for (int x = 0; x < districtEntities.size(); x++) {
                                                        if (!TextUtils.isEmpty(districtEntities.get(x).getDistrictNameTel())) {
                                                            districts.add(districtEntities.get(x).getDistrictNameTel());
                                                        }
                                                    }
                                                } else {
                                                    for (int x = 0; x < districtEntities.size(); x++) {
                                                        if (!TextUtils.isEmpty(districtEntities.get(x).getDistrictName())) {
                                                            districts.add(districtEntities.get(x).getDistrictName());
                                                        }
                                                    }
                                                }
                                                Utils.loadSpinnerData(MainActivity.this, districts, binding.districtSpinner);
                                            } else {
                                                binding.mandalSpinner.setEnabled(false);
                                                binding.villageSpinner.setEnabled(false);
                                                binding.districtSpinner.setEnabled(false);
                                                Utils.loadSpinnerData(MainActivity.this, emptyList, binding.districtSpinner);
                                                Utils.loadSpinnerData(MainActivity.this, emptyList, binding.mandalSpinner);
                                                Utils.loadSpinnerData(MainActivity.this, emptyList, binding.villageSpinner);
                                            }
                                        }
                                    });
                                } else {
                                    binding.distLl.setVisibility(View.GONE);
                                    binding.mandalLl.setVisibility(View.GONE);
                                    binding.villageLl.setVisibility(View.GONE);

                                    binding.tvPerDist.setVisibility(View.VISIBLE);
                                    binding.tvPerMan.setVisibility(View.VISIBLE);
                                    binding.tvPerVil.setVisibility(View.VISIBLE);
                                }
                            }

                        } else {
                            binding.mandalSpinner.setEnabled(false);
                            binding.villageSpinner.setEnabled(false);
                            binding.districtSpinner.setEnabled(false);

                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.districtSpinner);
                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.mandalSpinner);
                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.villageSpinner);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case R.id.district_spinner:
                    try {
                        mandalEntities = new ArrayList<>();
                        villageEntities = new ArrayList<>();
                        mandal = "";
                        village = "";
                        district = parent.getSelectedItem().toString();
                        if (!district.contains(getString(R.string.select))) {

                            MainActivity.this.distId = districtEntities.get(position).getDistrictId();
                            if (!TextUtils.isEmpty(distId)) {
                                LiveData<List<MandalEntity>> maListLiveData = svsSyncPlaceViewModel.getDistrictMandals(distId);
                                maListLiveData.observe(MainActivity.this, new Observer<List<MandalEntity>>() {
                                    @Override
                                    public void onChanged(List<MandalEntity> mandalEntities) {
                                        MainActivity.this.mandalEntities = mandalEntities;
                                        if (mandalEntities != null && mandalEntities.size() > 0) {
                                            //load based on language type
                                            mandals = new ArrayList<>();
                                            mandals.add(getString(R.string.select_mandal));
                                            binding.mandalSpinner.setEnabled(true);
                                            if (locale.equals("te")) {
                                                for (int x = 0; x < mandalEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(mandalEntities.get(x).getMandalNameTel())) {
                                                        mandals.add(mandalEntities.get(x).getMandalNameTel());
                                                    }
                                                }
                                            } else {
                                                for (int x = 0; x < mandalEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(mandalEntities.get(x).getMandalName())) {
                                                        mandals.add(mandalEntities.get(x).getMandalName());
                                                    }
                                                }
                                            }
                                            Utils.loadSpinnerData(MainActivity.this, mandals, binding.mandalSpinner);
                                        } else {
                                            Snackbar.make(binding.cl, getString(R.string.no_mandals), BaseTransientBottomBar.LENGTH_SHORT).show();

                                            binding.mandalSpinner.setEnabled(false);
                                            binding.villageSpinner.setEnabled(false);

                                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.mandalSpinner);
                                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.villageSpinner);
                                        }
                                    }
                                });


                            }

                        } else {
                            binding.mandalSpinner.setEnabled(false);
                            binding.villageSpinner.setEnabled(false);
                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.mandalSpinner);
                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.villageSpinner);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case R.id.mandal_spinner:
                    try {
                        villageEntities = new ArrayList<>();
                        village = "";
                        manId = "";
                        vilId = "";

                        mandal = parent.getSelectedItem().toString();
                        if (!mandal.contains(getString(R.string.select))) {

                            MainActivity.this.manId = mandalEntities.get(position - 1).getMandalId();
                            if (!TextUtils.isEmpty(manId)) {
                                LiveData<List<VillageEntity>> branchListLiveData = svsSyncPlaceViewModel.getMandalVillages(distId, manId);
                                branchListLiveData.observe(MainActivity.this, new Observer<List<VillageEntity>>() {
                                    @Override
                                    public void onChanged(List<VillageEntity> villageEntities) {
                                        MainActivity.this.villageEntities = villageEntities;
                                        if (villageEntities != null && villageEntities.size() > 0) {
                                            //load based on language type
                                            binding.villageSpinner.setEnabled(true);

                                            villages = new ArrayList<>();
                                            villages.add(getString(R.string.select_village));
                                            if (locale.equals("te")) {
                                                for (int x = 0; x < villageEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(villageEntities.get(x).getVillageNameTel())) {
                                                        villages.add(villageEntities.get(x).getVillageNameTel());
                                                    }
                                                }
                                            } else {
                                                for (int x = 0; x < villageEntities.size(); x++) {
                                                    if (!TextUtils.isEmpty(villageEntities.get(x).getVillageName())) {
                                                        villages.add(villageEntities.get(x).getVillageName());
                                                    }
                                                }
                                            }
                                            Utils.loadSpinnerData(MainActivity.this, villages, binding.villageSpinner);
                                        } else {
                                            Snackbar.make(binding.cl, getString(R.string.no_villages), BaseTransientBottomBar.LENGTH_SHORT).show();

                                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.villageSpinner);

                                            binding.villageSpinner.setEnabled(false);
                                        }
                                    }
                                });


                            }

                        } else {
                            binding.villageSpinner.setEnabled(false);

                            Utils.loadSpinnerData(MainActivity.this, emptyList, binding.villageSpinner);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;

                case R.id.village_spinner:
                    try {
                        vilId = "";
                        village = parent.getSelectedItem().toString();
                        if (!village.contains(getString(R.string.select))) {
                            MainActivity.this.vilId = villageEntities.get(position - 1).getVillageId();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean validate() {

        if (TextUtils.isEmpty(ulb_str) || ulb_str.contains(getString(R.string.select))) {
            ScrollToView(binding.ulbSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_ulb));
            binding.ulbSpinner.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(ward_str) || ward_str.contains(getString(R.string.select))) {
            ScrollToView(binding.wardSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_ward_number));
            binding.wardSpinner.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(aadharNo)) {
            ScrollToView(binding.etAadhaarNum);
            showBottomSheetSnackBar(getResources().getString(R.string.ent_aad_num));
            binding.etAadhaarNum.requestFocus();
            return false;
        }
        if (aadharNo.length() != 12) {
            ScrollToView(binding.etAadhaarNum);
            showBottomSheetSnackBar(getResources().getString(R.string.valid_aad_num));
            binding.etAadhaarNum.requestFocus();
            return false;
        }

        if (!Utils.ValidateAadharNumber(aadharNo)) {
            ScrollToView(binding.etAadhaarNum);
            showBottomSheetSnackBar(getResources().getString(R.string.valid_aad_num));
            binding.etAadhaarNum.requestFocus();
            return false;
        }
        if (binding.btnVerify.getText().equals(getString(R.string.verify))) {
            ScrollToView(binding.etAadhaarNum);
            showBottomSheetSnackBar(getResources().getString(R.string.verify_aad));
            binding.etAadhaarNum.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(nameOfVendor)) {
            ScrollToView(binding.etVendorName);
            showBottomSheetSnackBar(getResources().getString(R.string.enter_name_of_vendor));
            binding.etVendorName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(fatherHusbName)) {
            ScrollToView(binding.etFatherName);
            showBottomSheetSnackBar(getResources().getString(R.string.enter_father_husband_name));
            binding.etFatherName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(fatherHusbName)) {
            ScrollToView(binding.etFatherName);
            showBottomSheetSnackBar(getResources().getString(R.string.enter_father_husband_name));
            binding.etFatherName.requestFocus();
            return false;
        }

        if (!binding.dobRb.isChecked() && !binding.ageRb.isChecked()) {
            ScrollToView(binding.radioLl);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_dob_age));
            binding.etFatherName.requestFocus();
            return false;
        }

        if (binding.dobRb.isChecked() && !binding.ageRb.isChecked() && TextUtils.isEmpty(dob)) {
            ScrollToView(binding.etDob);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_dob));
            binding.etDob.requestFocus();
            return false;
        }

        if (!binding.dobRb.isChecked() && binding.ageRb.isChecked() && TextUtils.isEmpty(age_str)) {
            ScrollToView(binding.etAge);
            showBottomSheetSnackBar(getResources().getString(R.string.enter_age));
            binding.etAge.requestFocus();
            return false;
        }

        if (!TextUtils.isEmpty(age_str) && !(Integer.valueOf(age_str) > 18 && Integer.valueOf(age_str) <= 99)) {
            ScrollToView(binding.etAge);
            showBottomSheetSnackBar(getResources().getString(R.string.age_val));
            binding.etAge.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(gender_str) || gender_str.contains(getString(R.string.select))) {
            ScrollToView(binding.genderSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_gender_val));
            binding.genderSpinner.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(religion_str) || religion_str.contains(getString(R.string.select))) {
            ScrollToView(binding.religionSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_religion));
            binding.religionSpinner.requestFocus();
            return false;
        }

        if (religion_str.equals(getString(R.string.others)) && TextUtils.isEmpty(other_religion)) {
            ScrollToView(binding.etOthReligion);
            showBottomSheetSnackBar(getResources().getString(R.string.ent_other_religion));
            binding.etOthReligion.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(caste_str) || caste_str.contains(getString(R.string.select))) {
            ScrollToView(binding.casteSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_caste_val));
            binding.casteSpinner.requestFocus();
            return false;
        }

        if (caste_str.equals(getString(R.string.others)) && TextUtils.isEmpty(other_caste)) {
            ScrollToView(binding.etOthCaste);
            showBottomSheetSnackBar(getResources().getString(R.string.ent_other_caste));
            binding.etOthCaste.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(physicalVal)) {
            ScrollToView(binding.radioLl);
            showBottomSheetSnackBar(getResources().getString(R.string.pwd_type_sel));
            binding.radioGroupPwd.requestFocus();
            return false;
        }


        if (physicalVal.equals(getString(R.string.yes)) && physChall.contains(getString(R.string.select))) {
            ScrollToView(binding.pwdSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.pwd_type_sel_some));
            binding.pwdSpinner.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(highQual) || highQual.contains(getString(R.string.select))) {
            ScrollToView(binding.qualificationSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_high_qualification_val));
            binding.qualificationSpinner.requestFocus();
            return false;
        }
        if (flag_applicant == 0) {
            ScrollToView(binding.ivApplicant);
            showBottomSheetSnackBar(getString(R.string.cap_applicant));
            binding.ivApplicant.requestFocus();
            return false;
        }
        if (flag_vendorAct == 0) {
            ScrollToView(binding.ivVendingPhoto);
            showBottomSheetSnackBar(getString(R.string.cap_vend_act));
            binding.ivVendingPhoto.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(bank_str) || bank_str.contains(getString(R.string.select))) {
            ScrollToView(binding.bankSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_bank_val));
            binding.bankSpinner.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(branch_str) || branch_str.contains(getString(R.string.select)) || branch_str.equals("-")) {
            ScrollToView(binding.branchSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_branch_val));
            binding.branchSpinner.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(ifscCode) || ifscCode.equals("-")) {
            ScrollToView(binding.etIfscCode);
            showBottomSheetSnackBar(getResources().getString(R.string.no_ifsc));
            binding.etIfscCode.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(accntNo)) {
            ScrollToView(binding.etAccNum);
            showBottomSheetSnackBar(getResources().getString(R.string.enter_bank_acc_num));
            binding.etAccNum.requestFocus();
            return false;
        }

        if (Long.valueOf(accntNo) == 0) {
            ScrollToView(binding.etAccNum);
            showBottomSheetSnackBar(getResources().getString(R.string.bank_acc_sho_all_aero));
            binding.etAccNum.requestFocus();
            return false;
        }

        if (accntNo.length() < 10) {
            ScrollToView(binding.etAccNum);
            showBottomSheetSnackBar(getResources().getString(R.string.bank_acc_sho_min_ten));
            binding.etAccNum.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(confAccntNo)) {
            ScrollToView(binding.etConAccNum);
            showBottomSheetSnackBar(getResources().getString(R.string.enter_con_bank_acc_num));
            binding.etConAccNum.requestFocus();
            return false;
        }
        if (!(accntNo.equals(confAccntNo))) {
            ScrollToView(binding.etConAccNum);
            showBottomSheetSnackBar(getResources().getString(R.string.accnt_no_conf_accnt_no_should_be_same));
            binding.etConAccNum.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(mobNo)) {
            ScrollToView(binding.etMobNum);
            showBottomSheetSnackBar(getResources().getString(R.string.ent_mob_num));
            binding.etMobNum.requestFocus();
            return false;
        }
        if (mobNo.length() != 10 || !(mobNo.startsWith("6") || mobNo.startsWith("7") || mobNo.startsWith("8") || mobNo.startsWith("9"))) {
            ScrollToView(binding.etMobNum);
            showBottomSheetSnackBar(getResources().getString(R.string.val_mob_num));
            binding.etMobNum.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(ICENo)) {
            ScrollToView(binding.etIceNum);
            showBottomSheetSnackBar(getResources().getString(R.string.ent_ice_num));
            binding.etIceNum.requestFocus();
            return false;
        }
        if (ICENo.length() != 10 || !(ICENo.startsWith("6") || ICENo.startsWith("7") || ICENo.startsWith("8") || ICENo.startsWith("9"))) {
            ScrollToView(binding.etIceNum);
            showBottomSheetSnackBar(getResources().getString(R.string.val_ice_num));
            binding.etIceNum.requestFocus();
            return false;
        }
        if (mobNo.equalsIgnoreCase(ICENo)) {
            ScrollToView(binding.etMobNum);
            showBottomSheetSnackBar(getResources().getString(R.string.validationmobandemergency));
            binding.etMobNum.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(resAddress)) {
            ScrollToView(binding.etResAddress);
            showBottomSheetSnackBar(getResources().getString(R.string.enter_residential_address));
            binding.etResAddress.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(state) || state.contains(getString(R.string.select))) {
            ScrollToView(binding.stateSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.state_val));
            binding.stateSpinner.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(district) && !(state.equals(getString(R.string.telangana)) || state.equalsIgnoreCase(AppConstants.TELANGANA))) {
            ScrollToView(binding.etPerDist);
            showBottomSheetSnackBar(getResources().getString(R.string.ent_dist_name));
            binding.etPerDist.requestFocus();
            return false;
        }

        if ((TextUtils.isEmpty(district) || district.contains(getString(R.string.select)))
                && (state.equals(getString(R.string.telangana)) || state.equalsIgnoreCase(AppConstants.TELANGANA))) {
            ScrollToView(binding.districtSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_dis_val));
            binding.districtSpinner.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(mandal) && !(state.equals(getString(R.string.telangana)) || state.equalsIgnoreCase(AppConstants.TELANGANA))) {
            ScrollToView(binding.etPerMan);
            showBottomSheetSnackBar(getResources().getString(R.string.ent_man_name));
            binding.etPerMan.requestFocus();
            return false;
        }

        if ((TextUtils.isEmpty(mandal) || mandal.contains(getString(R.string.select)))
                && (state.equals(getString(R.string.telangana)) || state.equalsIgnoreCase(AppConstants.TELANGANA))) {
            ScrollToView(binding.mandalSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_man_val));
            binding.mandalSpinner.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(village) && !(state.equals(getString(R.string.telangana)) || state.equalsIgnoreCase(AppConstants.TELANGANA))) {
            ScrollToView(binding.etPerVil);
            showBottomSheetSnackBar(getResources().getString(R.string.ent_vil_nmae));
            binding.etPerVil.requestFocus();
            return false;
        }

        if ((TextUtils.isEmpty(village) || village.contains(getString(R.string.select)))
                && (state.equals(getString(R.string.telangana)) || state.equalsIgnoreCase(AppConstants.TELANGANA))) {
            ScrollToView(binding.villageSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_vil_val));
            binding.villageSpinner.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(HNo)) {
            ScrollToView(binding.etPerHNo);
            showBottomSheetSnackBar(getResources().getString(R.string.enter_h_no));
            binding.etPerHNo.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(street)) {
            ScrollToView(binding.etPerStreet);
            showBottomSheetSnackBar(getResources().getString(R.string.enter_street));
            binding.etPerStreet.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(businessType) || businessType.contains(getString(R.string.select))) {
            ScrollToView(binding.businessSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_type_of_business));
            binding.businessSpinner.requestFocus();
            return false;
        }

        if (businessType.equals(getString(R.string.others)) && TextUtils.isEmpty(other_business)) {
            ScrollToView(binding.etOthBusiness);
            showBottomSheetSnackBar(getResources().getString(R.string.ent_other_business));
            binding.etOthBusiness.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(vendingType) || vendingType.contains(getString(R.string.select))) {
            ScrollToView(binding.vendingSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_type_of_vending));
            binding.vendingSpinner.requestFocus();
            return false;
        }

        if (!(venAddresses != null && venAddresses.size() > 0)) {
            Utils.customErrorAlert(MainActivity.this, getString(R.string.app_name),
                    getString(R.string.no_ven_area));
            binding.vendingSpinner.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(vendingArea) || vendingArea.contains(getString(R.string.select))) {
            ScrollToView(binding.vendingAreaSpinner);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_vending_area_val));
            binding.vendingAreaSpinner.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(vendingDOB)) {
            ScrollToView(binding.etEvnDob);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_vending_date));
            binding.etEvnDob.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(policeStation)) {
            ScrollToView(binding.etPolSta);
            showBottomSheetSnackBar(getResources().getString(R.string.ent_police_station));
            binding.etPolSta.requestFocus();
            return false;
        }


        if (familyInfoArrayList == null) {
            ScrollToView(binding.familyLl);
            showBottomSheetSnackBar(getResources().getString(R.string.add_atleast_member));
            binding.familyLl.requestFocus();
            return false;
        }

        if (familyInfoArrayList.size() == 0) {
            ScrollToView(binding.familyLl);
            showBottomSheetSnackBar(getResources().getString(R.string.add_atleast_member));
            binding.familyLl.requestFocus();
            return false;
        }

        return true;
    }

    private void showBottomSheetSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }


    private boolean validateData() {
        String aadharNum = binding.etAadhaarNum.getText().toString().trim();
        if (TextUtils.isEmpty(aadharNum)) {
            ScrollToView(binding.etAadhaarNum);
            binding.etAadhaarNum.setError(getString(R.string.ent_aad_num));
            binding.etAadhaarNum.requestFocus();
            return false;
        }

        boolean isValidAadhar = Utils.ValidateAadharNumber(aadharNum);
        if (!isValidAadhar) {
            ScrollToView(binding.etAadhaarNum);
            binding.etAadhaarNum.setError(getString(R.string.valid_aad_num));
            binding.etAadhaarNum.requestFocus();
            return false;
        }
        return true;
    }

    private void ScrollToView(View view) {
        view.getParent().requestChildFocus(view, view);
    }

    @Override
    public void onBackPressed() {

        Utils.customAlertExit(MainActivity.this,
                getResources().getString(R.string.app_name),
                getString(R.string.enroll_cancel), editor);
    }

    @Override
    public void getData(SubmitResponse submitResponse) {
        customProgressDialog.hide();
        if (submitResponse.getStatusCode() != null) {

            if (submitResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                editor.putString(AppConstants.MEMBER_DATA, "");
                editor.commit();
                familyInfoArrayList.clear();


                Utils.customSaveAlert(MainActivity.this, getString(R.string.app_name),
                        submitResponse.getStatusMessage());
            } else if (submitResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {

                Utils.customErrorAlert(MainActivity.this, getString(R.string.app_name),
                        submitResponse.getStatusMessage());
            } else {
                Utils.customErrorAlert(MainActivity.this, getString(R.string.app_name),
                        getString(R.string.something));
            }
        } else {
            Utils.customErrorAlert(MainActivity.this, getString(R.string.app_name),
                    getString(R.string.something));
        }

    }

    @Override
    public void getAadharData(ValidateAadharResponse validateAadharResponse) {
        customProgressDialog.hide();
        if (validateAadharResponse != null && validateAadharResponse.getStatusCode() != null) {

            if (validateAadharResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)
                    && validateAadharResponse.getSvData() != null && validateAadharResponse.getSvData().size() == 0) {
                binding.venRemLl.setVisibility(View.VISIBLE);
                binding.otherLl.setVisibility(View.VISIBLE);
                binding.btnVerify.setText(getString(R.string.edit));
                binding.etAadhaarNum.setEnabled(false);


            } else if (validateAadharResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)
                    && validateAadharResponse.getSvData() != null && validateAadharResponse.getSvData().size() > 0) {


                binding.etAadhaarNum.setText("");
                Utils.customErrorAlertExit(MainActivity.this, getString(R.string.app_name),
                        aadharNo + " is " + validateAadharResponse.getStatusMessage() + " with the name " +
                                validateAadharResponse.getSvData().get(0).getSvName());

/*
                                                if (!TextUtils.isEmpty(validateAadharResponse.getSvData().get(0).getSvPhoto())) {
                                                    String replacedPhoto =
                                                            validateAadharResponse.getSvData().get(0).getSvPhoto().
                                                                    replace("data:image/png;base64,", "");
                                                    try {
                                                        byte[] decodedString = Base64.decode(replacedPhoto, Base64.DEFAULT);
                                                        Utils.customErrorAlertExitPhoto(MainActivity.this, getString(R.string.app_name),
                                                                aadharNo + " is " + validateAadharResponse.getStatusMessage() + " with the name " +
                                                                        validateAadharResponse.getSvData().get(0).getSvName(),
                                                                decodedString,validateAadharResponse.getSvData().get(0).getSvName());

                                                    } catch (Exception e) {
                                                        Utils.customErrorAlertExit(MainActivity.this, getString(R.string.app_name),
                                                                aadharNo + " is " + validateAadharResponse.getStatusMessage() + " with the name " +
                                                                        validateAadharResponse.getSvData().get(0).getSvName());
                                                    }
                                                } else {
                                                    Utils.customErrorAlertExit(MainActivity.this, getString(R.string.app_name),
                                                            aadharNo + " is " + validateAadharResponse.getStatusMessage() + " with the name " +
                                                                    validateAadharResponse.getSvData().get(0).getSvName());
                                                }
*/

            } else if (validateAadharResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                Utils.customErrorAlertExit(MainActivity.this, getString(R.string.app_name),
                        validateAadharResponse.getStatusMessage());

                binding.etAadhaarNum.setText("");
            } else {
                Utils.customErrorAlertExit(MainActivity.this, getString(R.string.app_name),
                        getString(R.string.something));
                binding.etAadhaarNum.setText("");
            }
        } else {
            binding.etAadhaarNum.setText("");
            Utils.customErrorAlertExit(MainActivity.this, getString(R.string.app_name),
                    getString(R.string.something));
        }
    }
}
