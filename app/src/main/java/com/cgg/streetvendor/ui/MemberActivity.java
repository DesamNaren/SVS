package com.cgg.streetvendor.ui;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.application.AppConstants;
import com.cgg.streetvendor.application.SVSApplication;
import com.cgg.streetvendor.databinding.ActivityAddMemberBinding;
import com.cgg.streetvendor.databinding.ActivityMemberBinding;
import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.interfaces.MemberInterface;
import com.cgg.streetvendor.source.reposnse.kyc.GenderEntity;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.RelationEntity;
import com.cgg.streetvendor.util.LocaleHelper;
import com.cgg.streetvendor.util.Utils;
import com.cgg.streetvendor.viewmodel.SVSSyncKYCViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MemberActivity extends AppCompatActivity implements MemberInterface, ErrorHandlerInterface {

    private CoordinatorLayout rootLayout;
    private ActivityAddMemberBinding activityAddMemberBinding;
    private String memGender, memName, memAge, memRelation, memQualification;
    private String genId, relId, quaId;
    private ArrayList<FamilyInfo> familyInfoArrayList;
    private ActivityMemberBinding activityMemberBinding;
    private MemberAdapter memberAdapter;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private String memberData;
    private SharedPreferences sharedPreferences;
    SVSSyncKYCViewModel svsSyncKYCViewModel;
    private ArrayList<String> qualifications, relations, genders;
    private String locale;

    private String select_empty;
    private ArrayList<String> emptyList = new ArrayList<>();

    private List<GenderEntity> genderEntities;
    private List<RelationEntity> relationEntities;
    private List<QualificationEntity> qualificationEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMemberBinding = DataBindingUtil.setContentView(MemberActivity.this, R.layout.activity_member);
        emptyList.add(select_empty);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int startColor = getWindow().getStatusBarColor();
            int endColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
            ObjectAnimator.ofArgb(getWindow(), "statusBarColor", startColor, endColor).start();
        }

        activityMemberBinding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        locale = LocaleHelper.getLanguage(MemberActivity.this);
        qualifications = new ArrayList<>();
        relations = new ArrayList<>();
        genders = new ArrayList<>();
        editor = SVSApplication.get(MemberActivity.this).getPreferencesEditor();
        gson = SVSApplication.get(MemberActivity.this).getGson();
        svsSyncKYCViewModel = new SVSSyncKYCViewModel(this, getApplication());

        activityMemberBinding.header.headerTitle.setText(getString(R.string.member_registration));
        activityMemberBinding.contentLayout.rvMembers.setLayoutManager(new LinearLayoutManager(this));
        sharedPreferences = SVSApplication.get(this).getPreferences();
        String string = sharedPreferences.getString(AppConstants.MEMBER_DATA, "");
        Type listType = new TypeToken<List<FamilyInfo>>() {
        }.getType();
        familyInfoArrayList = gson.fromJson(string, listType);

        if (familyInfoArrayList != null && familyInfoArrayList.size() > 0) {
            activityMemberBinding.contentLayout.tvInitText.setVisibility(View.GONE);
            activityMemberBinding.contentLayout.btnDone.setVisibility(View.VISIBLE);
            memberAdapter = new MemberAdapter(MemberActivity.this,
                    familyInfoArrayList);
            activityMemberBinding.contentLayout.rvMembers.setAdapter(memberAdapter);

            memberAdapter.notifyDataSetChanged();
        } else {
            familyInfoArrayList = new ArrayList<>();
            activityMemberBinding.contentLayout.tvInitText.setVisibility(View.VISIBLE);

        }

        activityMemberBinding.contentLayout.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMemberLayout(null, -1);
            }
        });

        LiveData<List<RelationEntity>> rListLiveData = svsSyncKYCViewModel.getAllRelations();
        rListLiveData.observe(MemberActivity.this, new Observer<List<RelationEntity>>() {
            @Override
            public void onChanged(List<RelationEntity> relationEntities) {
                rListLiveData.removeObservers(MemberActivity.this);
                MemberActivity.this.relationEntities = relationEntities;
                if (relationEntities == null || relationEntities.size() <= 0) {

                    Utils.customSyncAlertDownload(MemberActivity.this, getString(R.string.app_name),
                            getString(R.string.relation_message));
                } else {
                    //load based on language type

                    if (locale.equals("te")) {
                        for (int x = 0; x < relationEntities.size(); x++) {

                            if (!TextUtils.isEmpty(relationEntities.get(x).getRelationNameTel())) {
                                relations.add(relationEntities.get(x).getRelationNameTel());
                            }
                        }
                    } else {
                        for (int x = 0; x < relationEntities.size(); x++) {

                            if (!TextUtils.isEmpty(relationEntities.get(x).getRelationName())) {
                                relations.add(relationEntities.get(x).getRelationName());
                            }
                        }
                    }


                }
            }
        });

        LiveData<List<GenderEntity>> gListLiveData = svsSyncKYCViewModel.getAllGender();
        gListLiveData.observe(MemberActivity.this, new Observer<List<GenderEntity>>() {
            @Override
            public void onChanged(List<GenderEntity> genderEntities) {
                gListLiveData.removeObservers(MemberActivity.this);
                MemberActivity.this.genderEntities = genderEntities;

                if (genderEntities == null || genderEntities.size() <= 0) {
                    Utils.customSyncAlertDownload(MemberActivity.this, getString(R.string.app_name),
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

                }
            }
        });

        LiveData<List<QualificationEntity>> qListLiveData = svsSyncKYCViewModel.getAllQual();
        qListLiveData.observe(MemberActivity.this, new Observer<List<QualificationEntity>>() {
            @Override
            public void onChanged(List<QualificationEntity> qualificationEntities) {
                qListLiveData.removeObservers(MemberActivity.this);
                MemberActivity.this.qualificationEntities = qualificationEntities;
                if (qualificationEntities == null || qualificationEntities.size() <= 0) {
                    Utils.customSyncAlertDownload(MemberActivity.this, getString(R.string.app_name),
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
                }
            }
        });
    }

    public void addMemberLayout(FamilyInfo familyInfo, int pos) {
        activityAddMemberBinding = DataBindingUtil.inflate(
                LayoutInflater.from(MemberActivity.this),
                R.layout.activity_add_member, null, false);
        Dialog dialog = new BottomSheetDialog(MemberActivity.this);
        dialog.setContentView(activityAddMemberBinding.getRoot());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        Utils.loadSpinnerData(MemberActivity.this, qualifications, activityAddMemberBinding.qualificationSpinner);
        Utils.loadSpinnerData(MemberActivity.this, relations, activityAddMemberBinding.relationSpinner);
        Utils.loadSpinnerData(MemberActivity.this, genders, activityAddMemberBinding.genderSpinner);

        if (familyInfo != null) {
            activityAddMemberBinding.etMemberName.setText(familyInfo.getFmemberName());
            activityAddMemberBinding.etMemberAge.setText(familyInfo.getFage());
            activityAddMemberBinding.genderSpinner.setSelection(getIndex(
                    activityAddMemberBinding.genderSpinner, familyInfo.getGender_sel()
            ));
            activityAddMemberBinding.relationSpinner.setSelection(getIndex(
                    activityAddMemberBinding.relationSpinner, familyInfo.getRel_sel()
            ));
            activityAddMemberBinding.qualificationSpinner.setSelection(getIndex(
                    activityAddMemberBinding.qualificationSpinner, familyInfo.getQal_sel()
            ));
        }


        activityAddMemberBinding.icClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        activityAddMemberBinding.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genId="";
                if (parent.getSelectedItem() != null) {
                    String gender = parent.getSelectedItem().toString();
                    if (!gender.contains(getString(R.string.select))) {
                        MemberActivity.this.genId = genderEntities.get(position).getGenderId();
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        activityAddMemberBinding.relationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                relId="";
                if (parent.getSelectedItem() != null) {
                    String relation = parent.getSelectedItem().toString();
                    if (!relation.contains(getString(R.string.select))) {
                        MemberActivity.this.relId = relationEntities.get(position).getRelationId();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        activityAddMemberBinding.qualificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quaId="";
                if (parent.getSelectedItem() != null) {
                    String qua = parent.getSelectedItem().toString();
                    if (!qua.contains(getString(R.string.select))) {
                        MemberActivity.this.quaId = qualificationEntities.get(position).getQualificationId();
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        activityAddMemberBinding.btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityAddMemberBinding.etMemberName.getText() != null) {
                    memName = activityAddMemberBinding.etMemberName.getText().toString().trim();
                }
                if (activityAddMemberBinding.etMemberAge.getText() != null) {
                    memAge = activityAddMemberBinding.etMemberAge.getText().toString().trim();
                }
                if (activityAddMemberBinding.genderSpinner.getSelectedItem() != null) {
                    memGender = activityAddMemberBinding.genderSpinner.getSelectedItem().toString().trim();
                }
                if (activityAddMemberBinding.relationSpinner.getSelectedItem() != null) {
                    memRelation = activityAddMemberBinding.relationSpinner.getSelectedItem().toString().trim();
                }
                if (activityAddMemberBinding.qualificationSpinner.getSelectedItem() != null) {
                    memQualification = activityAddMemberBinding.qualificationSpinner.getSelectedItem().toString().trim();
                }


                if (memberAddValidations()) {
                    dialog.dismiss();
                    if (familyInfo != null) {
                        familyInfo.setFmemberName(memName);
                        familyInfo.setFage(memAge);
                        familyInfo.setFgender(genId);
                        familyInfo.setFrelation(relId);
                        familyInfo.setFqualification(quaId);
                        familyInfo.setGender_sel(memGender);
                        familyInfo.setRel_sel(memRelation);
                        familyInfo.setQal_sel(memQualification);
                        familyInfoArrayList.set(pos, familyInfo);
                    } else {
                        FamilyInfo familyInfo = new FamilyInfo(memName, genId,
                                memAge, relId, quaId, memGender, memRelation, memQualification);
                        familyInfoArrayList.add(familyInfo);

                    }

                    memberAdapter = new MemberAdapter(MemberActivity.this,
                            familyInfoArrayList);
                    activityMemberBinding.contentLayout.rvMembers.setAdapter(memberAdapter);

                    memberAdapter.notifyDataSetChanged();

                    editor.putString(AppConstants.MEMBER_DATA, gson.toJson(familyInfoArrayList));
                    editor.commit();

//                    activityMemberBinding.contentLayout.rvMembers.scrollToPosition(familyInfoArrayList.size()+1);

                    showInitText();
                    // Set adapter to RecyclerView
                }
            }
        });

    }

    private void showInitText() {
        if (familyInfoArrayList.size() > 0) {
            activityMemberBinding.contentLayout.tvInitText.setVisibility(View.GONE);
            activityMemberBinding.contentLayout.btnDone.setVisibility(View.VISIBLE);
        } else {
            activityMemberBinding.contentLayout.tvInitText.setVisibility(View.VISIBLE);
            activityMemberBinding.contentLayout.btnDone.setVisibility(View.GONE);
        }
    }

    private boolean memberAddValidations() {

        if (TextUtils.isEmpty(memName)) {
            showSnackBar(getString(R.string.ent_member_name));
            return false;
        }

        if (TextUtils.isEmpty(memAge)) {
            showSnackBar(getString(R.string.ent_member_age));
            return false;
        }
        if (Integer.valueOf(memAge) == 0 || Integer.valueOf(memAge) > 99) {
            showSnackBar(getString(R.string.ent_valid_member_age));
            return false;
        }

        if (TextUtils.isEmpty(memGender) || memGender.contains(getString(R.string.select))) {
            showSnackBar(getString(R.string.sel_member_gender));
            return false;
        }
        if (TextUtils.isEmpty(memRelation) || memRelation.contains(getString(R.string.select))) {
            showSnackBar(getString(R.string.sel_member_relation));
            return false;
        }
        if (TextUtils.isEmpty(memQualification) || memQualification.contains(getString(R.string.select))) {
            showSnackBar(getString(R.string.sel_member_qualification));
            return false;
        }
        return true;
    }

    private void showSnackBar(String str) {
        Snackbar.make(activityAddMemberBinding.rootCl, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void editMember(FamilyInfo familyInfo, int pos) {
        addMemberLayout(familyInfo, pos);
    }

    @Override
    public void removeMember(int pos) {
        familyInfoArrayList.remove(pos);
        memberAdapter.notifyDataSetChanged();
        editor.putString(AppConstants.MEMBER_DATA, gson.toJson(familyInfoArrayList));
        editor.commit();
        showInitText();
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public void handleError(Throwable e, Context context) {

    }


}
